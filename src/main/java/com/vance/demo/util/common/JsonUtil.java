package com.vance.demo.util.common;

import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * JSON 處理工具類別
 */
@Slf4j
@UtilityClass
public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 將任意 Java 物件轉換為 JSON 字串。
     *
     * @param obj 要轉換的物件
     * @return JSON 字串，如果轉換失敗則返回 null
     */
    public static String toJsonString(Object obj) {
        if (Objects.isNull(obj)) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("無法將物件轉換為 JSON 字串: {}", obj, e);
            return null;
        }
    }

    /**
     * 將 Map 物件轉換為 JSON 字串。
     * 這是 toJsonString(Object obj) 的一個特化版本，為了方便使用。
     *
     * @param map 要轉換的 Map
     * @return JSON 字串，如果轉換失敗則返回 null
     */
    public static String toJsonString(Map<?, ?> map) {
        return toJsonString((Object) map);
    }
}
