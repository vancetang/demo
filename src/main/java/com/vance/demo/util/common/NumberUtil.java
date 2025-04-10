package com.vance.demo.util.common;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import lombok.experimental.UtilityClass;

/**
 * 數字處理工具
 * 
 * @author Vance
 */
@UtilityClass
public class NumberUtil {

    private static final String[] UNITS_STRINGS = new String[] { "", "萬", "億", "萬億" };

    /**
     * 檢查是不是數字
     * 
     * @param value
     * @return
     */
    public static boolean isNumeric(Object value) {
        if (Objects.isNull(value)) {
            return false;
        } else if (value instanceof Number) {
            return true;
        } else {
            String v = value.toString();
            return v.trim().replaceAll(",", StringUtils.EMPTY).matches("(\\+|-)?\\d+(\\.\\d+)?");
        }
    }

    /**
     * 傳入帶有comma的文字，去掉Comma，並傳回字串.
     * 
     * @param number String 帶有comma的字串。
     * @return String 傳出浮點數型態的數字(小數點2位，四捨五入)。
     */
    public static String delComma(Object input) {
        if (input instanceof Double) {
            BigDecimal bd = BigDecimal.valueOf((Double) input);
            return bd.toPlainString();
        } else if (input instanceof Long) {
            BigDecimal bd = BigDecimal.valueOf((Long) input);
            return bd.toPlainString();
        } else if (isNumeric(input)) {
            return String.valueOf(input).trim().replaceAll("(\\+|,)", StringUtils.EMPTY);
        }
        return StringUtils.EMPTY;
    }

    /**
     * 解析為整數
     * 
     * @param input
     * @return
     */
    public static Integer parseInt(Object input) {
        return parseInt(input, false);
    }

    /**
     * 解析為整數
     * 
     * @param input
     * @param returnNull (當值為NULL時,是否回傳NULL)
     * @return
     */
    public static Integer parseInt(Object input, boolean returnNull) {
        if (Objects.isNull(input) && returnNull) {
            return null;
        }
        return parseBigDecimal(input).intValue();
    }

    /**
     * 解析為Double
     * 
     * @param input
     * @return
     */
    public static Double parseDouble(Object input) {
        return parseDouble(input, false);
    }

    /**
     * 解析為Double
     * 
     * @param input
     * @param returnNull (當值為NULL時,是否回傳NULL)
     * @return
     */
    public static Double parseDouble(Object input, boolean returnNull) {
        if (Objects.isNull(input) && returnNull) {
            return null;
        }
        return parseBigDecimal(input).doubleValue();
    }

    /**
     * 解析為Double(四拾五入)
     * 
     * @param input
     * @param scale (小數點位置)
     * @return
     */
    public static Double parseDouble(Object input, int scale) {
        return parseDouble(input, scale, false);
    }

    /**
     * 解析為Double(四拾五入)
     * 
     * @param input
     * @param scale      (小數點位置)
     * @param returnNull (當值為NULL時,是否回傳NULL)
     * @return
     */
    public static Double parseDouble(Object input, int scale, boolean returnNull) {
        if (Objects.isNull(input) && returnNull) {
            return null;
        }
        return parseBigDecimal(input).setScale(scale, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 解析為長整數
     * 
     * @param input
     * @return
     */
    public static Long parseLong(Object input) {
        return parseLong(input, false);
    }

    /**
     * 解析為長整數
     * 
     * @param input
     * @param returnNull (當值為NULL時,是否回傳NULL)
     * @return
     */
    public static Long parseLong(Object input, boolean returnNull) {
        if (Objects.isNull(input) && returnNull) {
            return null;
        }
        return parseBigDecimal(input).longValue();
    }

    /**
     * 解析為BigDecimal
     * 
     * @param input
     * @return
     */
    public static BigDecimal parseBigDecimal(Object input) {
        // 預設BigDecimal.ROUND_HALF_UP add by fantasy 2020/04/22
        BigDecimal result = parseBigDecimal(input, false);
        return result.setScale(result.scale(), RoundingMode.HALF_UP);
    }

    /**
     * 解析為BigDecimal
     * 
     * @param input
     * @param returnNull 當值為NULL時,是否回傳NULL
     * @return
     */
    public static BigDecimal parseBigDecimal(Object input, boolean returnNull) {
        if (Objects.isNull(input) && returnNull) {
            return null;
        }
        if (input instanceof BigDecimal) {
            return (BigDecimal) input;
        } else if (isNumeric(input)) {
            return new BigDecimal(delComma(input));
        }
        return BigDecimal.ZERO;
    }

    /**
     * 阿拉伯數字轉中文小寫金錢數字 123 → 一百二十三；一二三四五六七八九十百千萬億
     * 
     * @param number 要轉換的阿拉伯數字字串
     * @return String 中文小寫金錢數字
     */
    public static String toChineseNumber(Object input) {
        String number = delComma(input);
        if (isNumeric(number) && !number.startsWith("-")) {
            return parseNumberToChinese(new String[] { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" },
                    new String[] { "", "十", "百", "千" }, number);
        }
        return StringUtils.EMPTY;
    }

    /**
     * 阿拉伯數字轉中文大寫金錢數字 123 → 壹佰貳拾參；壹貳參肆伍陸柒捌玖零拾佰仟萬億
     * 
     * @param number 要轉換的阿拉伯數字字串
     * @return String 中文大寫金錢數字
     */
    public static String toChineseNumberFull(Object input) {
        String number = delComma(input);
        if (isNumeric(number) && !number.startsWith("-")) {
            return parseNumberToChinese(new String[] { "零", "壹", "貳", "參", "肆", "伍", "陸", "柒", "捌", "玖" },
                    new String[] { "", "拾", "佰", "仟" }, number);
        }
        return StringUtils.EMPTY;
    }

    /**
     * 數字轉中文
     * 
     * @param unms
     * @param digits
     * @param x
     * @return
     */
    private static String parseNumberToChinese(String[] unms, String[] digits, String x) {
        String result = StringUtils.EMPTY;
        // 去掉字頭0
        x = x.replaceAll("^0+", StringUtils.EMPTY);
        int p = 0;
        int m = x.length() % 4;
        int k = (m > 0 ? x.length() / 4 + 1 : x.length() / 4);
        // 從最左邊的那組開始，向右迴圈
        for (int i = k; i > 0; i--) {
            int len = 4;
            // 當i為最左邊的那組時，組長度可能有變化
            if (i == k && m != 0) {
                len = m;
            }
            String s = x.substring(p, p + len);
            int le = s.length();
            for (int j = 0; j < le; j++) {
                int n = Integer.parseInt(s.substring(j, j + 1));
                if (0 == n) {
                    // 加零的條件：不為最後一位 && 下一位數字大於0 && 以前沒有加過“零”
                    if (j < le - 1 && Integer.parseInt(s.substring(j + 1, j + 2)) > 0 && !result.endsWith(unms[0])) {
                        result += unms[0];
                    }
                } else {
                    // 處理1013一千零"十三"，1113 一千一百"一十三"
                    if (!(n == 1 && (result.endsWith(unms[0]) || result.length() == 0) && j == le - 2)) {
                        result += unms[n];
                    }
                    result += digits[le - j - 1];
                }
            }
            // 如果這組數字不全是 0，則加上單位：萬，億，萬億
            if (0 != Integer.parseInt(s)) {
                result += UNITS_STRINGS[i - 1];
            }
            p += len;
        }
        return result;
    }
}
