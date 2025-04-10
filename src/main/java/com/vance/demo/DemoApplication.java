package com.vance.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import lombok.extern.slf4j.Slf4j;

/**
 * Application主程式
 * 
 * @author Vance
 */
@Slf4j
@SpringBootApplication
@ServletComponentScan
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		log.info("啟動-測試完成!!，順便測試commit是否為中文");
	}

}
