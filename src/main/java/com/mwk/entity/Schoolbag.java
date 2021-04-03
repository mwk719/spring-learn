package com.mwk.entity;

import lombok.Builder;
import lombok.Data;

/**
 * 书包类
 *
 * @author MinWeikai
 * @date 2021/4/3 12:31
 */
@Builder
@Data
public class Schoolbag {

	private String color;

	private PencilCase pencilCase;
}
