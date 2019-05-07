package com.mwk.demo2;

/**
 * 线性查找
 * 
 * @author 闵渭凯
 *
 *         2019年4月12日
 */
public class arry7 {

	private static int binary(double[] values, double searchValue) {
		int pos = 0;
		boolean found = false;
		while (!found && pos < values.length) {
			if (values[pos] == searchValue) {
				found = true;
			} else {
				pos++;
			}

		}
		if (found) {
			return pos;
		} else {
			return -1;
		}

	}

	public static void main(String[] args) {
		int LENGTH = 500000;
		double[] values = new double[LENGTH];
		for (int i = 0; i < values.length; i++) {
			values[i] = i * i;
		}
		//System.out.println(Arrays.toString(values));

		long start = System.currentTimeMillis();

		int result = binary(values, 25);
		if (result != -1) {
			System.out.println("Found at position " + result);
		} else {
			System.out.println("Not found");
		}

		long end = System.currentTimeMillis();

		System.out.println("Use time " + (end - start));
	}

}
