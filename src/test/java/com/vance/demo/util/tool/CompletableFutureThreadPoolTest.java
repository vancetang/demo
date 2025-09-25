package com.vance.demo.util.tool;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * CompletableFutureSimpleThreadPool 的測試類，用於驗證其多任務並行執行功能。
 */
@Slf4j
public class CompletableFutureThreadPoolTest {

    /**
     * 測試 executeTasks 方法，驗證多個無返回值任務的並行執行。
     * 模擬四個耗時 5 秒的任務，檢查是否正確並行執行並完成。
     */
    @Test
    public void test02() {
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

        // 使用帶前綴的 executeTasks 方法
        CompletableFutureThreadPool.executeTasks("VANCE", run1, run2, run3, run4);

        log.info("test02 執行完畢");
    }

    /**
     * 測試 executeCompletableFutures 方法，驗證帶返回值的 CompletableFuture 任務並行執行。
     * 使用三個模擬耗時任務，檢查結果是否正確返回。
     */
    @Test
    public void test03() {
        // 創建三個 CompletableFuture 任務
        CompletableFuture<String> future1 = CompletableFuture
                .supplyAsync(CompletableFutureThreadPoolTest::getData1);
        CompletableFuture<String> future2 = CompletableFuture
                .supplyAsync(CompletableFutureThreadPoolTest::getData2);
        CompletableFuture<String> future3 = CompletableFuture
                .supplyAsync(CompletableFutureThreadPoolTest::getData3);

        // 並行執行並收集結果
        List<String> res = CompletableFutureThreadPool.executeCompletableFutures(future1, future2, future3);
        log.info("結果: {}", res);
        log.info("test03 執行完畢");
    }

