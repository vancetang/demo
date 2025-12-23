package com.vance.demo.util.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.Objects;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;

import com.google.common.hash.Hashing;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * 通用檔案處理工具類別 (File Utility)
 * <p>
 * 提供檔案讀寫、Base64 轉換、雜湊計算 (Hash)、複製移動等常用功能。
 * 整合了 Java NIO, Apache Commons IO 與 Guava 等函式庫，旨在提供簡便且健壯的檔案操作介面。
 * </p>
 *
 * @author Vance
 */
@Slf4j
@UtilityClass
public class FileUtil {

    /**
     * 計算檔案的 SHA-256 Hash 值 (使用 Guava)
     *
     * @param file 來源檔案 (不可為 null)
     * @return SHA-256 雜湊值字串
     * @throws RuntimeException 當檔案讀取失敗時拋出
     */
    public static String getFileHashByGuava(@NonNull File file) {
        try {
            return com.google.common.io.Files.asByteSource(file).hash(Hashing.sha256()).toString();
        } catch (IOException e) {
            log.error("計算 Hash (Guava) 失敗: {}", file.getAbsolutePath(), e);
            throw new RuntimeException("計算 Hash 失敗", e);
        }
    }

    /**
     * 計算檔案的 SHA-256 Hash 值 (使用 Apache Commons Codec)
     * <p>
     * 適用於需要串流處理大型檔案的場景。
     * </p>
     *
     * @param file 來源檔案 (不可為 null)
     * @return SHA-256 雜湊值字串
     * @throws RuntimeException 當檔案讀取失敗時拋出
     */
    public static String getFileHashByCodec(@NonNull File file) {
        try (FileInputStream fis = new FileInputStream(file)) {
            return DigestUtils.sha256Hex(fis);
        } catch (IOException e) {
            log.error("計算 Hash (Codec) 失敗: {}", file.getAbsolutePath(), e);
            throw new RuntimeException("計算 Hash 失敗", e);
        }
    }

    /**
     * 將檔案內容轉換為 Base64 編碼字串
     *
     * @param file 來源檔案 (不可為 null)
     * @return Base64 編碼後的字串
     * @throws RuntimeException 當檔案讀取失敗時拋出
     */
    public static String fileToBase64(@NonNull File file) {
        try {
            byte[] fileData = Files.readAllBytes(file.toPath());
            return byteToBase64(fileData);
        } catch (IOException e) {
            log.error("檔案轉 Base64 失敗: {}", file.getAbsolutePath(), e);
            throw new RuntimeException("檔案轉 Base64 失敗", e);
        }
    }

    /**
     * 將 Base64 編碼字串解碼並寫入至檔案
     *
     * @param base64 Base64 編碼的字串 (不可為 null)
     * @param file   目標檔案 (不可為 null)
     */
    public static void base64ToFile(@NonNull String base64, @NonNull File file) {
        byte[] bytes = base64ToBytes(base64);
        bytesToFile(bytes, file);
    }

    /**
     * 將 Byte 陣列寫入至檔案
     * <p>
     * 若目標檔案已存在，將會先刪除再寫入。
     * 若目標資料夾不存在，將會自動建立。
     * </p>
     *
     * @param bytes 檔案內容 Byte 陣列
     * @param file  目標檔案
     * @throws RuntimeException 當寫入失敗時拋出
     */
    public static void bytesToFile(byte[] bytes, @NonNull File file) {
        try {
            Path path = file.toPath();
            // 確保父目錄存在
            if (file.getParentFile() != null && !file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            // 寫入檔案 (StandardOpenOption 預設行為即為 CREATE, TRUNCATE_EXISTING)
            Files.write(path, bytes);
        } catch (IOException e) {
            log.error("寫入檔案失敗: {}", file.getAbsolutePath(), e);
            throw new RuntimeException("寫入檔案失敗", e);
        }
    }

    /**
     * Base64 字串轉 Byte 陣列
     *
     * @param base64 Base64 編碼字串
     * @return 解碼後的 Byte 陣列
     */
    public static byte[] base64ToBytes(@NonNull String base64) {
        return Base64.getDecoder().decode(base64);
    }

    /**
     * Byte 陣列轉 Base64 字串
     *
     * @param fileData 檔案內容 Byte 陣列
     * @return Base64 編碼字串
     */
    public static String byteToBase64(byte[] fileData) {
        return Base64.getEncoder().encodeToString(fileData);
    }

    // ========================================================================
    // 新增通用擴充方法
    // ========================================================================

    /**
     * 讀取檔案內容為字串 (預設使用 UTF-8)
     *
     * @param file 來源檔案
     * @return 檔案內容字串
     */
    public static String readFileToString(@NonNull File file) {
        return readFileToString(file, StandardCharsets.UTF_8);
    }

    /**
     * 讀取檔案內容為字串 (指定編碼)
     *
     * @param file    來源檔案
     * @param charset 編碼格式 (如 StandardCharsets.UTF_8)
     * @return 檔案內容字串
     */
    public static String readFileToString(@NonNull File file, @NonNull Charset charset) {
        try {
            return FileUtils.readFileToString(file, charset);
        } catch (IOException e) {
            log.error("讀取檔案字串失敗: {}", file.getAbsolutePath(), e);
            throw new RuntimeException("讀取檔案失敗", e);
        }
    }

    /**
     * 將字串寫入檔案 (預設使用 UTF-8)
     *
     * @param file    目標檔案
     * @param content 內容字串
     */
    public static void writeStringToFile(@NonNull File file, String content) {
        writeStringToFile(file, content, StandardCharsets.UTF_8);
    }

    /**
     * 將字串寫入檔案 (指定編碼)
     *
     * @param file    目標檔案
     * @param content 內容字串
     * @param charset 編碼格式
     */
    public static void writeStringToFile(@NonNull File file, String content, @NonNull Charset charset) {
        try {
            FileUtils.writeStringToFile(file, content, charset);
        } catch (IOException e) {
            log.error("寫入檔案字串失敗: {}", file.getAbsolutePath(), e);
            throw new RuntimeException("寫入檔案失敗", e);
        }
    }

    /**
     * 複製檔案
     *
     * @param source 來源檔案
     * @param dest   目標檔案
     */
    public static void copyFile(@NonNull File source, @NonNull File dest) {
        try {
            Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("複製檔案失敗: {} -> {}", source.getAbsolutePath(), dest.getAbsolutePath(), e);
            throw new RuntimeException("複製檔案失敗", e);
        }
    }

    /**
     * 刪除檔案 (若存在)
     *
     * @param file 欲刪除的檔案
     * @return 是否成功刪除 (若檔案原本就不存在回傳 false)
     */
    public static boolean deleteFile(@NonNull File file) {
        try {
            return Files.deleteIfExists(file.toPath());
        } catch (IOException e) {
            log.warn("刪除檔案失敗: {}", file.getAbsolutePath(), e);
            return false;
        }
    }
}
