package com.mwk.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Person {

    private String name;

    private BigDecimal money;

    public Person() {
    }

    public Person(String name, BigDecimal money) {
        this.name = name;
        this.money = money;
    }

    public void say() {
        System.out.println("hello person");
    }

    public void see() {
        System.out.println("see person");
    }
}
