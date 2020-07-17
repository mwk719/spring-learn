package com.mwk.encrypt;

/**
 * @author MinWeikai
 * @date 2020/7/16 16:49
 */
public class ByteRange {

	public static void main(String[] args) {
//		//最大范围
//		int a = 127;
//		//int a = 383;
//
//		byte b = -128;
//		byte c = 1;
//		int count = 0;
//		for (int i = b; i <= a; i++) {
//			count++;
//			System.out.println("第" + count + "次结果：" + (byte) (c + i));
//		}

		String name = "23435.mwk.mwk";
		System.out.println(name.lastIndexOf(".mwk"));
		System.out.println(name.substring(0,name.lastIndexOf(".mwk")));

	}
}