    /**
     * 測試 executeSuppliers 方法，驗證 Supplier 類型任務的並行執行。
     * 使用三個模擬耗時任務，檢查結果是否正確返回。
     */
    @Test
    public void test04() {
        // 並行執行三個 Supplier 任務並收集結果
        List<String> res = CompletableFutureThreadPool.executeSuppliers(
                CompletableFutureThreadPoolTest::getData1,
                CompletableFutureThreadPoolTest::getData2,
                CompletableFutureThreadPoolTest::getData3);
        log.info("結果: {}", res);
        log.info("test04 執行完畢");
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

    /**
     * 測試處理資料夾下所有 .txt 檔案的範例。
     * 掃描指定資料夾，為每個 .txt 檔案建立一個耗時的處理任務，
     * 然後使用 CompletableFutureThreadPool.executeTasks 並行執行。
     */
    @Test
    public void testProcessTxtFiles() {
        // 指定要處理的資料夾路徑（相對於專案根目錄）
        String folderPath = "test-data";

        // 建立測試資料夾和檔案（如果不存在）
        createTestTxtFiles(folderPath);

        // 掃描資料夾並建立處理任務
        List<Runnable> txtProcessingTasks = createTxtProcessingTasks(folderPath);

        if (txtProcessingTasks.isEmpty()) {
            log.warn("在資料夾 {} 中沒有找到 .txt 檔案", folderPath);
            return;
        }

        log.info("找到 {} 個 .txt 檔案，開始並行處理", txtProcessingTasks.size());

        // 使用 CompletableFutureThreadPool 並行執行所有 txt 處理任務
        CompletableFutureThreadPool.executeTasks("TXT_PROCESSOR",
                txtProcessingTasks.toArray(new Runnable[0]));

        log.info("所有 .txt 檔案處理完成");
    }

    /**
     * 建立測試用的 .txt 檔案。
     * 在指定資料夾中建立幾個測試檔案，用於示範檔案處理功能。
     *
     * @param folderPath 資料夾路徑
     */
    private void createTestTxtFiles(String folderPath) {
        try {
            Path folder = Paths.get(folderPath);

            // 建立資料夾（如果不存在）
            if (!Files.exists(folder)) {
                Files.createDirectories(folder);
                log.info("建立測試資料夾: {}", folder.toAbsolutePath());
            }

            // 建立測試檔案
            String[] testFiles = { "file1.txt", "file2.txt", "file3.txt", "document.txt", "readme.md" };
            String[] testContents = {
                    "這是第一個測試檔案的內容\n包含多行文字",
                    "第二個檔案\n用於測試並行處理",
                    "第三個檔案內容\n模擬實際的文字處理場景",
                    "這是一個文件檔案\n包含重要資訊",
                    "這是 markdown 檔案，不會被處理"
            };

            for (int i = 0; i < testFiles.length; i++) {
                Path filePath = folder.resolve(testFiles[i]);
                if (!Files.exists(filePath)) {
                    Files.write(filePath, testContents[i].getBytes("UTF-8"));
                    log.debug("建立測試檔案: {}", filePath.toAbsolutePath());
                }
            }

        } catch (IOException e) {
            log.error("建立測試檔案時發生錯誤", e);
        }
    }

    /**
     * 掃描指定資料夾，為每個 .txt 檔案建立處理任務。
     * 每個任務會模擬耗時的檔案處理操作（如文字分析、格式轉換等）。
     *
     * @param folderPath 要掃描的資料夾路徑
     * @return 檔案處理任務列表
     */
    private List<Runnable> createTxtProcessingTasks(String folderPath) {
        List<Runnable> tasks = new ArrayList<>();

        try {
            Path folder = Paths.get(folderPath);

            if (!Files.exists(folder) || !Files.isDirectory(folder)) {
                log.warn("資料夾不存在或不是目錄: {}", folder.toAbsolutePath());
                return tasks;
            }

            // 使用 Files.walk 遞迴掃描資料夾（包含子資料夾）
            try (Stream<Path> paths = Files.walk(folder)) {
                paths.filter(Files::isRegularFile) // 只處理一般檔案
                        .filter(path -> path.toString().toLowerCase().endsWith(".txt")) // 只處理 .txt 檔案
                        .forEach(txtFile -> {
                            // 為每個 .txt 檔案建立一個處理任務
                            Runnable processingTask = createFileProcessingTask(txtFile);
                            tasks.add(processingTask);
                        });
            }

        } catch (IOException e) {
            log.error("掃描資料夾時發生錯誤: {}", folderPath, e);
        }

        return tasks;
    }

    /**
     * 為單一檔案建立處理任務。
     * 這個方法展示如何將檔案處理邏輯封裝成 Runnable，
     * 以便在 CompletableFutureThreadPool 中並行執行。
     *
     * @param filePath 要處理的檔案路徑
     * @return 檔案處理任務
     */
    private Runnable createFileProcessingTask(Path filePath) {
        return () -> {
            try {
                String fileName = filePath.getFileName().toString();
                log.info("開始處理檔案: {}", fileName);

                // 讀取檔案內容
                List<String> lines = Files.readAllLines(filePath);
                log.debug("檔案 {} 包含 {} 行內容", fileName, lines.size());

                // 模擬耗時的檔案處理操作
                // 在實際應用中，這裡可能是：
                // - 文字分析和處理
                // - 格式轉換
                // - 資料提取
                // - 檔案壓縮或加密
                // - 上傳到雲端儲存
                simulateTimeConsumingFileProcessing(fileName, lines);

                // 模擬處理結果的儲存
                saveProcessingResult(filePath, lines);

                log.info("檔案處理完成: {}", fileName);

            } catch (IOException e) {
                log.error("處理檔案時發生錯誤: {}", filePath, e);
            } catch (InterruptedException e) {
                log.warn("檔案處理被中斷: {}", filePath);
                Thread.currentThread().interrupt();
            }
        };
    }

    /**
     * 模擬耗時的檔案處理操作。
     * 根據檔案內容的複雜度決定處理時間。
     *
     * @param fileName 檔案名稱
     * @param lines    檔案內容行數
     * @throws InterruptedException 如果處理被中斷
     */
    private void simulateTimeConsumingFileProcessing(String fileName, List<String> lines)
            throws InterruptedException {

        // 根據檔案大小決定處理時間（模擬真實場景）
        int processingTimeSeconds = Math.max(2, lines.size()); // 最少 2 秒，每行增加 1 秒

        log.info("檔案 {} 預計處理時間: {} 秒", fileName, processingTimeSeconds);

        // 分段處理，模擬實際的處理進度
        for (int i = 0; i < processingTimeSeconds; i++) {
            Thread.sleep(1000); // 每秒處理一部分

            // 計算處理進度
            int progress = (int) ((double) (i + 1) / processingTimeSeconds * 100);
            log.debug("檔案 {} 處理進度: {}%", fileName, progress);
        }
    }

    /**
     * 儲存檔案處理結果。
     * 在實際應用中，這裡可能會將處理結果寫入新檔案、資料庫或發送到其他系統。
     *
     * @param originalFile   原始檔案路徑
     * @param processedLines 處理後的內容
     */
    private void saveProcessingResult(Path originalFile, List<String> processedLines) {
        try {
            // 建立處理結果檔案路徑（在原檔案名後加上 .processed）
            String originalFileName = originalFile.getFileName().toString();
            String resultFileName = originalFileName.replace(".txt", ".processed.txt");
            Path resultFile = originalFile.getParent().resolve(resultFileName);

            // 模擬處理結果（在每行前加上處理時間戳）
            List<String> processedContent = new ArrayList<>();
            String timestamp = java.time.LocalDateTime.now().toString();
            processedContent.add("處理時間: " + timestamp);
            processedContent.add("原始檔案: " + originalFileName);
            processedContent.add("處理執行緒: " + Thread.currentThread().getName());
            processedContent.add("--- 處理後內容 ---");

            for (int i = 0; i < processedLines.size(); i++) {
                processedContent.add(String.format("[行%d] %s", i + 1, processedLines.get(i)));
            }

            // 寫入處理結果
            Files.write(resultFile, processedContent);
            log.info("處理結果已儲存至: {}", resultFile.getFileName());

        } catch (IOException e) {
            log.error("儲存處理結果時發生錯誤", e);
        }
    }

    /**
     * 進階範例：處理大量檔案的實際應用場景。
     * 展示如何處理不同類型的檔案處理任務，包括錯誤處理和結果統計。
     */
    @Test
    public void testAdvancedFileProcessing() {
        String folderPath = "test-data-advanced";

        // 建立更複雜的測試資料
        createAdvancedTestFiles(folderPath);

        // 建立不同類型的處理任務
        List<Runnable> processingTasks = createAdvancedProcessingTasks(folderPath);

        if (processingTasks.isEmpty()) {
            log.warn("沒有找到需要處理的檔案");
            return;
        }

        log.info("準備處理 {} 個檔案，使用進階處理邏輯", processingTasks.size());

        // 記錄開始時間
        long startTime = System.currentTimeMillis();

        // 並行執行所有處理任務
        CompletableFutureThreadPool.executeTasks("ADVANCED_PROCESSOR",
                processingTasks.toArray(new Runnable[0]));

        // 計算總處理時間
        long totalTime = System.currentTimeMillis() - startTime;
        log.info("進階檔案處理完成，總耗時: {} 毫秒", totalTime);

        // 統計處理結果
        logProcessingStatistics(folderPath);
    }

    /**
     * 建立進階測試檔案，包含不同大小和類型的檔案。
     *
     * @param folderPath 資料夾路徑
     */
    private void createAdvancedTestFiles(String folderPath) {
        try {
            Path folder = Paths.get(folderPath);
            Files.createDirectories(folder);

            // 建立不同大小的檔案來測試處理時間的差異
            createTestFile(folder, "small.txt", generateContent(5));
            createTestFile(folder, "medium.txt", generateContent(15));
            createTestFile(folder, "large.txt", generateContent(25));
            createTestFile(folder, "config.txt", "配置檔案內容\nkey=value\nsetting=true");
            createTestFile(folder, "data.txt", generateDataContent());
            createTestFile(folder, "log.txt", generateLogContent());

            // 建立一些非 .txt 檔案（不會被處理）
            createTestFile(folder, "image.jpg", "假的圖片檔案內容");
            createTestFile(folder, "script.py", "print('Hello Python')");

        } catch (IOException e) {
            log.error("建立進階測試檔案時發生錯誤", e);
        }
    }

    /**
     * 建立單一測試檔案。
     *
     * @param folder   資料夾路徑
     * @param fileName 檔案名稱
     * @param content  檔案內容
     * @throws IOException 如果檔案建立失敗
     */
    private void createTestFile(Path folder, String fileName, String content) throws IOException {
        Path filePath = folder.resolve(fileName);
        if (!Files.exists(filePath)) {
            Files.write(filePath, content.getBytes("UTF-8"));
            log.debug("建立測試檔案: {} (大小: {} 字元)", fileName, content.length());
        }
    }

    /**
     * 產生指定行數的測試內容。
     *
     * @param lines 行數
     * @return 測試內容
     */
    private String generateContent(int lines) {
        StringBuilder content = new StringBuilder();
        for (int i = 1; i <= lines; i++) {
            content.append(String.format("這是第 %d 行內容，用於測試檔案處理功能。\n", i));
        }
        return content.toString();
    }

    /**
     * 產生模擬資料檔案內容。
     *
     * @return 資料檔案內容
     */
    private String generateDataContent() {
        StringBuilder content = new StringBuilder();
        content.append("姓名,年齡,城市\n");
        content.append("張三,25,台北\n");
        content.append("李四,30,台中\n");
        content.append("王五,28,高雄\n");
        content.append("趙六,35,桃園\n");
        return content.toString();
    }

    /**
     * 產生模擬日誌檔案內容。
     *
     * @return 日誌檔案內容
     */
    private String generateLogContent() {
        StringBuilder content = new StringBuilder();
        content.append("2025-09-25 10:00:00 [INFO] 應用程式啟動\n");
        content.append("2025-09-25 10:01:00 [DEBUG] 載入配置檔案\n");
        content.append("2025-09-25 10:02:00 [WARN] 記憶體使用率較高\n");
        content.append("2025-09-25 10:03:00 [ERROR] 資料庫連線失敗\n");
        content.append("2025-09-25 10:04:00 [INFO] 重新連線成功\n");
        return content.toString();
    }

    /**
     * 建立進階處理任務，根據檔案類型採用不同的處理策略。
     *
     * @param folderPath 資料夾路徑
     * @return 處理任務列表
     */
    private List<Runnable> createAdvancedProcessingTasks(String folderPath) {
        List<Runnable> tasks = new ArrayList<>();

        try {
            Path folder = Paths.get(folderPath);

            if (!Files.exists(folder)) {
                return tasks;
            }

            try (Stream<Path> paths = Files.walk(folder)) {
                paths.filter(Files::isRegularFile)
                        .filter(path -> path.toString().toLowerCase().endsWith(".txt"))
                        .forEach(txtFile -> {
                            Runnable task = createAdvancedFileProcessingTask(txtFile);
                            tasks.add(task);
                        });
            }

        } catch (IOException e) {
            log.error("建立進階處理任務時發生錯誤: {}", folderPath, e);
        }

        return tasks;
    }

    /**
     * 建立進階檔案處理任務，根據檔案名稱和內容採用不同的處理策略。
     *
     * @param filePath 檔案路徑
     * @return 檔案處理任務
     */
    private Runnable createAdvancedFileProcessingTask(Path filePath) {
        return () -> {
            try {
                String fileName = filePath.getFileName().toString();
                log.info("開始進階處理檔案: {}", fileName);

                // 讀取檔案內容
                List<String> lines = Files.readAllLines(filePath);

                // 根據檔案名稱決定處理策略
                ProcessingStrategy strategy = determineProcessingStrategy(fileName, lines);

                // 執行對應的處理邏輯
                ProcessingResult result = executeProcessingStrategy(fileName, lines, strategy);

                // 儲存處理結果
                saveAdvancedProcessingResult(filePath, result);

                log.info("檔案 {} 處理完成，策略: {}, 耗時: {} 毫秒",
                        fileName, strategy.name(), result.getProcessingTime());

            } catch (Exception e) {
                log.error("進階處理檔案時發生錯誤: {}", filePath, e);
            }
        };
    }

    /**
     * 處理策略枚舉。
     */
    private enum ProcessingStrategy {
        TEXT_ANALYSIS, // 文字分析
        DATA_PROCESSING, // 資料處理
        LOG_PARSING, // 日誌解析
        CONFIG_VALIDATION, // 配置驗證
        GENERAL_PROCESSING // 一般處理
    }

    /**
     * 處理結果類別。
     */
    private static class ProcessingResult {
        private final String fileName;
        private final ProcessingStrategy strategy;
        private final List<String> processedContent;
        private final long processingTime;
        private final int originalLines;
        private final int processedLines;

        public ProcessingResult(String fileName, ProcessingStrategy strategy,
                List<String> processedContent, long processingTime,
                int originalLines, int processedLines) {
            this.fileName = fileName;
            this.strategy = strategy;
            this.processedContent = processedContent;
            this.processingTime = processingTime;
            this.originalLines = originalLines;
            this.processedLines = processedLines;
        }

        // Getter 方法
        public String getFileName() {
            return fileName;
        }

        public ProcessingStrategy getStrategy() {
            return strategy;
        }

        public List<String> getProcessedContent() {
            return processedContent;
        }

        public long getProcessingTime() {
            return processingTime;
        }

        public int getOriginalLines() {
            return originalLines;
        }

        public int getProcessedLines() {
            return processedLines;
        }
    }

    /**
     * 根據檔案名稱和內容決定處理策略。
     *
     * @param fileName 檔案名稱
     * @param lines    檔案內容
     * @return 處理策略
     */
    private ProcessingStrategy determineProcessingStrategy(String fileName, List<String> lines) {
        String lowerFileName = fileName.toLowerCase();

        if (lowerFileName.contains("config")) {
            return ProcessingStrategy.CONFIG_VALIDATION;
        } else if (lowerFileName.contains("data")) {
            return ProcessingStrategy.DATA_PROCESSING;
        } else if (lowerFileName.contains("log")) {
            return ProcessingStrategy.LOG_PARSING;
        } else if (lowerFileName.contains("large") || lines.size() > 20) {
            return ProcessingStrategy.TEXT_ANALYSIS;
        } else {
            return ProcessingStrategy.GENERAL_PROCESSING;
        }
    }

    /**
     * 執行對應的處理策略。
     *
     * @param fileName 檔案名稱
     * @param lines    原始內容
     * @param strategy 處理策略
     * @return 處理結果
     */
    private ProcessingResult executeProcessingStrategy(String fileName, List<String> lines,
            ProcessingStrategy strategy) {
        long startTime = System.currentTimeMillis();
        List<String> processedContent = new ArrayList<>();

        try {
            switch (strategy) {
                case TEXT_ANALYSIS:
                    processedContent = performTextAnalysis(lines);
                    Thread.sleep(3000); // 模擬文字分析耗時
                    break;

                case DATA_PROCESSING:
                    processedContent = performDataProcessing(lines);
                    Thread.sleep(2000); // 模擬資料處理耗時
                    break;

                case LOG_PARSING:
                    processedContent = performLogParsing(lines);
                    Thread.sleep(1500); // 模擬日誌解析耗時
                    break;

                case CONFIG_VALIDATION:
                    processedContent = performConfigValidation(lines);
                    Thread.sleep(1000); // 模擬配置驗證耗時
                    break;

                case GENERAL_PROCESSING:
                default:
                    processedContent = performGeneralProcessing(lines);
                    Thread.sleep(2000); // 模擬一般處理耗時
                    break;
            }
        } catch (InterruptedException e) {
            log.warn("處理策略執行被中斷: {}", strategy);
            Thread.currentThread().interrupt();
        }

        long processingTime = System.currentTimeMillis() - startTime;

        return new ProcessingResult(fileName, strategy, processedContent,
                processingTime, lines.size(), processedContent.size());
    }

    /**
     * 執行文字分析處理。
     *
     * @param lines 原始內容
     * @return 處理後內容
     */
    private List<String> performTextAnalysis(List<String> lines) {
        List<String> result = new ArrayList<>();
        result.add("=== 文字分析報告 ===");
        result.add("總行數: " + lines.size());
        result.add("總字數: " + lines.stream().mapToInt(String::length).sum());
        result.add("平均每行字數: " + (lines.isEmpty() ? 0 : lines.stream().mapToInt(String::length).sum() / lines.size()));
        result.add("");
        result.add("=== 內容摘要 ===");

        for (int i = 0; i < Math.min(lines.size(), 5); i++) {
            result.add(String.format("行 %d: %s", i + 1,
                    lines.get(i).length() > 50 ? lines.get(i).substring(0, 50) + "..." : lines.get(i)));
        }

        return result;
    }

    /**
     * 執行資料處理。
     *
     * @param lines 原始內容
     * @return 處理後內容
     */
    private List<String> performDataProcessing(List<String> lines) {
        List<String> result = new ArrayList<>();
        result.add("=== 資料處理報告 ===");
        result.add("處理時間: " + java.time.LocalDateTime.now());
        result.add("資料行數: " + lines.size());
        result.add("");
        result.add("=== 處理後資料 ===");

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.contains(",")) {
                // 假設是 CSV 格式，進行簡單的資料清理
                String[] parts = line.split(",");
                String processedLine = String.join(" | ", parts);
                result.add(String.format("記錄 %d: %s", i + 1, processedLine));
            } else {
                result.add(String.format("文字 %d: %s", i + 1, line));
            }
        }

