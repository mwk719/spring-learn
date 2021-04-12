package com.mwk.entity;

import lombok.Data;

/**
 * 铅笔盒
 *
 * @author MinWeikai
 * @date 2021/4/3 12:55
 */
@Data
public class PencilCase {

	public PencilCase() {
	}

	public PencilCase(String name, Integer num) {
		this.name = name;
		this.num = num;
	}

	private String name;

	private Integer num;
}
