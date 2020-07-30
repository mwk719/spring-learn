package com.mwk.bottom.exception;

/**
 * try catch 错误跳出
 *
 * @author MinWeikai
 * @date 2019/10/25 15:10
 */
public class TryCatchReturn {

	public static void main(String[] args) {
		//test1();
		//test2();
		test3();
	}

	private static void test1() {
		try {
			System.out.println(1 / 0);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("进入catch");
		}
	}

	private static void test2() {
		try {
			System.out.println(1 / 0);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("进入catch");
		} finally {
			System.out.println("进入finally");
		}
	}

	private static void test3() {
		int a = 1;
		int b = 1;
		try {
			System.out.println(a + b);
			return;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("进入catch");
		} finally {
			System.out.println("进入finally");
		}
	}

}
