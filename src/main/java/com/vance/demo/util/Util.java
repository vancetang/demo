package com.vance.demo.util;

import java.util.Collections;
import java.util.Map;

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
    public static <T> Iterable<T> emptyIfNull(final Iterable<T> iterable) {
        return iterable == null ? Collections.<T>emptyList() : iterable;
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
        return map == null ? Collections.<K, V>emptyMap() : map;
    }
}
