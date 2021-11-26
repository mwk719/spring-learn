package com.mwk.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 书包类
 *
 * @author MinWeikai
 * @date 2021/4/3 12:31
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Schoolbag extends BasePojo{

	public Schoolbag() {
	}

	public Schoolbag(Integer id, String color) {
		this.color = color;
		super.setId(id);
	}

	public Schoolbag(String color, PencilCase pencilCase) {
		this.color = color;
		this.pencilCase = pencilCase;
	}

	private String color;

	private PencilCase pencilCase;
}
