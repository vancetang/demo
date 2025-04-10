package com.vance.demo.controller;

import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;
import com.vance.demo.data.dto.ApiResult;
import com.vance.demo.data.model.User;
import com.vance.demo.exception.UserNotFoundException;
import com.vance.demo.util.common.DateUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

/**
 * 測試用controller
 * 
 * @author Vance
 */
@Slf4j
@RestController
@RequestMapping(path = "/api")
public class TestController {

    @Value("${env}")
    private String env;

    @GetMapping("/test")
    public ApiResult test(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        log.info("Session is null: {}", Objects.isNull(session));
        if (Objects.nonNull(session)) {
            log.info("Session ID: {}", session.getId());
        }
        return ApiResult.success().data("say", "hello world").data("env", env);
    }

    @PostMapping("/test2")
    public ApiResult test2(@RequestBody User user) {
        log.info("{}", user);
        return ApiResult.success().data("User", user);
    }

    @PostMapping("/test3")
    public Map<String, Object> test3() {
        Map<String, Object> result = Maps.newHashMapWithExpectedSize(10);
        result.put("vance-name", "test1");
        return result;
    }

    @PostMapping("/test4")
    public ApiResult test4() {
        return ApiResult.success().data("today", DateUtil.getTodayDateString()).data("env", env);
    }

    @PostMapping("/test5")
    public String test5() {
        throw new RuntimeException("Vance測試錯誤");
    }

    @PostMapping("/test6")
    public String test6() {
        throw new UserNotFoundException("Vance測試錯誤");
    }
}
