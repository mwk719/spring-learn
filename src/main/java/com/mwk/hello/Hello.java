package com.mwk.hello;

/**
 * 将单个文件打成jar包
 * http://www.cppcns.com/ruanjian/java/239756.html
 *
 * @author MinWeikai
 * @date 2020-06-22 18:06:45
 */
public class Hello {

	public static void main(String[] args) {
		Hello hello = new Hello();
		hello.sayHello("https://minwk.top/");
	}

	private void sayHello(String word) {
		System.out.println("hello " + word);
	}
}