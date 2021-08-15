package com.mwk.entity;

import lombok.Data;

/**
 * 课程
 * @author MinWeikai
 * @date 2021/8/14 17:43
 */
@Data
public class Subject {

	public Subject() {
	}

	public Subject(String name, String value) {
		this.name = name;
		this.value = value;
	}

	/**
	 * 语文，数学，物理
	 */
	private String name;

	/**
	 * 分数
	 */
	private String value;
}
