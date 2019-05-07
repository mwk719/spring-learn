package com.mwk.demo2;

/**
 * 二分法查找
 * 
 * @author 闵渭凯
 *
 *         2019年4月12日
 */
public class arry6 {

	private static int binary(double[] values, double searchValue) {
		int front = 0;
		int end = values.length - 1;
		while (front <= end) {
			int mid = (front + end) / 2;
			// 中值与查值相同
			if (values[mid] == searchValue) {
				return mid;
			}
			// 中值>查值，则查值在前半段
			if (values[mid] > searchValue) {
				end = mid - 1;
			}
			// 中值，查值，则查值在后半段
			if (values[mid] < searchValue) {
				front = mid + 1;
			}
		}
		return -1;
	}

	public static void main(String[] args) {
		int LENGTH = 20000;
		double[] values = new double[LENGTH];
		for (int i = 0; i < values.length; i++) {
			values[i] = i * i;
		}
		// System.out.println(Arrays.toString(values));

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
