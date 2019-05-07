package com.mwk.demo2;

import java.util.Arrays;

/**
 * 元素分隔符
 * 
 * @author 闵渭凯
 *
 *         2019年4月12日
 */
public class arry4 {

	public static void main(String[] args) {
		int LENGTH = 10;
		double[] values = new double[LENGTH];
		for (int i = 0; i < values.length; i++) {
			values[i] = i * i;
		}

		StringBuilder sb = new StringBuilder();

		sb.append("[");
		for (int i = 0; i < values.length; i++) {
			sb.append(values[i]);
			if (i < values.length - 1) {
				sb.append("|");
			}
		}
		sb.append("]");
		System.out.println(sb.toString());

		System.out.println(Arrays.toString(values));

	}

}
