package com.mwk.entity;

import lombok.Data;

/**
 * 书包类
 *
 * @author MinWeikai
 * @date 2021/4/3 12:31
 */
@Data
public class Schoolbag {

	public Schoolbag() {
	}

	public Schoolbag(String color, PencilCase pencilCase) {
		this.color = color;
		this.pencilCase = pencilCase;
	}

	private String color;

	private PencilCase pencilCase;
}
