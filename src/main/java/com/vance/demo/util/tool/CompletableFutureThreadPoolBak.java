package com.vance.demo.util.tool;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 使用 CompletableFuture 和傳統平台線程池實現的並行任務處理工具。
 * 採用單例模式（enum）管理一個固定大小的 ThreadPoolExecutor，適合需要控制並發數量的場景（如 CPU 密集型任務）。
 */
@Slf4j
public enum CompletableFutureThreadPoolBak {
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
	 * 私有構造函數，初始化線程池。
	 * 核心線程數 15，最大線程數 20，空閒超時 30 秒，任務隊列容量 1000。
	 */
	CompletableFutureThreadPoolBak() {
		singleThreadPool = new ThreadPoolExecutor(15, 20, 30L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1000),
				r -> new Thread(r, "CompletableFutureThreadPool - " + r.hashCode()));
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
	 *
	 * @param tasks 要執行的任務數組，不能為空
	 * @throws IllegalArgumentException 如果任務數組為空
	 */
	public static void executeTasks(@Nonnull Runnable... tasks) {
		if (ObjectUtil.isEmpty(tasks)) {
			logCheckTaskIsEmpty();
			throw new IllegalArgumentException("任務數組不能為空");
		}
		long start = logTaskStart();

		// 將任務轉為 CompletableFuture，使用線程池執行
		CompletableFuture<?>[] futures = Arrays.stream(tasks)
				.map(task -> CompletableFuture.runAsync(() -> {
					try {
						task.run();
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
	 *
	 * @param <T>   返回值類型
	 * @param tasks 要執行的 CompletableFuture 任務數組，不能為空
	 * @return 任務執行結果的列表
	 * @throws IllegalArgumentException 如果任務數組為空
	 */
	@SafeVarargs
	public static <T> List<T> executeCompletableFutures(@Nonnull CompletableFuture<T>... tasks) {
		if (ObjectUtil.isEmpty(tasks)) {
			logCheckTaskIsEmpty();
			return Collections.emptyList();
		}
		long start = logTaskStart();

		// 統一使用線程池執行傳入的 CompletableFuture
		CompletableFuture<?>[] normalizedFutures = Arrays.stream(tasks)
				.map(task -> task.thenApplyAsync(result -> result, INSTANCE.singleThreadPool))
				.toArray(CompletableFuture[]::new);

		CompletableFuture.allOf(normalizedFutures).join(); // 等待所有任務完成
		List<T> results = Arrays.stream(tasks).map(CompletableFuture::join).collect(Collectors.toList());
		logTaskEnd(start);
		return results;
	}

	/**
	 * 執行多個帶返回值的 Supplier 任務，使用線程池控制並發。
	 *
	 * @param <T>   返回值類型
	 * @param tasks 要執行的 Supplier 任務數組，不能為空
	 * @return 任務執行結果的列表
	 * @throws IllegalArgumentException 如果任務數組為空
	 */
	@SafeVarargs
	public static <T> List<T> executeSuppliers(@Nonnull Supplier<T>... tasks) {
		if (ObjectUtil.isEmpty(tasks)) {
			logCheckTaskIsEmpty();
			return Collections.emptyList();
		}
		long start = logTaskStart();

		// 將 Supplier 轉為 CompletableFuture，使用線程池執行
		List<CompletableFuture<T>> futures = Arrays.stream(tasks)
				.map(task -> CompletableFuture.supplyAsync(() -> {
					try {
						return task.get();
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
	 * 記錄線程池當前狀態，包括池大小、活躍線程數等。
	 */
	private static void logThreadPoolStatus() {
		log.info("[線程池狀態] 池大小: {}, 活躍線程數: {}, 排隊任務數: {}, 總任務數: {}, 完成任務數: {}",
				INSTANCE.singleThreadPool.getPoolSize(),
				INSTANCE.singleThreadPool.getActiveCount(),
				INSTANCE.singleThreadPool.getQueue().size(),
				INSTANCE.singleThreadPool.getTaskCount(),
				INSTANCE.singleThreadPool.getCompletedTaskCount());
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