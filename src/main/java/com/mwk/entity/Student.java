package com.mwk.entity;

import lombok.Data;

/**
 * 学生类
 *
 * @author MinWeikai
 * @date 2021/4/3 12:30
 */
@Data
public class Student {

	public Student() {
	}

	public Student(String name, Schoolbag bag) {
		this.name = name;
		this.bag = bag;
	}

	private String name;


	private Schoolbag bag;
}
