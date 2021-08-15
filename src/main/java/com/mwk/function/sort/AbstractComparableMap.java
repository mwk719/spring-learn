package com.mwk.function.sort;

import java.util.Map;

/**
 * @author MinWeikai
 * @date 2021/8/15 11:03
 */
public abstract class AbstractComparableMap<T> implements Comparable<T> {

	/**
	 * 排序属性名
	 */
	static String fieldName;
	/**
	 * 排序类型
	 * 1 正序，-1倒序
	 */
	static Integer orderType;

	protected static Map<String, String> map1;
	protected static Map<String, String> map2;

	@Override
	public int compareTo(T o) {
		this.buildCompareMap(o);
		return map1.get(fieldName).compareTo(map2.get(fieldName)) < 0 ? orderType : -orderType;
	}

	/**
	 * 构建需要去比较的map
	 *
	 * @param o
	 */
	public abstract void buildCompareMap(T o);
}


