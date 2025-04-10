package com.vance.demo.util.tool;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * CompletableFutureSimpleThreadPool 的測試類，用於驗證其多任務並行執行功能。
 */
@Slf4j
public class VirtualThreadPoolTest {

    /**
     * 測試 executeTasks 方法，驗證多個無返回值任務的並行執行。
     * 模擬四個耗時 5 秒的任務，檢查是否正確並行執行並完成。
     */
    @Test
    public void test01() {
        // 定義第一個任務，模擬耗時操作
        Runnable run1 = () -> {
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(5)); // 模擬 5 秒耗時
            } catch (InterruptedException e) {
                log.error("{}", e);
            }
            log.info("run1");
        };
        // 定義第二個任務，模擬耗時操作
        Runnable run2 = () -> {
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(5));
            } catch (InterruptedException e) {
                log.error("{}", e);
            }
            log.info("run2");
        };
        // 定義第三個任務，模擬耗時操作
        Runnable run3 = () -> {
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(5));
            } catch (InterruptedException e) {
                log.error("{}", e);
            }
            log.info("run3");
        };
        // 定義第四個任務，模擬耗時操作
        Runnable run4 = () -> {
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(5));
            } catch (InterruptedException e) {
                log.error("{}", e);
            }
            log.info("run4");
        };

        // 執行多個任務並等待完成
        VirtualThreadPool.executeTasks(run1, run2, run3, run4);
        log.info("test01 執行完畢");
    }

    /**
     * 測試 executeCompletableFutures 方法，驗證帶返回值的 CompletableFuture 任務並行執行。
     * 使用三個模擬耗時任務，檢查結果是否正確返回。
     */
    @Test
    public void test02() {
        // 創建三個 CompletableFuture 任務
        CompletableFuture<String> future1 = CompletableFuture
                .supplyAsync(VirtualThreadPoolTest::getData1);
        CompletableFuture<String> future2 = CompletableFuture
                .supplyAsync(VirtualThreadPoolTest::getData2);
        CompletableFuture<String> future3 = CompletableFuture
                .supplyAsync(VirtualThreadPoolTest::getData3);

        // 並行執行並收集結果
        List<String> res = VirtualThreadPool.executeCompletableFutures(future1, future2, future3);
        log.info("結果: {}", res);
        log.info("test02 執行完畢");
    }

    /**
     * 測試 executeSuppliers 方法，驗證 Supplier 類型任務的並行執行。
     * 使用三個模擬耗時任務，檢查結果是否正確返回。
     */
    @Test
    public void test03() {
        // 並行執行三個 Supplier 任務並收集結果
        List<String> res = VirtualThreadPool.executeSuppliers(
                VirtualThreadPoolTest::getData1,
                VirtualThreadPoolTest::getData2,
                VirtualThreadPoolTest::getData3);
        log.info("結果: {}", res);
        log.info("test03 執行完畢");
    }

    /**
     * 模擬獲取資料的方法，返回 "data1"，模擬 5 秒耗時。
     * 
     * @return "data1"
     */
    public static String getData1() {
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(5)); // 模擬 5 秒耗時
        } catch (InterruptedException e) {
            log.error("{}", e);
        }
        log.info("data1 執行完畢");
        return "data1";
    }

    /**
     * 模擬獲取資料的方法，返回 "data2"，模擬 5 秒耗時。
     * 
     * @return "data2"
     */
    public static String getData2() {
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(5)); // 模擬 5 秒耗時
        } catch (InterruptedException e) {
            log.error("{}", e);
        }
        log.info("data2 執行完畢");
        return "data2";
    }

    /**
     * 模擬獲取資料的方法，返回 "data3"，模擬 6 秒耗時。
     * 
     * @return "data3"
     */
    public static String getData3() {
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(6)); // 模擬 6 秒耗時
        } catch (InterruptedException e) {
            log.error("{}", e);
        }
        log.info("data3 執行完畢");
        return "data3";
    }
}