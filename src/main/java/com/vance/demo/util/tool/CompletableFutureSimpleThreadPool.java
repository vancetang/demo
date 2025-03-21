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
 * 使用 CompletableFuture 實現簡單的線程池
 */
@Slf4j
public enum CompletableFutureSimpleThreadPool {
	/**
	 * 單例對象
	 */
	INSTANCE;

	/**
	 * 線程池關閉的超時時間（單位：秒）
	 */
	public static final long SHUTDOWN_TIMEOUT_SECONDS = 60;

	/**
	 * 自定義線程池，用於執行任務
	 */
	private final ThreadPoolExecutor singleThreadPool;

	/**
	 * 構造函數，初始化線程池並設置參數
	 */
	CompletableFutureSimpleThreadPool() {
		// 初始化線程池：核心線程數15，最大線程數20，線程空閒超時30秒
		singleThreadPool = new ThreadPoolExecutor(15, 20, 30L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1000),
				r -> new Thread(r, "CompletableFutureUtils - " + r.hashCode()));
	}

	// 在 JVM 關閉時自動關閉線程池
	static {
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			log.info("[線程池] JVM 關閉前自動關閉線程池...");
			INSTANCE.shutdown();
		}));
	}

	/**
	 * 執行多個無返回值的任務
	 *
	 * @param tasks 任務數組，不能為空
	 */
	public static void executeTasks(@Nonnull Runnable... tasks) {
		if (ObjectUtil.isEmpty(tasks)) {
			logCheckTaskIsEmpty();
			return;
		}
		long start = logTaskStart();
		// 使用線程池並行執行多個任務
		CompletableFuture<?>[] futures = Arrays.stream(tasks).map(task -> CompletableFuture.runAsync(() -> {
			try {
				task.run();
			} catch (Exception e) {
				log.error("任務執行失敗", e);
			}
		}, INSTANCE.singleThreadPool)).toArray(CompletableFuture[]::new);
		// 記錄線程池當前狀態
		logThreadPoolStatus();
		// 等待所有任務完成
		CompletableFuture.allOf(futures).join();
		logTaskEnd(start);
	}

	/**
	 * 執行多個帶返回值的 CompletableFuture 任務
	 *
	 * @param tasks 任務數組，不能為空
	 * @return 任務執行結果的列表
	 */
	@SafeVarargs
	public static <T> List<T> executeCompletableFutures(@Nonnull CompletableFuture<T>... tasks) {
		List<T> results;
		if (ObjectUtil.isEmpty(tasks)) {
			logCheckTaskIsEmpty();
			return Collections.emptyList();
		}
		long start = logTaskStart();
		// 等待所有任務完成
		CompletableFuture<Void> allOf = CompletableFuture.allOf(tasks);
		allOf.join();
		// 收集所有任務的結果
		results = Arrays.stream(tasks).map(CompletableFuture::join).collect(Collectors.toList());
		logTaskEnd(start);
		return results;
	}

	/**
	 * 執行多個帶返回值的 Supplier 任務
	 *
	 * @param tasks 任務數組，不能為空
	 * @return 任務執行結果的列表
	 */
	@SafeVarargs
	public static <T> List<T> executeSuppliers(@Nonnull Supplier<T>... tasks) {
		List<T> results;
		if (ObjectUtil.isEmpty(tasks)) {
			logCheckTaskIsEmpty();
			return Collections.emptyList();
		}
		long start = logTaskStart();
		// 將 Supplier 任務轉為 CompletableFuture 並執行
		List<CompletableFuture<T>> futures = Arrays.stream(tasks).map(task -> CompletableFuture.supplyAsync(() -> {
			try {
				return task.get();
			} catch (Exception e) {
				log.error("任務執行失敗", e);
				return null; // 可選擇拋出異常代替返回 null
			}
		}, INSTANCE.singleThreadPool)).collect(Collectors.toList());
		// 記錄線程池狀態
		logThreadPoolStatus();
		// 收集所有任務的結果
		results = futures.stream().map(CompletableFuture::join).collect(Collectors.toList());
		logTaskEnd(start);
		return results;
	}

	/**
	 * 記錄線程池的當前狀態
	 */
	private static void logThreadPoolStatus() {
		log.info("[線程池狀態] 池大小:{}，活躍線程數:{}，排隊任務數:{}，總任務數:{}，完成任務數:{}", INSTANCE.singleThreadPool.getPoolSize(),
				INSTANCE.singleThreadPool.getActiveCount(), INSTANCE.singleThreadPool.getQueue().size(),
				INSTANCE.singleThreadPool.getTaskCount(), INSTANCE.singleThreadPool.getCompletedTaskCount());
	}

	/**
	 * 記錄任務為空的情況
	 */
	private static void logCheckTaskIsEmpty() {
		log.error("[線程池][執行任務] 任務為空");
	}

	/**
	 * 記錄任務開始執行時間
	 *
	 * @return 開始時間（毫秒）
	 */
	private static long logTaskStart() {
		long startTime = System.currentTimeMillis();
		log.info("[線程池][執行任務] 開始時間：{}，開始執行任務", LocalDateTimeUtil.now());
		return startTime;
	}

	/**
	 * 記錄任務結束時間及耗時
	 *
	 * @param start 開始時間（毫秒）
	 */
	private static void logTaskEnd(long start) {
		log.info("[線程池][執行任務] 結束時間：{}，執行任務結束，耗時:{}ms", LocalDateTimeUtil.now(), System.currentTimeMillis() - start);
	}

	/**
	 * 關閉線程池，通常在 JVM 關閉時調用
	 */
	public void shutdown() {
		log.info("[線程池] 正在關閉線程池...");
		singleThreadPool.shutdown();
		try {
			if (!singleThreadPool.awaitTermination(SHUTDOWN_TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
				log.warn("[線程池] 線程池未在規定時間內關閉，強制關閉");
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
