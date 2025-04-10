package com.vance.demo;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.vance.demo.data.dto.ApiResult;
import com.vance.demo.util.common.JsonUtil;
import com.vance.demo.util.common.Util;

import lombok.extern.slf4j.Slf4j;

/**
 * 測試類別
 * 
 * @author vance
 * @since 2025/03/31
 */
@Slf4j
public class VanceTest {
    public static void main(String[] args) {
        log.info("========== {} 開始 ==========", VanceTest.class.getSimpleName());
        try {
            Map<String, Object> data = new HashMap<>();
            data.put("me", "vance");
            data.put("test1", Collections.singletonMap("me", "vance"));
            data.put("test2", Collections.singletonList(Collections.singletonMap("me", "vance")));
            log.info("{}", Util.safeGet(data, "me", String.class));
            log.info("{}", Util.safeGet(data, "test2", List.class));

            // 使用 ApiResult 類別測試 =========================
            ApiResult result = ApiResult.success().data(data);
            log.info("{}", result);
            Map<String, Object> map = new HashMap<>();
            map.put("vvv", "vance");
            result.setData(map).data(data).data("newone", "vance-newone");
            log.info("{}", result);
            // ===============================================
            data.put("a", "vance-a");
            data.put("b", "vance-b");
            map = new HashMap<>();
            map.put("data", data);
            log.info("data==>{}", map);
            Optional.ofNullable(map)
                    .map(m -> Util.safeGetMap(m, "data"))
                    .ifPresent(dtl -> {
                        dtl.computeIfPresent("a", (k, v) -> StringUtils.EMPTY);
                        dtl.computeIfPresent("b", (k, v) -> StringUtils.EMPTY);
                    });
            log.info("{}", map);
            log.info("{}", JsonUtil.toJsonString(new InnerVanceTest("vance....")));
        } catch (Exception e) {
            log.error("{}", ExceptionUtils.getStackTrace(e));
        }
        log.info("========== {} 結束 ==========", VanceTest.class.getSimpleName());
    }

    record InnerVanceTest(String vance) {
    }
}
