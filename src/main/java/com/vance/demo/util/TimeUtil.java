package com.vance.demo.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Date;

import org.apache.commons.lang3.exception.ExceptionUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * GMT: 格林威治時間，UTC: 世界標準時間<br/>
 * 目前標準為UTC，UCT=GMT+偏移量(忽略不看的話就是UCT=GMT)
 * 
 * @author Vance
 */
@Slf4j
public class TimeUtil {

	/**
	 * 主程式
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		log.info("======= {} 開始 =======", TimeUtil.class.getSimpleName());
		try {
			// long time = System.currentTimeMillis();
			long time = 1050826;
			String utc = toUTC(time);
			// Asia/Taipei
			String iso = toUTC(time, ZoneId.of("Japan"));
			log.info("{}", utc);
			log.info("{}", iso);
			log.info("{}", toLong(utc));
			log.info("{}", toLong(iso));
			log.info("{}", toUTCTw(time));
			log.info("{}", DateUtil.formatDate(new Date(time), "yyyy-MM-dd HH:mm:ss.SSS"));
			// log.info("{}", toLong("2023-04-13T11:00:11.751"));
			log.info("------------------------------------");
			// Instant 是以UTC+0的方式存放
			// EX: 2023-11-14T16:00:00Z -> 台北時間是+8 所以是2023-11-15 00:00:00
			Instant instant = toInstant("2023-11-15", "yyyy-MM-dd");
			log.info("{}", instant);
			String str = formatter(Instant.now(), "yyyy-MM");
			log.info("{}", str);
			log.info("1=>{}", toInstant("2023-11-15 17:23:56.345", "yyyy-MM-dd HH:mm:ss.SSS"));
			log.info("2=>{}", toInstant("2023-11-15 17:23:56", "yyyy-MM-dd HH:mm:ss"));
			log.info("3=>{}", toInstant("2023-11-15 17:23", "yyyy-MM-dd HH:mm"));
			log.info("4=>{}", toInstant("2023-11-15 17", "yyyy-MM-dd HH"));
			log.info("5=>{}", toInstant("2023-11-15", "yyyy-MM-dd"));
			log.info("6=>{}", toInstant("2023-11", "yyyy-MM"));
			log.info("7=>{}", toInstant("2023", "yyyy"));
			Instant a = toInstant("2023-11-15", "yyyy-MM-dd");
			log.info("{}", formatter(a, "yyyy-MM-dd HH:mm:ss.SSS"));
		} catch (Exception e) {
			log.error(ExceptionUtils.getStackTrace(e));
		}
		log.info("======= {} 結束 =======", TimeUtil.class.getSimpleName());
	}

	/**
	 * Timestamp轉世界標準時間格式(ZoneId:格林威治時間)<br/>
	 * <br/>
	 * 格式: 2023-04-13T03:00:11.751Z (Z:就代表 UTC+0；T:分隔符)
	 * 
	 * @param timestamp 時間戳
	 * @return
	 */
	public static String toUTC(long timestamp) {
		return toUTC(timestamp, ZoneId.of("GMT"));
	}

	/**
	 * Timestamp轉世界標準時間格式(ZoneId:格林威治時間)<br/>
	 * <br/>
	 * 格式: 2023-04-13T03:00:11.751Z (Z:就代表 UTC+0；T:分隔符)
	 * 
	 * @param timestamp 時間戳
	 * @return
	 */
	public static String toUTCTw(long timestamp) {
		return toUTC(timestamp, ZoneId.systemDefault());
	}

	/**
	 * Timestamp轉世界標準時間格式<br/>
	 * <br/>
	 * 格式: 2023-04-13T11:00:11.751+08:00<br/>
	 * (+08:00 就代表 UTC+8(也就是台北時間)；T:分隔符)
	 * 
	 * @param timestamp 時間戳
	 * @param zoneid    目標時區
	 * @return
	 */
	public static String toUTC(long timestamp, ZoneId zoneid) {
		LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.of("UTC"));
		ZonedDateTime utcZone = ZonedDateTime.of(localDateTime, ZoneId.of("UTC")).withZoneSameInstant(zoneid);
		String str = utcZone.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
		return str;
	}

	/**
	 * UTC Sting轉Timestamp
	 * 
	 * @param utc UTC String
	 * @return
	 */
	public static long toLong(String utc) {
		ZonedDateTime x = ZonedDateTime.parse(utc);
		return x.toInstant().toEpochMilli();
	}

	/**
	 * 字串轉Instant物件
	 * 
	 * @param dateTimeStr 日期字串
	 * @param pattern     來源字串格式
	 * @return
	 */
	public static Instant toInstant(String dateTimeStr, String pattern) {
		// 給定預設值，這樣前端不管傳入什麼格式，轉換都不會有問題
		DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern(pattern)
				.parseDefaulting(ChronoField.MONTH_OF_YEAR, 1).parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
				.parseDefaulting(ChronoField.HOUR_OF_DAY, 0).parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
				.parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0).toFormatter();
		return LocalDateTime.parse(dateTimeStr, formatter).atZone(ZoneId.systemDefault()).toInstant();
	}

	/**
	 * Instant 轉 字串
	 * 
	 * @param instant Instant物件
	 * @param pattern 欲產出格式
	 * @return
	 */
	public static String formatter(Instant instant, String pattern) {
		return DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.systemDefault()).format(instant);
	}
}
