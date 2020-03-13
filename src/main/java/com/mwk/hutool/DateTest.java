package com.mwk.hutool;

import cn.hutool.core.date.DateUtil;

import java.util.Date;

/**
 * @author MinWeikai
 * @date 2020/1/10 17:30
 */
public class DateTest {

	public static void main(String[] args) {
		Date date = new Date();
		System.out.println(date);
		System.out.println(DateUtil.toLocalDateTime(date));
	}
}
