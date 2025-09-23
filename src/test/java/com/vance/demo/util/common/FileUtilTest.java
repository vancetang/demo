package com.vance.demo.util.common;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import lombok.extern.slf4j.Slf4j;

/**
 * FileUtil 測試類別
 *
 * @author Vance
 */
@Slf4j
@DisplayName("FileUtil 測試")
public class FileUtilTest {

    @TempDir
    Path tempDir;

    private File testFile;
    private String testContent;
    private byte[] testBytes;
    private String testBase64;

    /**
     * 測試前準備
     */
    @BeforeEach
    void setUp() throws IOException {
        // 準備測試資料
        testContent = "Hello, World! 這是測試內容。";
        testBytes = testContent.getBytes(StandardCharsets.UTF_8);
        testBase64 = java.util.Base64.getEncoder().encodeToString(testBytes);

        // 建立測試檔案
        testFile = tempDir.resolve("test.txt").toFile();
        FileUtils.writeStringToFile(testFile, testContent, StandardCharsets.UTF_8);

        log.info("測試準備完成 - 臨時目錄: {}", tempDir);
    }

    /**
     * 測試後清理
     */
    @AfterEach
    void tearDown() {
        log.info("測試清理完成");
    }

    /**
     * 測試檔案轉 Base64
     */
    @Test
    @DisplayName("測試檔案轉 Base64")
    void testFileToBase64() {
        // When
        String result = FileUtil.fileToBase64(testFile);

        // Then
        assertNotNull(result, "Base64 結果不應為 null");
        assertFalse(result.isEmpty(), "Base64 結果不應為空字串");
        assertEquals(testBase64, result, "Base64 編碼結果應該相符");

        log.info("檔案轉 Base64 測試通過");
    }

    /**
     * 測試 Base64 轉檔案
     */
    @Test
    @DisplayName("測試 Base64 轉檔案")
    void testBase64ToFile() throws IOException {
        // Given
        File outputFile = tempDir.resolve("output.txt").toFile();

        // When
        FileUtil.base64ToFile(testBase64, outputFile);

        // Then
        assertTrue(outputFile.exists(), "輸出檔案應該存在");
        assertTrue(outputFile.length() > 0, "輸出檔案不應為空");

        String actualContent = FileUtils.readFileToString(outputFile, StandardCharsets.UTF_8);
        assertEquals(testContent, actualContent, "檔案內容應該相符");

        log.info("Base64 轉檔案測試通過 - 輸出檔案: {}", outputFile);
    }

    /**
     * 測試 byte 陣列轉檔案
     */
    @Test
    @DisplayName("測試 byte 陣列轉檔案")
    void testBytesToFile() throws IOException {
        // Given
        File outputFile = tempDir.resolve("bytes_output.txt").toFile();

        // When
        FileUtil.bytesToFile(testBytes, outputFile);

        // Then
        assertTrue(outputFile.exists(), "輸出檔案應該存在");
        byte[] actualBytes = Files.readAllBytes(outputFile.toPath());
        assertArrayEquals(testBytes, actualBytes, "檔案內容應該相符");

        log.info("byte 陣列轉檔案測試通過");
    }

    /**
     * 測試 Base64 轉 byte 陣列
     */
    @Test
    @DisplayName("測試 Base64 轉 byte 陣列")
    void testBase64ToBytes() {
        // When
        byte[] result = FileUtil.base64ToBytes(testBase64);

        // Then
        assertNotNull(result, "結果不應為 null");
        assertArrayEquals(testBytes, result, "byte 陣列應該相符");

        log.info("Base64 轉 byte 陣列測試通過");
    }

    /**
     * 測試 byte 陣列轉 Base64
     */
    @Test
    @DisplayName("測試 byte 陣列轉 Base64")
    void testByteToBase64() {
        // When
        String result = FileUtil.byteToBase64(testBytes);

        // Then
        assertNotNull(result, "結果不應為 null");
        assertFalse(result.isEmpty(), "結果不應為空字串");
        assertEquals(testBase64, result, "Base64 字串應該相符");

        log.info("byte 陣列轉 Base64 測試通過");
    }

    /**
     * 測試檔案雜湊值計算 (使用 Guava)
     */
    @Test
    @DisplayName("測試檔案雜湊值計算 (Guava)")
    void testGetFileHashByGuava() {
        // When
        String hash1 = FileUtil.getFileHashByGuava(testFile);
        String hash2 = FileUtil.getFileHashByGuava(testFile);

        // Then
        assertNotNull(hash1, "雜湊值不應為 null");
        assertFalse(hash1.isEmpty(), "雜湊值不應為空字串");
        assertEquals(64, hash1.length(), "SHA-256 雜湊值應為 64 字元");
        assertEquals(hash1, hash2, "相同檔案的雜湊值應該相同");
        assertTrue(hash1.matches("^[a-f0-9]{64}$"), "雜湊值應為 64 位十六進位字串");

        log.info("檔案雜湊值計算 (Guava) 測試通過 - Hash: {}", hash1);
    }

