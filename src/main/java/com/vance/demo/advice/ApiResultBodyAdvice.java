package com.vance.demo.advice;

import java.lang.reflect.Method;
import java.util.Objects;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.vance.demo.pojo.ApiResult;

/**
 * 增加註解說明
 */
@RestControllerAdvice
public class ApiResultBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    @Nullable
    public Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType, MediaType mediaType,
            Class<? extends HttpMessageConverter<?>> converterType, ServerHttpRequest request,
            ServerHttpResponse response) {
        if (Objects.nonNull(body)) {
            ApiResult result = (ApiResult) body;
            return result.setPath(request.getURI().getPath());
        }
        return body;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // RestController 且回傳型態為 ApiResult
        Method method = returnType.getMethod();
        if (Objects.nonNull(method) && Objects.equals(method.getReturnType(), ApiResult.class)) {
            return true;
        }
        return false;
    }
}