        return result;
    }

    /**
     * 執行日誌解析。
     *
     * @param lines 原始內容
     * @return 處理後內容
     */
    private List<String> performLogParsing(List<String> lines) {
        List<String> result = new ArrayList<>();
        result.add("=== 日誌解析報告 ===");

        int infoCount = 0, warnCount = 0, errorCount = 0, debugCount = 0;

        for (String line : lines) {
            if (line.contains("[INFO]"))
                infoCount++;
            else if (line.contains("[WARN]"))
                warnCount++;
            else if (line.contains("[ERROR]"))
                errorCount++;
            else if (line.contains("[DEBUG]"))
                debugCount++;
        }

        result.add("INFO 訊息數量: " + infoCount);
        result.add("WARN 訊息數量: " + warnCount);
        result.add("ERROR 訊息數量: " + errorCount);
        result.add("DEBUG 訊息數量: " + debugCount);
        result.add("");
        result.add("=== 錯誤訊息詳情 ===");

        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).contains("[ERROR]")) {
                result.add(String.format("行 %d: %s", i + 1, lines.get(i)));
            }
        }

        return result;
    }

    /**
     * 執行配置驗證。
     *
     * @param lines 原始內容
     * @return 處理後內容
     */
    private List<String> performConfigValidation(List<String> lines) {
        List<String> result = new ArrayList<>();
        result.add("=== 配置檔案驗證報告 ===");
        result.add("驗證時間: " + java.time.LocalDateTime.now());
        result.add("");

        boolean hasErrors = false;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty() || line.startsWith("#")) {
                continue; // 跳過空行和註解
            }

            if (line.contains("=")) {
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    result.add(String.format("✓ 行 %d: %s = %s", i + 1, parts[0].trim(), parts[1].trim()));
                } else {
                    result.add(String.format("✗ 行 %d: 格式錯誤 - %s", i + 1, line));
                    hasErrors = true;
                }
            } else {
                result.add(String.format("? 行 %d: 未知格式 - %s", i + 1, line));
            }
        }

        result.add("");
        result.add("驗證結果: " + (hasErrors ? "發現錯誤" : "通過驗證"));

        return result;
    }

    /**
     * 執行一般處理。
     *
     * @param lines 原始內容
     * @return 處理後內容
     */
    private List<String> performGeneralProcessing(List<String> lines) {
        List<String> result = new ArrayList<>();
        result.add("=== 一般檔案處理報告 ===");
        result.add("處理時間: " + java.time.LocalDateTime.now());
        result.add("處理執行緒: " + Thread.currentThread().getName());
        result.add("原始行數: " + lines.size());
        result.add("");
        result.add("=== 處理後內容 ===");

        for (int i = 0; i < lines.size(); i++) {
            result.add(String.format("[%03d] %s", i + 1, lines.get(i)));
        }

        return result;
    }

    /**
     * 儲存進階處理結果。
     *
     * @param originalFile 原始檔案路徑
     * @param result       處理結果
     */
    private void saveAdvancedProcessingResult(Path originalFile, ProcessingResult result) {
        try {
            String originalFileName = originalFile.getFileName().toString();
            String resultFileName = originalFileName.replace(".txt", ".advanced.txt");
            Path resultFile = originalFile.getParent().resolve(resultFileName);

            List<String> finalContent = new ArrayList<>();
            finalContent.add("=== 進階檔案處理結果 ===");
            finalContent.add("原始檔案: " + result.getFileName());
            finalContent.add("處理策略: " + result.getStrategy().name());
            finalContent.add("處理時間: " + result.getProcessingTime() + " 毫秒");
            finalContent.add("原始行數: " + result.getOriginalLines());
            finalContent.add("處理後行數: " + result.getProcessedLines());
            finalContent.add("處理執行緒: " + Thread.currentThread().getName());
            finalContent.add("完成時間: " + java.time.LocalDateTime.now());
            finalContent.add("");
            finalContent.addAll(result.getProcessedContent());

            Files.write(resultFile, finalContent);
            log.debug("進階處理結果已儲存至: {}", resultFile.getFileName());

        } catch (IOException e) {
            log.error("儲存進階處理結果時發生錯誤", e);
        }
    }

    /**
     * 記錄處理統計資訊。
     *
     * @param folderPath 資料夾路徑
     */
    private void logProcessingStatistics(String folderPath) {
        try {
            Path folder = Paths.get(folderPath);

            if (!Files.exists(folder)) {
                log.warn("統計資料夾不存在: {}", folderPath);
                return;
            }

            log.info("=== 處理統計報告 ===");

            // 統計原始檔案
            long txtFileCount = 0;
            long totalOriginalSize = 0;

            // 統計處理結果檔案
            long processedFileCount = 0;
            long totalProcessedSize = 0;

            try (Stream<Path> paths = Files.walk(folder)) {
                List<Path> allFiles = paths.filter(Files::isRegularFile).collect(Collectors.toList());

                for (Path file : allFiles) {
                    String fileName = file.getFileName().toString().toLowerCase();
                    long fileSize = Files.size(file);

                    if (fileName.endsWith(".txt") && !fileName.contains("processed")
                            && !fileName.contains("advanced")) {
                        txtFileCount++;
                        totalOriginalSize += fileSize;
                    } else if (fileName.contains("advanced.txt")) {
                        processedFileCount++;
                        totalProcessedSize += fileSize;
                    }
                }
            }

            log.info("原始 .txt 檔案數量: {}", txtFileCount);
            log.info("原始檔案總大小: {} 位元組", totalOriginalSize);
            log.info("處理結果檔案數量: {}", processedFileCount);
            log.info("處理結果總大小: {} 位元組", totalProcessedSize);

            if (txtFileCount > 0) {
                log.info("平均原始檔案大小: {} 位元組", totalOriginalSize / txtFileCount);
            }

            if (processedFileCount > 0) {
                log.info("平均處理結果檔案大小: {} 位元組", totalProcessedSize / processedFileCount);
                double expansionRatio = totalOriginalSize > 0 ? (double) totalProcessedSize / totalOriginalSize : 0;
                log.info("檔案擴展比例: {:.2f}", expansionRatio);
            }

            log.info("=== 統計報告結束 ===");

        } catch (IOException e) {
            log.error("統計處理結果時發生錯誤", e);
        }
    }
}