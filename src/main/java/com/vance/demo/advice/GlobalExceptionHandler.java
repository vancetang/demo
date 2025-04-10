package com.vance.demo.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.vance.demo.data.dto.ApiResult;
import com.vance.demo.enums.ResultCodeEnum;
import com.vance.demo.exception.UserNotFoundException;

/**
 * 全域例外狀況處理 (僅處理標記有 @RestController 的類別)。
 * <p>
 * 此例外狀況處理類別專門用於處理來自標記有 {@link RestController} 的控制器所引發的例外狀況，
 * 例如 {@link UserNotFoundException}。
 * </p>
 *
 * @author Vance
 */
@RestControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResult handleUserNotFound(UserNotFoundException ex) {
        return ApiResult.result(ResultCodeEnum.USER_NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResult handleIllegalArgument(IllegalArgumentException ex) {
        return ApiResult.result(ResultCodeEnum.FAIL).message(ex.getMessage());
    }
}
