package com.vance.demo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateUtil {

    /**
     * 解析由Date.toString()的文字,還原成Date。
     * 
     * @param value
     * @return
     */
    public static Date parseDate(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        try {
            value = Util.trim(value).replaceAll("(/|-)", "");
            // 不足5碼回傳null
            if (StringUtils.length(value) < 5) {
                return null;
            }
            // 長度五碼(民國年月 yyyMM)，補上後面01
            else if (value.matches("^\\d{5}$")) {
                return parseDate(StringUtils.join(value, "01"));
            }
            // 長度為6碼的情況下，先行補0補滿7碼
            else if (value.matches("^\\d{6}$")) {
                return parseDate(Util.addZeroWithValue(value, 7));
            }
            // 民國日期 EX:1040101
            else if (value.matches("^\\d{7}$")) {
                int y = NumberUtil.parseInt(value.substring(0, 3)) + 1911;
                return parseDate(y + value.substring(3));
            }
            // 西元日期 EX:20150101
            else if (value.matches("^\\d{8}$")) {
                SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");
                return formatDate.parse(value);
            }
            // 民國時間
            else if (value.matches("^\\d{7} \\d{2}\\d{2}\\d{2}\\.?\\d*$")) {
                int y = NumberUtil.parseInt(value.substring(0, 3)) + 1911;
                return parseDate(y + value.substring(3));
            }
            // 西元時間
            else if (value.matches("^\\d{8} \\d{2}\\d{2}\\d{2}\\.?\\d*$")) {
                SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd HHmmss");
                return formatDate.parse(value);
            }
            // 民國時間
            else if (value.matches("^\\d{7} \\d{2}:\\d{2}:\\d{2}\\.?\\d*$")) {
                int y = NumberUtil.parseInt(value.substring(0, 3)) + 1911;
                return parseDate(y + value.substring(3));
            }
            // 西元時間
            else if (value.matches("^\\d{8} \\d{2}:\\d{2}:\\d{2}\\.?\\d*$")) {
                SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
                return formatDate.parse(value);
            }
            // 2014-10-24T00:00:00.000000000
            else if (value.matches("^\\d{8}T\\d{2}:\\d{2}:\\d{2}\\.?\\d*$")) {
                SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd'T'HH:mm:ss");
                return formatDate.parse(value);
            }
            // long time EX:1516264163880
            else if (value.matches("^\\d+$")) {
                return new Date(NumberUtil.parseLong(value));
            }
            // Date.toString()
            else {
                SimpleDateFormat formatDate = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.US);
                return formatDate.parse(value);
            }
        } catch (ParseException e) {
            log.info("parseDate value:[{}]", value);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 依當前時間取出指定格式日期字串
     * 
     * @param format
     * @return
     */
    public static String getCurrentDate(final String format) {
        return formatDate(new Date(), format);
    }

    /**
     * 轉出指定格式的日期字串
     * 
     * @param date
     * @param format
     * @return
     */
    public static String formatDate(final Date date, final String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 取得星期幾(請搭配Calendar.SUNDAY.....etc使用)
     * 
     * @param date 任意一天日期物件
     * @return
     */
    public static int getDayOfWeek(Date date) {
        if (date != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            return c.get(Calendar.DAY_OF_WEEK);
        }
        return 0;
    }

    /**
     * 是否為正常周休二日(六日)
     * 
     * @param date 日期
     * @return
     */
    public static boolean isWeekoff(Date date) {
        int i = DateUtil.getDayOfWeek(date);
        return (i == Calendar.SATURDAY) || (i == Calendar.SUNDAY);
    }

    /**
     * 轉為星期幾
     * 
     * @param date
     * @return
     */
    public static String toWeek(Date date) {
        int i = DateUtil.getDayOfWeek(date);
        StringBuilder sb = new StringBuilder("星期");
        if (i == Calendar.SUNDAY) {
            sb.append("日");
        } else {
            sb.append(NumberUtil.toChineseNumber(i - 1));
        }
        return sb.toString();
    }
}
