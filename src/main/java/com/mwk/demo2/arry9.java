package com.mwk.demo2;

import java.util.Arrays;

/**
 * 数组中删除某个元素
 * 
 * @author 闵渭凯
 *
 *         2019年4月12日
 */
public class arry9 {

	public static void main(String[] args) {
		int LENGTH = 10;
		double[] values = new double[LENGTH];
		for (int i = 0; i < values.length; i++) {
			values[i] = i * i;
		}
		Arrays.sort(values);
		System.out.println(Arrays.toString(values));

	}

}
