package com.mwk.function.array;

import java.util.Arrays;

/**
 * 填充
 * 
 * @author 闵渭凯
 *
 *         2019年4月12日
 */
public class arry2 {

	public static void main(String[] args) {
		int LENGTH = 10;

		double[] values = new double[LENGTH];

		for (int i = 0; i < values.length; i++) {
			values[i] = i * i;
		}

		System.out.println(Arrays.toString(values));

	}

}
