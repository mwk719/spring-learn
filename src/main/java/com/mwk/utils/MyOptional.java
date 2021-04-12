/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
package com.mwk.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mwk.entity.Schoolbag;
import com.mwk.entity.Student;

import java.util.Objects;

/**
 * @author MinWeikai
 * @date 2021/4/3 12:57
 */
public final class MyOptional {


    /**
     * 获取层级对象的值通过层级属性
     *
     * @param <T>
     * @param source 源对象
     * @param filed  可为层级属性，属性名用小圆点.分隔
     * @param vClass 属性类型
     * @return
     */
    public static <T> T getValueByField(Object source, String filed, final Class<T> vClass) {
        Objects.requireNonNull(filed);
        String[] filedNames = filed.split("\\.");
        Object oldTemp;
        if (filedNames.length == 1) {
            oldTemp = ReflectionUtil.invokeGetterMethodNoThrowException(source, filed);
            return new ObjectMapper().convertValue(oldTemp, vClass);
        }
        oldTemp = ReflectionUtil.invokeGetterMethodNoThrowException(source, getFirstStrByPoint(filed));
        if (Objects.isNull(oldTemp)) {
            return null;
        }
        oldTemp = getValueByField(oldTemp, getEndStrByPoint(filed), vClass);
        return new ObjectMapper().convertValue(oldTemp, vClass);
    }


    private static String getFirstStrByPoint(String str) {
        return str.substring(0, str.indexOf("."));
    }

    private static String getEndStrByPoint(String str) {
        return str.substring(str.indexOf(".") + 1);
    }


    public static void main(String[] args) {
        Student student = new Student("李明",
                new Schoolbag("黄色", null));
//				.name()
//				.bag(Schoolbag.builder()
//						.color("黄色")
//						.pencilCase(
//								PencilCase.builder()
//										.name("中性笔")
//										.num(2)
//										.build()
//						)
//						.build())
//				.build();

        System.out.println(getValueByField(student, "name", String.class));
        System.out.println(getValueByField(student, "bag", Schoolbag.class));
        System.out.println(getValueByField(student, "bag", Schoolbag.class).getColor());
		System.out.println(getValueByField(student, "bag.color", String.class));
		System.out.println(getValueByField(student, "bag.pencilCase.num", Integer.class));

		// 随意测试值，找不到应该为null
		System.out.println(getValueByField(student, "ww.ee.num", Integer.class));
		System.out.println(getValueByField(student, "bag.ee.num", Integer.class));
		System.out.println(getValueByField(null, "bag.ee.num", Integer.class));
    }

}
