package com.vance.demo.util.common;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.util.Base64;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;

import com.google.common.hash.Hashing;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * 檔案處理工具
 * 
 * @author Vance
 */
@Slf4j
@UtilityClass
public class FileUtil {

    public static void main(String[] args) {
        log.info("======= {} 開始 =======", FileUtil.class.getSimpleName());
        File file = FileUtils.getFile(SystemUtils.getUserHome(), "Desktop", "test.txt");
        log.info("{}", fileToBase64(file));
        log.info("{}", getFileHashByCodec(file));
        log.info("======= {} 結束 =======");
    }

    /**
     * 使用 Guava 來計算檔案的 SHA-256 Hash
     * 
     * @param file 來源檔案
     * @return 檔案雜湊值
     */
    public static String getFileHashByGuava(File file) {
        log.info("File Path: {}", file.getAbsolutePath());
        try {
            return com.google.common.io.Files.asByteSource(file).hash(Hashing.sha256()).toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 使用 Apache Commons Codec 來計算檔案的 SHA-256 Hash
     * 
     * @param file 來源檔案
     * @return 檔案雜湊值
     */
    public static String getFileHashByCodec(File file) {
        log.info("File Path: {}", file.getAbsolutePath());
        try (FileInputStream fis = new FileInputStream(file)) {
            return DigestUtils.sha256Hex(fis);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 檔案轉為base64文字
     * 
     * @param file 欲轉換的檔案
     * @return Base64 編碼後的字串
     */
    public static String fileToBase64(File file) {
        try {
            byte[] fileData = Files.readAllBytes(file.toPath());
            return byteToBase64(fileData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * base64文字轉為檔案
     * 
     * @param base64 Base64 編碼的字串
     * @param file   儲存內容的目標檔案
     */
    public static void base64ToFile(String base64, File file) {
        byte[] bytes = base64ToBytes(base64);
        bytesToFile(bytes, file);
    }

    /**
     * bytes轉為檔案
     * 
     * @param bytes 檔案的 byte 陣列
     * @param file  儲存內容的目標檔案
     */
    public static void bytesToFile(byte[] bytes, File file) {
        try {
            if (file.exists()) {
                file.delete();
            }
            file.getParentFile().mkdirs();
            FileUtils.writeByteArrayToFile(file, bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * base64文字轉為byte Array
     * 
     * @param base64 Base64 編碼的字串
     * @return 解碼後的 byte 陣列
     */
    public static byte[] base64ToBytes(String base64) {
        return Base64.getDecoder().decode(base64);
    }

    /**
     * byte轉為base64文字
     * 
     * @param fileData 檔案內容的 byte 陣列
     * @return Base64 編碼後的字串
     */
    public static String byteToBase64(byte[] fileData) {
        return Base64.getEncoder().encodeToString(fileData);
    }
}
