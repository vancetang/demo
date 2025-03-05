package com.vance.demo.test;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import com.vance.demo.util.common.Util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VanceTest {
    public static void main(String[] args) {
        log.info("========== {} 開始 ==========", VanceTest.class.getSimpleName());
        try {
            // String html = FtlUtil.getString("copyright.ftl",
            // Collections.singletonMap("me", "vance"));
            // log.info("{}", html);
            Map<String, Object> data = new HashMap<>();
            data.put("me", "vance");
            data.put("test1", Collections.singletonMap("me", "vance"));
            data.put("test2", Collections.singletonList(Collections.singletonMap("me", "vance")));
            log.info("{}", Util.safeGet(data, "me", String.class));
            log.info("{}", Util.safeGet(data, "test2", List.class));
        } catch (Exception e) {
            log.error("{}", ExceptionUtils.getStackTrace(e));
        }
        log.info("========== {} 結束 ==========", VanceTest.class.getSimpleName());
    }

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
    public static String getFileHashByApache(File file) {
        log.info("File Path: {}", file.getAbsolutePath());
        try (FileInputStream fis = new FileInputStream(file)) {
            return DigestUtils.sha256Hex(fis);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
