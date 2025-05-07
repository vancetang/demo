package com.vance.demo.util.tool;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.StringUtils;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 使用 CompletableFuture 和傳統平台線程池實現的並行任務處理工具。
 * 採用單例模式（enum）管理一個固定大小的 ThreadPoolExecutor，適合需要控制並發數量的場景（如 CPU 密集型任務）。
 * <p>
 * 此工具類提供了三種類型的任務執行方法：
 * <ul>
 * <li>{@link #executeTasks(Runnable...)} - 執行無返回值的 Runnable 任務</li>
 * <li>{@link #executeCompletableFutures(CompletableFuture...)} - 執行帶返回值的
 * CompletableFuture 任務</li>
 * <li>{@link #executeSuppliers(Supplier...)} - 執行帶返回值的 Supplier 任務</li>
 * </ul>
 * <p>
 * 所有方法都支持為任務指定前綴名稱，以便在日誌中更容易識別和追蹤任務執行情況。
 * 線程命名格式為：基本名稱(Task-序號) + "-" + 前綴 + "-" + 任務索引，例如 "Task-0-VANCE-1"。
 * <p>
 * 使用示例：
 *
 * <pre>{@code
 * // 執行無返回值的任務
 * CompletableFutureThreadPool.executeTasks("VANCE", runnable1, runnable2);
 *
 * // 執行帶返回值的 Supplier 任務
 * List<String> results = CompletableFutureThreadPool.executeSuppliers("VANCE", supplier1, supplier2);
 * }</pre>
 *
 * @author Vance
 * @version 1.0
 */
@Slf4j
public enum CompletableFutureThreadPool {
	/**
	 * 單例實例，提供全局唯一的線程池訪問點。
	 */
	INSTANCE;

	/**
	 * 線程池關閉的超時時間（單位：秒）。
	 */
	public static final long SHUTDOWN_TIMEOUT_SECONDS = 60;

	/**
	 * 自定義線程池，基於 ThreadPoolExecutor，用於執行並行任務。
	 */
	private final ThreadPoolExecutor singleThreadPool;

	/**
	 * 線程計數器，用於為每個新創建的線程分配唯一的序號。
	 * 從 0 開始遞增，確保每個線程都有唯一的標識符。
	 */
	private final AtomicInteger threadCounter = new AtomicInteger(0);

	/**
	 * 使用 ThreadLocal 存儲線程的基本名稱，確保在線程重用時能夠正確識別原始線程名稱。
	 * 在線程池創建線程時設置，格式為 "Task-序號"，例如 "Task-0"。
	 */
	private static final ThreadLocal<String> BASE_NAME = new ThreadLocal<>();

	/**
	 * 私有構造函數，初始化線程池。
	 * 核心線程數 2，最大線程數 2，空閒超時 30 秒，任務隊列容量 1000。
	 * 使用自定義的線程工廠，為每個線程分配唯一的名稱（"Task-序號"）並存儲到 ThreadLocal 中。
	 */
	CompletableFutureThreadPool() {
		singleThreadPool = new ThreadPoolExecutor(2, 2, 30L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1000),
				r -> {
					String baseName = "Task-" + threadCounter.getAndIncrement();
					Thread t = new Thread(r, baseName);
					BASE_NAME.set(baseName);
					return t;
				});
	}

	// JVM 關閉時自動清理線程池資源
	static {
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			log.info("[線程池] JVM 關閉前自動關閉線程池...");
			INSTANCE.shutdown();
		}));
	}

	/**
	 * 執行多個無返回值的任務，使用線程池控制並發。
	 * 使用默認前綴 "DEFAULT"。
	 *
	 * @param tasks 要執行的任務數組，不能為空
	 * @throws IllegalArgumentException 如果任務數組為空
	 */
	public static void executeTasks(@Nonnull Runnable... tasks) {
		executeTasks("DEFAULT", tasks);
	}

	/**
	 * 執行多個無返回值的任務，使用線程池控制並發，並為每個任務指定前綴名稱。
	 *
	 * @param namePrefix 任務名稱前綴，不能為 null
	 * @param tasks      要執行的任務數組，不能為空
	 * @throws IllegalArgumentException 如果任務數組為空或前綴為 null
	 */
	public static void executeTasks(@Nonnull String namePrefix, @Nonnull Runnable... tasks) {
		if (ObjectUtil.isEmpty(tasks)) {
			logCheckTaskIsEmpty();
			throw new IllegalArgumentException("任務數組不能為空");
		}
		long start = logTaskStart();

		// 將任務轉為 CompletableFuture，使用線程池執行
		CompletableFuture<?>[] futures = IntStream.range(0, tasks.length)
				.mapToObj(i -> CompletableFuture.runAsync(() -> {
					try {
						// 使用通用方法設置線程名稱
						setThreadName(namePrefix, i, null);
						tasks[i].run();
					} catch (Exception e) {
						log.error("[線程池] 任務執行失敗", e);
					}
				}, INSTANCE.singleThreadPool))
				.toArray(CompletableFuture[]::new);

		logThreadPoolStatus(); // 記錄當前線程池狀態
		CompletableFuture.allOf(futures).join(); // 等待所有任務完成
		logTaskEnd(start);
	}

	/**
	 * 執行多個帶返回值的 CompletableFuture 任務，確保使用線程池執行。
	 * 使用默認前綴 "DEFAULT"。
	 *
	 * @param <T>   返回值類型
	 * @param tasks 要執行的 CompletableFuture 任務數組，不能為空
	 * @return 任務執行結果的列表
	 * @throws IllegalArgumentException 如果任務數組為空
	 */
	@SafeVarargs
	public static <T> List<T> executeCompletableFutures(@Nonnull CompletableFuture<T>... tasks) {
		return executeCompletableFutures("DEFAULT", tasks);
	}

	/**
	 * 執行多個帶返回值的 CompletableFuture 任務，確保使用線程池執行，並為每個任務指定前綴名稱。
	 *
	 * @param <T>        返回值類型
	 * @param namePrefix 任務名稱前綴，不能為 null
	 * @param tasks      要執行的 CompletableFuture 任務數組，不能為空
	 * @return 任務執行結果的列表
	 * @throws IllegalArgumentException 如果任務數組為空或前綴為 null
	 */
	@SafeVarargs
	public static <T> List<T> executeCompletableFutures(@Nonnull String namePrefix,
			@Nonnull CompletableFuture<T>... tasks) {
		if (ObjectUtil.isEmpty(tasks)) {
			logCheckTaskIsEmpty();
			return Collections.emptyList();
		}
		long start = logTaskStart();

		// 統一使用線程池執行傳入的 CompletableFuture
		CompletableFuture<?>[] normalizedFutures = IntStream.range(0, tasks.length)
				.mapToObj(i -> tasks[i].thenApplyAsync(result -> {
					// 使用通用方法設置線程名稱
					setThreadName(namePrefix, i, "CompletableFuture");
					return result;
				}, INSTANCE.singleThreadPool))
				.toArray(CompletableFuture[]::new);

		logThreadPoolStatus(); // 記錄當前線程池狀態
		CompletableFuture.allOf(normalizedFutures).join(); // 等待所有任務完成
		List<T> results = Arrays.stream(tasks).map(CompletableFuture::join).collect(Collectors.toList());
		logTaskEnd(start);
		return results;
	}

	/**
	 * 執行多個帶返回值的 Supplier 任務，使用線程池控制並發。
	 * 使用默認前綴 "DEFAULT"。
	 *
	 * @param <T>   返回值類型
	 * @param tasks 要執行的 Supplier 任務數組，不能為空
	 * @return 任務執行結果的列表
	 * @throws IllegalArgumentException 如果任務數組為空
	 */
	@SafeVarargs
	public static <T> List<T> executeSuppliers(@Nonnull Supplier<T>... tasks) {
		return executeSuppliers("DEFAULT", tasks);
	}

	/**
	 * 執行多個帶返回值的 Supplier 任務，使用線程池控制並發，並為每個任務指定前綴名稱。
	 *
	 * @param <T>        返回值類型
	 * @param namePrefix 任務名稱前綴，不能為 null
	 * @param tasks      要執行的 Supplier 任務數組，不能為空
	 * @return 任務執行結果的列表
	 * @throws IllegalArgumentException 如果任務數組為空或前綴為 null
	 */
	@SafeVarargs
	public static <T> List<T> executeSuppliers(@Nonnull String namePrefix, @Nonnull Supplier<T>... tasks) {
		if (ObjectUtil.isEmpty(tasks)) {
			logCheckTaskIsEmpty();
			return Collections.emptyList();
		}
		long start = logTaskStart();

		// 將 Supplier 轉為 CompletableFuture，使用線程池執行
		List<CompletableFuture<T>> futures = IntStream.range(0, tasks.length)
				.mapToObj(i -> CompletableFuture.supplyAsync(() -> {
					try {
						// 使用通用方法設置線程名稱
						setThreadName(namePrefix, i, "Supplier");
						return tasks[i].get();
					} catch (Exception e) {
						log.error("[線程池] Supplier 任務執行失敗", e);
						return null; // 返回 null 以供外部處理
					}
				}, INSTANCE.singleThreadPool))
				.collect(Collectors.toList());

		logThreadPoolStatus(); // 記錄當前線程池狀態
		List<T> results = futures.stream().map(CompletableFuture::join).collect(Collectors.toList());
		logTaskEnd(start);
		return results;
	}

	/**
	 * 記錄線程池當前狀態，包括池大小、活躍線程數、排隊任務數、總任務數和完成任務數。
	 * 同時記錄所有以 "Task-" 開頭的線程的狀態，包括線程 ID、主執行緒名稱、子任務名稱和線程狀態。
	 * <p>
	 * 線程名稱會被解析為三個部分：
	 * <ul>
	 * <li>主執行緒名稱（例如 "Task-0"）</li>
	 * <li>子任務名稱（例如 "VANCE-1"）</li>
	 * <li>如果沒有子任務，則顯示為 "Idle"</li>
	 * </ul>
	 */
	private static void logThreadPoolStatus() {
		log.info("[線程池狀態] 池大小: {}, 活躍線程數: {}, 排隊任務數: {}, 總任務數: {}, 完成任務數: {}",
				INSTANCE.singleThreadPool.getPoolSize(),
				INSTANCE.singleThreadPool.getActiveCount(),
				INSTANCE.singleThreadPool.getQueue().size(),
				INSTANCE.singleThreadPool.getTaskCount(),
				INSTANCE.singleThreadPool.getCompletedTaskCount());
		// 記錄當前執行緒名稱
		ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
		long[] allThreadIds = threadBean.getAllThreadIds();
		for (long tid : allThreadIds) {
			ThreadInfo tinfo = threadBean.getThreadInfo(tid);
			if (Objects.nonNull(tinfo)) {
				String threadName = tinfo.getThreadName();
				// 顯示所有任務線程，包括具名任務
				if (StringUtils.startsWith(threadName, "Task-")) {
					String[] parts = threadName.split("-", 3);
					String mainThreadName = parts.length >= 2 ? parts[0] + "-" + parts[1] : threadName;
					String subName = parts.length == 3 ? parts[2] : "Idle";
					log.info("[執行緒狀態] ID: {}, 主執行緒: {}, 子任務: {}, 狀態: {}",
							tid, mainThreadName, subName, tinfo.getThreadState());
				}
			}
		}
	}

	/**
	 * 設置當前線程的名稱，格式為：基本名稱 + "-" + 前綴 + "-" + 任務索引。
	 * 使用 ThreadLocal 存儲的原始線程名稱作為基本名稱，確保不會重複添加前綴。
	 *
	 * @param namePrefix 任務名稱前綴
	 * @param taskIndex  任務索引
	 * @param taskType   任務類型（用於日誌輸出）
	 * @return 設置後的線程名稱
	 */
	private static String setThreadName(String namePrefix, int taskIndex, String taskType) {
		// 獲取原始的線程名稱（由線程池創建時設置的，例如 "Task-0"）
		String baseName = BASE_NAME.get();

		// 如果 BASE_NAME 為 null，則從當前線程名稱中提取基本名稱
		if (baseName == null) {
			String currentName = Thread.currentThread().getName();
			// 如果當前名稱包含連字符，則提取第一部分和第二部分（例如 "Task-0"）
			if (currentName.contains("-")) {
				String[] parts = currentName.split("-", 3);
				if (parts.length >= 2) {
					baseName = parts[0] + "-" + parts[1];
				} else {
					baseName = currentName;
				}
			} else {
				baseName = currentName;
			}
			// 更新 ThreadLocal 中的值
			BASE_NAME.set(baseName);
		}

		// 設置線程名稱為：基本名稱 + "-" + 前綴 + "-" + 任務索引
		String taskName = baseName + "-" + namePrefix + "-" + taskIndex;
		Thread.currentThread().setName(taskName);

		// 根據任務類型輸出不同的日誌
		if (StringUtils.isBlank(taskType)) {
			log.info("[線程池] 執行任務: {}", taskName);
		} else {
			log.debug("[線程池] 執行 {} 任務: {}", taskType, taskName);
		}

		return taskName;
	}

	/**
	 * 記錄任務為空的情況並輸出錯誤日誌。
	 */
	private static void logCheckTaskIsEmpty() {
		log.error("[線程池][執行任務] 任務數組為空");
	}

	/**
	 * 記錄任務開始時間並返回當前時間戳。
	 *
	 * @return 開始時間（毫秒）
	 */
	private static long logTaskStart() {
		long startTime = System.currentTimeMillis();
		log.info("[線程池][執行任務] 開始時間: {}, 開始執行任務", LocalDateTimeUtil.now());
		return startTime;
	}

	/**
	 * 記錄任務結束時間及總耗時。
	 *
	 * @param start 任務開始時間（毫秒）
	 */
	private static void logTaskEnd(long start) {
		log.info("[線程池][執行任務] 結束時間: {}, 執行任務結束，耗時: {}ms",
				LocalDateTimeUtil.now(), System.currentTimeMillis() - start);
	}

	/**
	 * 關閉線程池，釋放資源，通常由 JVM 關閉鉤子調用。
	 * <p>
	 * 關閉過程：
	 * <ol>
	 * <li>嘗試優雅關閉線程池，等待所有任務完成</li>
	 * <li>如果在指定的超時時間（{@link #SHUTDOWN_TIMEOUT_SECONDS}）內未能關閉，則強制關閉</li>
	 * <li>如果關閉過程被中斷，則強制關閉並恢復中斷狀態</li>
	 * </ol>
	 * <p>
	 * 注意：此方法通常不需要手動調用，因為在 JVM 關閉時會自動調用。
	 */
	public void shutdown() {
		log.info("[線程池] 正在關閉線程池...");
		singleThreadPool.shutdown();
		try {
			if (!singleThreadPool.awaitTermination(SHUTDOWN_TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
				log.warn("[線程池] 線程池未在 {} 秒內關閉，強制關閉", SHUTDOWN_TIMEOUT_SECONDS);
				singleThreadPool.shutdownNow();
			}
			log.info("[線程池] 線程池已關閉");
		} catch (InterruptedException e) {
			log.error("[線程池] 線程池關閉時被中斷", e);
			singleThreadPool.shutdownNow();
			Thread.currentThread().interrupt();
		}
	}
}