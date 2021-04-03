package com.mwk.entity;

import lombok.Builder;
import lombok.Data;

/**
 * 学生类
 *
 * @author MinWeikai
 * @date 2021/4/3 12:30
 */
@Builder
@Data
public class Student {

	private String name;


	private Schoolbag bag;
}
