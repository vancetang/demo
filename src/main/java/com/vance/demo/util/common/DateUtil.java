package com.vance.demo.util.common;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.UnaryOperator;

import org.apache.commons.lang3.StringUtils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * 日期時間處理工具類別，提供日期字串解析、格式化與星期相關操作。
 * 支援台灣民國年（TWN）與西元年（AD）格式，並處理多種日期字串輸入。
 * 
 * @author Vance
 */
@Slf4j
@UtilityClass
public class DateUtil {

    // 定義日期格式與預處理邏輯的靜態列表
    private static final List<DateFormatEntry> DATE_FORMATS = Arrays.asList(
            new DateFormatEntry(DateTimeFormatter.ofPattern("yyyyMMdd"), "^\\d{8}$", null, false),
            new DateFormatEntry(DateTimeFormatter.ofPattern("yyyyMMdd HHmmss"), "^\\d{8} \\d{6}$", null, true),
            new DateFormatEntry(DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss"),
                    "^\\d{8} \\d{2}:\\d{2}:\\d{2}\\.?\\d*$", null, true),
            new DateFormatEntry(DateTimeFormatter.ofPattern("yyyyMMdd'T'HH:mm:ss"),
                    "^\\d{8}T\\d{2}:\\d{2}:\\d{2}\\.?\\d*$", null, true),
            new DateFormatEntry(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"), "^\\d{14}$", null, true),
            // 民國年格式
            new DateFormatEntry(DateTimeFormatter.ofPattern("yyyyMMdd"), "^\\d{7}$", DateUtil::convertTwnToAd, false),
            new DateFormatEntry(DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss"),
                    "^\\d{7} \\d{2}:\\d{2}:\\d{2}\\.?\\d*$", DateUtil::convertTwnToAd, true));

    // 使用 DateTimeFormatter，保持 static
    private static final DateTimeFormatter DEFAULT_FORMAT = DateTimeFormatter.ofPattern("E MMM dd HH:mm:ss z yyyy",
            Locale.US);

    /**
     * 將日期字串解析為 {@link LocalDate} 或 {@link LocalDateTime}，並轉為
     * {@link Date}，支援多種格式，包括台灣民國年與西元年。
     * <p>
     * 支援的格式範例：
     * <ul>
     * <li>西元年：20250125, 2025-01-25 13:30:45, 20250125T133045</li>
     * <li>民國年：1140125（轉為 2025-01-25）, 1140125 13:30:45</li>
     * <li>民國年月：11401（假設為 2025-01-01）</li>
     * <li>時間戳：1516264163880</li>
     * <li>Date.toString()：Tue Jan 25 13:30:45 CST 2025</li>
     * </ul>
     * 若輸入字串少於 5 碼或無法解析，則返回 null。
     * 
     * @param value 日期字串，可能包含民國年或西元年格式。
     * @return 解析後的 {@link Date} 物件，若解析失敗或輸入無效則返回 null。
     */
    public static Date parseDate(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        try {
            // 使用字元類 [/-] 替換 (/|-)
            value = StringUtil.trim(value).replaceAll("[/-]", "");
            if (StringUtils.length(value) < 5) {
                return null;
            }
            // 處理民國年月 (yyyMM)，補上 "01"
            if (value.matches("^\\d{5}$")) {
                log.info("假設日期為當月第一天：{}", value);
                return parseDate(StringUtils.join(value, "01"));
            }
            // 6碼補0至7碼
            if (value.matches("^\\d{6}$")) {
                return parseDate(StringUtil.addZeroWithValue(value, 7));
            }
            // 遍歷格式列表進行解析
            for (DateFormatEntry entry : DATE_FORMATS) {
                if (value.matches(entry.regex)) {
                    return parseDateWithFormat(entry, value);
                }
            }
            // 處理純數字時間戳
            if (value.matches("^\\d+$")) {
                return new Date(NumberUtil.parseLong(value));
            }
            // 處理 Date.toString() 格式
            LocalDateTime dateTime = LocalDateTime.parse(value, DEFAULT_FORMAT);
            return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
        } catch (DateTimeParseException e) {
            log.warn("解析日期錯誤：[{}] {}", value, e);
        }
        return null;
    }

    /**
     * 使用指定的日期格式解析日期字串。
     * 此方法會先執行預處理邏輯（如果有的話），然後再進行日期解析。
     *
     * @param entry 日期格式項目，包含格式化物件和預處理邏輯
     * @param value 要解析的日期字串
     * @return 解析後的 Date 物件，若解析失敗則返回 null
     */
    private static Date parseDateWithFormat(final DateFormatEntry entry, final String value) {
        try {
            String adjustedValue = Objects.nonNull(entry.preprocessor) ? entry.preprocessor.apply(value) : value;
            if (entry.isDateTime) {
                LocalDateTime dateTime = LocalDateTime.parse(adjustedValue, entry.formatter);
                return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
            } else {
                LocalDate date = LocalDate.parse(adjustedValue, entry.formatter);
                return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
            }
        } catch (DateTimeParseException e) {
            log.warn("解析日期 [{}] 失敗，使用格式 [{}]", value, entry.formatter.toString(), e);
        }
        return null;
    }

    /**
     * 將台灣民國年日期字串轉換為西元年格式。
     * <p>
     * 例如：輸入 "1140125"（民國 114 年 01 月 25 日），返回 "20250125"（西元 2025 年 01 月 25 日）。
     * 若年份超出有效範圍（1 至當前民國年），則拋出異常。
     * 
     * @param twnDate 民國年日期字串，前 3 碼為年份。
     * @return 西元年格式的日期字串。
     * @throws IllegalArgumentException 若民國年份無效。
     */
    private static String convertTwnToAd(String twnDate) {
        int twnYear = NumberUtil.parseInt(twnDate.substring(0, 3));
        int currentTwnYear = Calendar.getInstance().get(Calendar.YEAR) - 1911;
        if (twnYear < 1 || twnYear > currentTwnYear) {
            throw new IllegalArgumentException("無效的民國年份：" + twnYear);
        }
        return String.valueOf(twnYear + 1911) + twnDate.substring(3);
    }

    /**
     * 依據當前時間生成指定格式的日期字串。
     * 
     * @param format 日期格式，例如 "yyyy-MM-dd HH:mm:ss" 或 "yyyyMMdd"。
     * @return 指定格式的當前日期字串。
     * @throws NullPointerException 若 format 為 null。
     */
    public static String getCurrentDate(final String format) {
        return formatDate(new Date(), format);
    }

    /**
     * 將指定的 {@link Date} 物件格式化為日期字串。
     * 
     * @param date   日期物件。
     * @param format 日期格式，例如 "yyyy-MM-dd HH:mm:ss" 或 "yyyyMMdd"。
     * @return 指定格式的日期字串。
     * @throws NullPointerException 若 date 或 format 為 null。
     */
    public static String formatDate(final Date date, final String format) {
        Objects.requireNonNull(date);
        Objects.requireNonNull(format);
        LocalDateTime dateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return DateTimeFormatter.ofPattern(format).format(dateTime);
    }

    /**
     * 取得指定日期的星期幾數字表示。
     * <p>
     * 返回值對應 {@link Calendar} 的常量，例如：
     * <ul>
     * <li>{@link Calendar#SUNDAY} = 1</li>
     * <li>{@link Calendar#MONDAY} = 2</li>
     * <li>...以此類推</li>
     * </ul>
     * 
     * @param date 日期物件，若為 null 則返回 0。
     * @return 星期幾的數字表示（1-7），若 date 為 null 則返回 0。
     */
    public static int getDayOfWeek(Date date) {
        if (Objects.isNull(date)) {
            return 0;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 判斷指定日期是否為周末（星期六或星期日）。
     * 
     * @param date 日期物件。
     * @return 若為星期六或星期日則返回 true，否則返回 false。
     * @throws NullPointerException 若 date 為 null。
     */
    public static boolean isWeekoff(Date date) {
        Objects.requireNonNull(date);
        int day = getDayOfWeek(date);
        return day == Calendar.SUNDAY || day == Calendar.SATURDAY;
    }

    /**
     * 將指定日期轉換為中文星期表示。
     * <p>
     * 例如：
     * <ul>
     * <li>星期日（若日期為星期日）</li>
     * <li>星期一（若日期為星期一）</li>
     * <li>...以此類推</li>
     * </ul>
     * 
     * @param date 日期物件。
     * @return 中文星期表示，例如 "星期一"。
     * @throws NullPointerException 若 date 為 null。
     */
    public static String toWeek(Date date) {
        Objects.requireNonNull(date);
        int day = getDayOfWeek(date);
        StringBuilder sb = new StringBuilder("星期");
        if (day == Calendar.SUNDAY) {
            sb.append("日");
        } else {
            sb.append(NumberUtil.toChineseNumber(day - 1));
        }
        return sb.toString();
    }

    // 內部類別：封裝日期格式與預處理邏輯
    private static class DateFormatEntry {
        final DateTimeFormatter formatter;
        final String regex;
        final UnaryOperator<String> preprocessor;
        final boolean isDateTime; // 表示是否解析為 LocalDateTime（否則為 LocalDate）

        DateFormatEntry(DateTimeFormatter formatter, String regex, UnaryOperator<String> preprocessor,
                boolean isDateTime) {
            this.formatter = formatter;
            this.regex = regex;
            this.preprocessor = preprocessor;
            this.isDateTime = isDateTime;
        }
    }

    /**
     * 取得今天日期的，預設格式為 "yyyy-MM-dd"。
     * 
     * @return 今天日期的 "yyyy-MM-dd" 格式西元 (AD) 字串。
     */
    public static String getTodayDateString() {
        return getCurrentDate("yyyy-MM-dd");
    }
}