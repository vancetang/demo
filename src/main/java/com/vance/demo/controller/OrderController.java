package com.vance.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vance.demo.data.dto.ApiResult;
import com.vance.demo.data.dto.OrderRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/api/orders")
public class OrderController {

    /**
     * 測試用的訂單建立 API
     * 
     * @param request 訂單請求物件
     * @return ApiResult 包含訂單資訊
     */
    @PostMapping
    public ApiResult createOrder(@RequestBody OrderRequest request) {
        log.info("Creating order with request: {}", request);
        return ApiResult.success().data("order", request);
    }
}
