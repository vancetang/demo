package com.vance.demo.filter;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.vance.demo.global.RequestBodyWrapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BodyTransferFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        RequestBodyWrapper requestBodyWrapper = null;
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            requestBodyWrapper = new RequestBodyWrapper(req);

        } catch (Exception e) {
            log.warn("requestBodyWrapper Error:", e);
        }
        chain.doFilter((Objects.isNull(requestBodyWrapper) ? request : requestBodyWrapper), response);
    }
}
