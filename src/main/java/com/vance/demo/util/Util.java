package com.vance.demo.util;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import com.vance.demo.constants.Constant;

/**
 * 工具類別
 * 
 * @author Vance
 */
public class Util {
    /**
     * 當傳入值為null時，回傳一個不可異動的空物件
     *
     * @param <T>      the element type
     * @param iterable the iterable(List, Set)
     * @return
     * @since Apache commons-collections4 CollectionUtils
     */
    public static <T> Iterable<T> emptyIfNull(final Collection<T> collection) {
        return CollectionUtils.emptyIfNull(collection);
    }

    /**
     * 當傳入值為null時，回傳一個不可異動的空物件
     *
     * @param <K> the key type
     * @param <V> the value type
     * @param map the map, possibly <code>null</code>
     * @return an empty map if the argument is <code>null</code>
     * @since Apache commons-collections4 MapUtils
     */
    public static <K, V> Map<K, V> emptyIfNull(final Map<K, V> map) {
        return MapUtils.emptyIfNull(map);
    }

    /**
     * 去除前後的空白字元。
     *
     * @param str
     * @return String
     */
    public static String trim(Object str) {
        if (str == null) {
            return "";
        } else if (str instanceof Double) {
            BigDecimal bd = BigDecimal.valueOf((Double) str);
            return bd.toPlainString();
        } else if (str instanceof Long) {
            BigDecimal bd = BigDecimal.valueOf((Long) str);
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
        for (int i = 0; i < num - str.length(); i++)
            sb.append("0");
        sb.append(str);
        return sb.toString();
    }
}
