package com.vance.demo.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import javax.net.ssl.SSLHandshakeException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.vance.demo.util.common.Util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestControllerTest {
    @Test
    void testTest() {
        int a = 4;
        Assertions.assertNotEquals(1, a);
    }

    @Test
    void testSSL() {
        String[] urls = { "https://expired.badssl.com/", "https://wrong.host.badssl.com/",
                "https://self-signed.badssl.com/", "https://untrusted-root.badssl.com/" };
        // 測試多個 SSL 失敗場景
        Arrays.asList(urls).forEach(url -> testSslUrlFailures(url, SSLHandshakeException.class));
        // 測試多個 SSL 成功場景
        Arrays.asList(urls).forEach(url -> testSslUrlSuccess(url));
    }

    /**
     * 測試 SSL 連線失敗
     * 
     * @param url
     * @param expectedCause
     */
    private void testSslUrlFailures(String url, Class<? extends Throwable> expectedCause) {
        // 預期 ResourceAccessException，並檢查其根本原因
        ResourceAccessException exception = assertThrows(ResourceAccessException.class, () -> {
            RestTemplate restTemplate = Util.createRestTemplate();
            restTemplate.getForObject(url, String.class);
        });
        // 檢查異常的根本原因是否符合預期
        Throwable cause = exception.getCause();
        assertTrue(expectedCause.isInstance(cause),
                "預期根本原因是 " + expectedCause.getSimpleName() + "，實際是 " +
                        cause.getClass().getSimpleName());
    }

    /**
     * 測試 SSL 連線失敗
     * 
     * @param url
     * @param expectedCause
     */
    private void testSslUrlSuccess(String url) {
        try {
            RestTemplate restTemplate = Util.createRestTemplateWithTls12();
            String json = restTemplate.getForObject(url, String.class);
            assertNotNull(json, "JSON是空的");
        } catch (Exception e) {
            log.error("{}", e);
            assertTrue(false, "SSL 連線失敗");
        }
    }
}
