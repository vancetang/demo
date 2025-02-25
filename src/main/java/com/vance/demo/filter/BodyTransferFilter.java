package com.vance.demo.filter;

import java.io.IOException;
import java.util.Objects;

import com.vance.demo.wrapper.RequestBodyWrapper;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * Filter將request重新包裝
 * 
 * @author Vance
 */
@Slf4j
public class BodyTransferFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        RequestBodyWrapper requestBodyWrapper = null;
        try {
            log.info("這裡....");
            HttpServletRequest req = (HttpServletRequest) request;
            requestBodyWrapper = new RequestBodyWrapper(req);
        } catch (Exception e) {
            log.warn("requestBodyWrapper Error:", e);
        }
        chain.doFilter((Objects.isNull(requestBodyWrapper) ? request : requestBodyWrapper), response);
    }
}
