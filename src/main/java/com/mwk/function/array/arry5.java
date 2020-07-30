package com.mwk.function.array;

/**
 * 求最大最小值
 * 
 * @author 闵渭凯
 *
 *         2019年4月12日
 */
public class arry5 {

	public static void main(String[] args) {
		int LENGTH = 10;
		double[] values = new double[LENGTH];
		for (int i = 0; i < values.length; i++) {
			values[i] = i * i;
		}

		double largest = values[0];

		for (double element : values) {
			if (element > largest) {
				largest = element;
			}
		}
		System.out.println("largest=" + largest);

	}

}
