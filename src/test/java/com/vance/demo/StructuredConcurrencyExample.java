package com.vance.demo;

import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

/**
 * 結構式並行程式設計範例<br/>
 * 本程式展示如何使用 JDK 21 的 {@link StructuredTaskScope} 實現結構式併發，結合虛擬執行緒（Virtual
 * Threads）執行多個並行任務。
 * 範例中啟動兩個子任務，分別模擬耗時 2 秒和 1 秒的操作，並在所有任務完成後獲取結果。
 * <p>
 * 參考資料: <a href=
 * "https://share.evernote.com/note/cf6f8fa4-ead4-dcf7-bf45-1457dc057af8">Evernote
 * 筆記</a>
 * 
 * @author Vance
 */
@Slf4j
public class StructuredConcurrencyExample {

    /**
     * 主方法，展示結構式併發的用法。
     * 
     * @param args 命令列參數
     */
    public static void main(String[] args) {
        // 使用 StructuredTaskScope 創建一個結構化任務範圍，指定虛擬執行緒工廠以運行子任務
        // 第一個參數 null 表示不使用自訂關閉策略，預設等待所有任務完成
        try (var scope = new StructuredTaskScope<String>(null, Thread.ofVirtual().factory())) {

            // 啟動第一個子任務，模擬耗時 2 秒的操作
            StructuredTaskScope.Subtask<String> task1 = scope.fork(() -> {
                log.info("Task 1 start at {}", System.currentTimeMillis());
                Thread.sleep(TimeUnit.SECONDS.toMillis(2));
                log.info("Task 1 end at {}", System.currentTimeMillis());
                return "Task 1 completed";
            });

            // 啟動第二個子任務，模擬耗時 1 秒的操作
            StructuredTaskScope.Subtask<String> task2 = scope.fork(() -> {
                log.info("Task 2 start at {}", System.currentTimeMillis());
                Thread.sleep(TimeUnit.SECONDS.toMillis(1));
                log.info("Task 2 end at {}", System.currentTimeMillis());
                return "Task 2 completed";
            });

            // 等待所有子任務完成，阻塞主執行緒直到 task1 和 task2 都結束
            // join() 確保結構化範圍內的所有任務執行完畢
            scope.join();

            // 獲取並記錄子任務的結果
            // task1.get() 和 task2.get() 分別返回子任務的執行結果
            log.info("Task 1 result: {}", task1.get());
            log.info("Task 2 result: {}", task2.get());
        } catch (Exception e) {
            // 捕獲並記錄任何異常，例如 InterruptedException 或 ExecutionException
            log.error("執行過程中發生錯誤: {}", e);
        }
    }
}
