package com.mwk.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class Person extends Father {

	private Integer id;

	private String name;

	private BigDecimal money;

	private Integer age;

	private Float weight;

	private Date date;

	private Date createDate;

	public Person() {
	}

	public Person(String name, BigDecimal money) {
		this.name = name;
		this.money = money;
	}

	public Person(String name, BigDecimal money, Integer age) {
		this.name = name;
		this.money = money;
		this.age = age;
	}

	public void say() {
		System.out.println("hello person");
	}

	public void see() {
		System.out.println("see person");
	}
}
