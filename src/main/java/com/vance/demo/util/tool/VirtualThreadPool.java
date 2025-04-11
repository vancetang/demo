package com.vance.demo.util.tool;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import cn.hutool.core.date.LocalDateTimeUtil;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * 使用 CompletableFuture 和 Java 21 虛擬線程實現的並行任務處理工具。
 * 基於虛擬線程執行器（VirtualThreadPerTaskExecutor），每個任務動態分配一個虛擬線程，
 * 適合高並發、I/O 密集型場景，無需手動管理線程生命週期。
 */
@Slf4j
@UtilityClass
public class VirtualThreadPool {
    /**
     * 虛擬線程執行器，為每個任務創建一個新的虛擬線程，由 JVM 自動管理。
     */
    private static final ExecutorService VIRTUAL_EXECUTOR = Executors.newVirtualThreadPerTaskExecutor();

    /**
     * 執行多個無返回值的任務，使用虛擬線程實現高並發。
     *
     * @param tasks 要執行的任務數組，不能為空
     * @throws IllegalArgumentException 如果任務數組為空
     */
    public static void executeTasks(@Nonnull Runnable... tasks) {
        if (tasks.length == 0) {
            log.error("[虛擬線程] 任務數組為空，無法執行");
            throw new IllegalArgumentException("任務數組不能為空");
        }
        long start = logTaskStart();

        // 將任務轉為 CompletableFuture，使用虛擬線程執行
        CompletableFuture<?>[] futures = Arrays.stream(tasks)
                .map(task -> CompletableFuture.runAsync(() -> {
                    try {
                        task.run();
                    } catch (Exception e) {
                        log.error("[虛擬線程] 任務執行失敗", e);
                        // 拋出異常以供外部處理
                        throw e;
                    }
                }, VIRTUAL_EXECUTOR))
                .toArray(CompletableFuture[]::new);
        // 等待所有任務完成
        CompletableFuture.allOf(futures).join();
        logTaskEnd(start);
    }

    /**
     * 執行多個帶返回值的 CompletableFuture 任務，統一使用虛擬線程執行器。
     *
     * @param <T>   返回值類型
     * @param tasks 要執行的 CompletableFuture 任務數組，不能為空
     * @return 任務執行結果的列表
     * @throws IllegalArgumentException 如果任務數組為空
     */
    @SafeVarargs
    public static <T> List<T> executeCompletableFutures(@Nonnull CompletableFuture<T>... tasks) {
        if (tasks.length == 0) {
            log.error("[虛擬線程] CompletableFuture 任務數組為空，返回空列表");
            return Collections.emptyList();
        }
        long start = logTaskStart();

        // 統一使用虛擬線程執行傳入的 CompletableFuture
        CompletableFuture<?>[] normalizedFutures = Arrays.stream(tasks)
                .map(task -> task.thenApplyAsync(result -> result, VIRTUAL_EXECUTOR))
                .toArray(CompletableFuture[]::new);

        // 等待所有任務完成
        CompletableFuture.allOf(normalizedFutures).join();
        List<T> results = Arrays.stream(tasks).map(CompletableFuture::join).collect(Collectors.toList());
        logTaskEnd(start);
        return results;
    }

    /**
     * 執行多個帶返回值的 Supplier 任務，使用虛擬線程實現高並發。
     *
     * @param <T>   返回值類型
     * @param tasks 要執行的 Supplier 任務數組，不能為空
     * @return 任務執行結果的列表
     * @throws IllegalArgumentException 如果任務數組為空
     */
    @SafeVarargs
    public static <T> List<T> executeSuppliers(@Nonnull Supplier<T>... tasks) {
        if (tasks.length == 0) {
            log.error("[虛擬線程] Supplier 任務數組為空，返回空列表");
            return Collections.emptyList();
        }
        long start = logTaskStart();

        // 將 Supplier 轉為 CompletableFuture，使用虛擬線程執行
        List<CompletableFuture<T>> futures = Arrays.stream(tasks)
                .map(task -> CompletableFuture.supplyAsync(() -> {
                    try {
                        return task.get();
                    } catch (Exception e) {
                        log.error("[虛擬線程] Supplier 任務執行失敗", e);
                        // 拋出異常以供外部處理
                        throw e;
                    }
                }, VIRTUAL_EXECUTOR))
                .collect(Collectors.toList());

        List<T> results = futures.stream().map(CompletableFuture::join).collect(Collectors.toList());
        logTaskEnd(start);
        return results;
    }

    /**
     * 記錄任務開始時間並返回當前時間戳。
     *
     * @return 開始時間（毫秒）
     */
    private static long logTaskStart() {
        long startTime = System.currentTimeMillis();
        log.info("[虛擬線程][執行任務] 開始時間: {}, 開始執行任務", LocalDateTimeUtil.now());
        return startTime;
    }

    /**
     * 記錄任務結束時間及總耗時。
     *
     * @param start 任務開始時間（毫秒）
     */
    private static void logTaskEnd(long start) {
        log.info("[虛擬線程][執行任務] 結束時間: {}, 執行任務結束，耗時: {}ms",
                LocalDateTimeUtil.now(), System.currentTimeMillis() - start);
    }
}