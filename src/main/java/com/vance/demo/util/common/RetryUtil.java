
package com.vance.demo.util.common;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import lombok.extern.slf4j.Slf4j;

/**
 * 通用工具類：提供帶重試的執行機制
 */
@Slf4j
public class RetryUtil {

    public static void main(String[] args) {
        String a = executeWithRetry("測試一下", 3, 1, () -> {
            log.info("開始執行");
            throw new RuntimeException("故意失敗");
        });
        log.info("結果: {}", a);
    }

    /**
     * 執行帶自動重試的任務
     *
     * @param taskName        任務名稱
     * @param maxAttempts     總共最多嘗試幾次
     * @param intervalSeconds 每次重試間隔秒數
     * @param action          業務邏輯
     * @param <T>             回傳型別
     * @return T 成功時的結果
     * @throws RuntimeException 全部失敗時拋出
     */
    public static <T> T executeWithRetry(String taskName, int maxAttempts, long intervalSeconds, Supplier<T> action) {
        // 基本參數檢核
        if (maxAttempts <= 0) {
            throw new IllegalArgumentException("maxAttempts 必須為正數");
        }
        if (intervalSeconds < 0) {
            throw new IllegalArgumentException("intervalSeconds 不可為負數");
        }
        Objects.requireNonNull(action, "action 不可為 null");
        Exception lastException = null;
        for (int i = 1; i <= maxAttempts; i++) {
            try {
                T result = action.get(); // 執行傳進來的真正邏輯
                log.info("[{}] 成功，第 {} 次嘗試（總共 {} 次）", taskName, i, maxAttempts);
                return result; // 成功 → 直接回傳結果並結束方法
            } catch (Exception e) {
                lastException = e;
                log.warn("[{}] 第 {} 次嘗試失敗，原因: {} - {}", taskName, i, e.getClass().getSimpleName(), e.getMessage());
                // 最後一次不需要 sleep
                if (i < maxAttempts) {
                    log.info("[{}] {} 秒後進行第 {} 次重試...", taskName, intervalSeconds, (i + 1));
                    try {
                        TimeUnit.SECONDS.sleep(intervalSeconds);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException(taskName + " - 重試等待被中斷", ie);
                    }
                }
            }
        }
        // 全部失敗
        log.error("[{}] 已嘗試 {} 次全部失敗，任務放棄！", taskName, maxAttempts);
        throw new RuntimeException("[" + taskName + "] 已嘗試 " + maxAttempts + " 次全部失敗！", lastException);
    }
}
