package com.vance.demo.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.vance.demo.filter.BodyTransferFilter;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<BodyTransferFilter> bodyTransferFilterRegistration() {
        FilterRegistrationBean<BodyTransferFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new BodyTransferFilter());
        registration.addUrlPatterns("/*"); // 指定 Filter 適用的 URL 模式
        registration.setOrder(1); // 設置執行順序，數字越小越先執行
        registration.setName("bodyTransferFilter");
        return registration;
    }
}
