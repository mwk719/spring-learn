package com.mwk.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Person {

    private String name;

    private BigDecimal money;

    private Integer age;

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
