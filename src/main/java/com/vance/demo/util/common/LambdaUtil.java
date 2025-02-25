package com.vance.demo.util.common;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Lambda 轉換工具
 * 
 * @author Vance
 */
public class LambdaUtil {

	/**
	 * function 取第一個(用途:當key值相同時，要做的事情)
	 * 
	 * @return
	 */
	public static <B> BinaryOperator<B> pickFirst() {
		return (k1, k2) -> k1;
	}

	/**
	 * function: 取第二個(用途:當key值相同時，要做的事情)
	 * 
	 * @return
	 */
	public static <B> BinaryOperator<B> pickSecond() {
		return (k1, k2) -> k2;
	}

	/**
	 * Collection to Stream(有做null處理)
	 * 
	 * @param collection
	 * @return
	 */
	public static <C> Stream<C> toStream(Collection<C> collection) {
		return Optional.ofNullable(collection).map(Collection::stream).orElseGet(Stream::empty);
	}

	/**
	 * Map to Stream(有做null處理)
	 * 
	 * @param collection
	 * @return
	 */
	public static <K, V> Stream<Map.Entry<K, V>> toStream(Map<K, V> map) {
		return Optional.ofNullable(map).map(m -> m.entrySet().stream()).orElseGet(Stream::empty);
	}

	/**
	 * Collection To Map (預設collection裡面是什麼，value就什麼，key值相同時預設value取最後一個)
	 * 
	 * @param collection  來源Collection
	 * @param keyFunction 取key的Function
	 * @return
	 */
	public static <C, K> Map<K, C> toMap(Collection<C> collection, Function<? super C, ? extends K> keyFunction) {
		return toMap(collection, keyFunction, Function.identity());
	}

	/**
	 * Collection To Map (key值相同時預設value取最後一個)
	 * 
	 * @param collection    來源Collection
	 * @param keyFunction   取key的Function
	 * @param valueFunction 取value的Function
	 * @return
	 */
	public static <C, K, V> Map<K, V> toMap(Collection<C> collection, Function<? super C, ? extends K> keyFunction,
			Function<? super C, ? extends V> valueFunction) {
		return toMap(collection, keyFunction, valueFunction, pickSecond());
	}

	/**
	 * Collection To Map
	 * 
	 * @param collection    來源Collection
	 * @param keyFunction   取key的Function
	 * @param valueFunction 取value的Function
	 * @param mergeFunction 當key值相同時，要如何處理的Function
	 * @return
	 */
	public static <C, K, V> Map<K, V> toMap(Collection<C> collection, Function<? super C, ? extends K> keyFunction,
			Function<? super C, ? extends V> valueFunction, BinaryOperator<V> mergeFunction) {
		return toStream(collection).collect(Collectors.toMap(keyFunction, valueFunction, mergeFunction));
	}

	/**
	 * Map中value值轉換
	 * 
	 * @param map       來源Map
	 * @param converter value如何轉換的Function(傳入Map中value)
	 * @return
	 */
	public static <K, V, C> Map<K, C> convertMapValue(Map<K, V> map, Function<? super V, ? extends C> converter) {
		return toStream(map).collect(Collectors.toMap((e) -> e.getKey(), (e) -> converter.apply(e.getValue())));
	}

	/**
	 * Map中value值轉換
	 * 
	 * @param map       來源Map
	 * @param converter value如何轉換的Function(傳入Map中key, value)
	 * @return
	 */
	public static <K, V, C> Map<K, C> convertMapValue(Map<K, V> map, BiFunction<K, V, C> converter) {
		return toStream(map).collect(Collectors.toMap(e -> e.getKey(), e -> converter.apply(e.getKey(), e.getValue())));
	}

	/**
	 * Set(資料C) 轉為 List(資料R)
	 * 
	 * @param collection 來源
	 * @param mapper     資料轉換規則
	 * @return
	 */
	public static <C, R> List<R> toList(Set<C> collection, Function<C, R> mapper) {
		return toStream(collection).map(mapper).collect(Collectors.toList());
	}

	/**
	 * Set(資料C) 轉為 Set(資料R)
	 * 
	 * @param collection 來源
	 * @param mapper     資料轉換規則
	 * @return
	 */
	public static <C, R> Set<R> toSet(Set<C> collection, Function<C, R> mapper) {
		return toStream(collection).map(mapper).collect(Collectors.toSet());
	}

	/**
	 * Collection(資料C) 轉為 List(資料R)
	 * 
	 * @param collection 來源
	 * @param mapper     資料轉換規則
	 * @return
	 */
	public static <C, R> List<R> toList(Collection<C> collection, Function<C, R> mapper) {
		return toStream(collection).map(mapper).collect(Collectors.toList());
	}

	/**
	 * Collection(資料C) 轉為 Set(資料R)
	 * 
	 * @param collection 來源
	 * @param mapper     資料轉換規則
	 * @return
	 */
	public static <C, R> Set<R> toSet(Collection<C> collection, Function<C, R> mapper) {
		return toStream(collection).map(mapper).collect(Collectors.toSet());
	}

	/**
	 * Collection(資料C) 轉為 List(資料C) (無其他轉換規則直接轉)
	 * 
	 * @param collection
	 * @return
	 */
	public static <C> List<C> toList(Collection<C> collection) {
		return toStream(collection).collect(Collectors.toList());
	}

	/**
	 * Collection(資料C) 轉為 Set(資料C) (無其他轉換規則直接轉)
	 * 
	 * @param collection
	 * @return
	 */
	public static <C> Set<C> toSet(Collection<C> collection) {
		return toStream(collection).collect(Collectors.toSet());
	}
}
