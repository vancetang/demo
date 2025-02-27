package com.vance.demo.aop;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.vance.demo.data.dto.ApiResult;
import com.vance.demo.enums.ResultCodeEnum;
import com.vance.demo.exception.UserNotFoundException;

/**
 * 全局異常處理
 * <p>
 * 異常處理類，用於處理全局異常，例如用戶不存在異常。
 * </p>
 * 
 * @author Vance
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResult handleUserNotFound(UserNotFoundException ex) {
        return ApiResult.result(ResultCodeEnum.USER_NOT_FOUND);
    }
}
