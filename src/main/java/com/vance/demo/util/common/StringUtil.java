package com.vance.demo.util.common;

import java.math.BigDecimal;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.vance.demo.constant.Constant;

import lombok.experimental.UtilityClass;

/**
 * 字串處理工具
 * 
 * @author Vance
 */
@UtilityClass
public class StringUtil {
    /**
     * 去除前後的空白字元。
     *
     * @param str
     * @return String
     */
    public static String trim(Object str) {
        if (Objects.isNull(str)) {
            return StringUtils.EMPTY;
        } else if (str instanceof Double d) {
            BigDecimal bd = BigDecimal.valueOf(d);
            return bd.toPlainString();
        } else if (str instanceof Long l) {
            BigDecimal bd = BigDecimal.valueOf(l);
            return bd.toPlainString();
        } else {
            String string = str.toString();
            char[] chars = string.toCharArray();
            int begin = 0;
            while (begin < chars.length
                    && (chars[begin] <= Constant.CHAR_SPACE || chars[begin] == Constant.CHAR_FULL_SPACE)) {
                ++begin;
            }
            int end = chars.length - 1;
            while (end > begin && (chars[end] <= Constant.CHAR_SPACE || chars[end] == Constant.CHAR_FULL_SPACE)) {
                --end;
            }
            if (begin == 0 && end == chars.length - 1) {
                return string;
            }
            return new String(chars, begin, end - begin + 1);
        }
    }

    /**
     * 字串前補0。
     * 
     * @param str
     * @param num
     * @return 補0後之字串
     */
    public static String addZeroWithValue(String str, int num) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < num - str.length(); i++) {
            sb.append("0");
        }
        sb.append(str);
        return sb.toString();
    }
}
