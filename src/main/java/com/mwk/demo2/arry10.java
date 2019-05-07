package com.mwk.demo2;

import java.util.ArrayList;
import java.util.List;

/**
 * 集合中删除某元素
 * 
 * @author 闵渭凯
 *
 *         2019年4月12日
 */
public class arry10 {

	public static void main(String[] args) {
		List<Integer> values = new ArrayList<>();
		values.add(0);
		values.add(2);
		values.add(2);
		values.add(4);
		System.out.println(values);

		int daleteValue = 4;
		// 少检查一个元素
		for (int i = 0; i < values.size(); i++) {
			if (values.get(i) == daleteValue) {
				values.remove(i);
			}
		}
		System.out.println(values);

		// 检查了所有元素
		int i = 0;
		while (i < values.size()) {
			if (values.get(i) == daleteValue) {
				values.remove(i);
			} else {
				i++;
			}
		}

		System.out.println(values);

	}

}