    /**
     * 測試檔案雜湊值計算 (Apache Commons Codec)
     */
    @Test
    @DisplayName("測試檔案雜湊值計算 (Apache Commons Codec)")
    void testGetFileHashByCodec() {
        // When
        String hash1 = FileUtil.getFileHashByCodec(testFile);
        String hash2 = FileUtil.getFileHashByCodec(testFile);

        // Then
        assertNotNull(hash1, "雜湊值不應為 null");
        assertFalse(hash1.isEmpty(), "雜湊值不應為空字串");
        assertEquals(64, hash1.length(), "SHA-256 雜湊值應為 64 字元");
        assertEquals(hash1, hash2, "相同檔案的雜湊值應該相同");
        assertTrue(hash1.matches("^[a-f0-9]{64}$"), "雜湊值應為 64 位十六進位字串");

        log.info("檔案雜湊值計算 (Apache Commons Codec) 測試通過 - Hash: {}", hash1);
    }

    /**
     * 測試兩種雜湊計算方法結果一致性
     */
    @Test
    @DisplayName("測試兩種雜湊計算方法結果一致性")
    void testHashMethodsConsistency() {
        // When
        String guavaHash = FileUtil.getFileHashByGuava(testFile);
        String codecHash = FileUtil.getFileHashByCodec(testFile);

        // Then
        assertEquals(guavaHash, codecHash, "兩種方法計算的雜湊值應該相同");

        log.info("雜湊計算方法一致性測試通過");
    }

    /**
     * 測試檔案覆寫功能
     */
    @Test
    @DisplayName("測試檔案覆寫功能")
    void testFileOverwrite() throws IOException {
        // Given
        File outputFile = tempDir.resolve("overwrite_test.txt").toFile();
        String originalContent = "原始內容";
        String newContent = "新內容";

        // 先寫入原始內容
        FileUtils.writeStringToFile(outputFile, originalContent, StandardCharsets.UTF_8);
        assertTrue(outputFile.exists(), "檔案應該存在");

        // When - 覆寫檔案
        byte[] newBytes = newContent.getBytes(StandardCharsets.UTF_8);
        FileUtil.bytesToFile(newBytes, outputFile);

        // Then
        assertTrue(outputFile.exists(), "檔案應該仍然存在");
        String actualContent = FileUtils.readFileToString(outputFile, StandardCharsets.UTF_8);
        assertEquals(newContent, actualContent, "檔案內容應該被覆寫");

        log.info("檔案覆寫功能測試通過");
    }

    /**
     * 測試目錄自動建立功能
     */
    @Test
    @DisplayName("測試目錄自動建立功能")
    void testDirectoryCreation() throws IOException {
        // Given
        File nestedDir = tempDir.resolve("nested/deep/directory").toFile();
        File outputFile = new File(nestedDir, "test.txt");

        assertFalse(nestedDir.exists(), "巢狀目錄應該不存在");

        // When
        FileUtil.bytesToFile(testBytes, outputFile);

        // Then
        assertTrue(nestedDir.exists(), "巢狀目錄應該被自動建立");
        assertTrue(outputFile.exists(), "檔案應該存在");

        byte[] actualBytes = Files.readAllBytes(outputFile.toPath());
        assertArrayEquals(testBytes, actualBytes, "檔案內容應該正確");

        log.info("目錄自動建立功能測試通過");
    }

    /**
     * 測試空 Base64 字串處理
     */
    @Test
    @DisplayName("測試空 Base64 字串處理")
    void testEmptyBase64() {
        // Given
        String emptyBase64 = java.util.Base64.getEncoder().encodeToString(new byte[0]);
        File outputFile = tempDir.resolve("empty.txt").toFile();

        // When
        FileUtil.base64ToFile(emptyBase64, outputFile);

        // Then
        assertTrue(outputFile.exists(), "檔案應該存在");
        assertEquals(0, outputFile.length(), "檔案應該為空");

        log.info("空 Base64 字串處理測試通過");
    }

    /**
     * 測試無效 Base64 字串異常處理
     */
    @Test
    @DisplayName("測試無效 Base64 字串異常處理")
    void testInvalidBase64() {
        // Given
        String invalidBase64 = "這不是有效的Base64字串!@#$%";

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            FileUtil.base64ToBytes(invalidBase64);
        }, "無效的 Base64 字串應該拋出異常");

        log.info("無效 Base64 字串異常處理測試通過");
    }

    /**
     * 測試不存在檔案的雜湊計算異常處理
     */
    @Test
    @DisplayName("測試不存在檔案的雜湊計算異常處理")
    void testNonExistentFileHash() {
        // Given
        File nonExistentFile = tempDir.resolve("non_existent.txt").toFile();

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            FileUtil.getFileHashByGuava(nonExistentFile);
        }, "不存在的檔案應該拋出異常");

        assertThrows(RuntimeException.class, () -> {
            FileUtil.getFileHashByCodec(nonExistentFile);
        }, "不存在的檔案應該拋出異常");

        log.info("不存在檔案的雜湊計算異常處理測試通過");
    }
}
