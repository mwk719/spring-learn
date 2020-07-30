package com.mwk.bottom.annotation;

import java.lang.reflect.Method;

public class Test1 {

	@Hello(value = "hello")
	public static void main(String[] args) throws NoSuchMethodException, SecurityException {
		Class<Test1> cls = Test1.class;
		Method method = cls.getMethod("main", String[].class);
		Hello hello = method.getAnnotation(Hello.class);
		System.out.println(hello);
	}

}
