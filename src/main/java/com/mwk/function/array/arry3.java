package com.mwk.function.array;

/**
 * 求和与平均值
 * 
 * @author 闵渭凯
 *
 *         2019年4月12日
 */
public class arry3 {

	public static void main(String[] args) {
		int LENGTH = 3;
		double[] values = new double[LENGTH];
		for (int i = 0; i < values.length; i++) {
			values[i] = i * i;
		}

		double total = 0;
		for (double element : values) {
			total = total + element;
		}
		System.out.println("total=" + total);

		double average = 0;
		if (values.length > 0) {
			average = total / values.length;
		}
		System.out.println("average=" + average);

	}

}
