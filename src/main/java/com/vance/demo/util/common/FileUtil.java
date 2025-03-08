package com.vance.demo.util.common;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.codec.digest.DigestUtils;

import com.google.common.hash.Hashing;
import com.google.common.io.Files;

import lombok.extern.slf4j.Slf4j;

/**
 * 檔案處理工具
 * 
 * @author Vance
 */
@Slf4j
public class FileUtil {
    /**
     * 使用 Guava 來計算檔案的 SHA-256 Hash
     * 
     * @param file
     * @return
     */
    public static String getFileHashByGuava(File file) {
        log.info("File Path: {}", file.getAbsolutePath());
        try {
            return Files.asByteSource(file).hash(Hashing.sha256()).toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 使用 Apache Commons Codec 來計算檔案的 SHA-256 Hash
     * 
     * @param file
     * @return
     */
    public static String getFileHashByCodec(File file) {
        log.info("File Path: {}", file.getAbsolutePath());
        try (FileInputStream fis = new FileInputStream(file)) {
            return DigestUtils.sha256Hex(fis);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
