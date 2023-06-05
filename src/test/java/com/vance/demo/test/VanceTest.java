package com.vance.demo.test;

import java.util.function.Predicate;

import org.apache.commons.lang3.exception.ExceptionUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VanceTest {
    public static void main(String[] args) {
        log.info("vance test start");
        try {
            Predicate<Integer> isEven = n -> n % 2 == 0;
            log.info("{}", isEven.test(10));
        } catch (Exception e) {
            log.error("{}", ExceptionUtils.getStackTrace(e));
        }
        log.info("vance test end");
    }
}
