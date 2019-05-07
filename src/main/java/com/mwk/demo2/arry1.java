package com.mwk.demo2;

import java.util.Arrays;

public class arry1 {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		double[] values = { 1, 2, 5, 9, 10 };
		for (double element : values) {
			element = 0;
		}

		// for (int i = 0; i < values.length; i++) {
		// values[i] = 0;
		// }
		System.out.println(Arrays.toString(values));

	}

}
