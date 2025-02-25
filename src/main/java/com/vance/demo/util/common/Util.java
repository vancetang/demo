package com.vance.demo.util.common;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

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
     * 是否符合freemarker exception string格式 (因為當傳入的字串為null會變成
     * freemarker.core.DefaultToExpression$EmptyStringAndSequenceAndHash)
     * 
     * @param value
     * @return
     */
    public static boolean isFreemarkerEmptyString(Object value) {
        return StringUtil.trim(value).toUpperCase().matches("^FREEMARKER\\.CORE\\.DEFAULTTOEXPRESSION.*$");
    }
}
