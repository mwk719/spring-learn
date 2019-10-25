package com.mwk.optional;

import java.util.Optional;

public class Test1 {

	private Integer a = 1;
	private Integer b = 5;

	public static void main(String[] args) throws Exception {
		Test1 test = new Test1();

		Optional<Test1> opt = Optional.ofNullable(test);
		System.out.println(opt.isPresent());
	}

	public void sum() throws Exception {
		//Test1 test = null;
		Test1 test = new Test1();

		Optional<Test1> opt = Optional.ofNullable(test);
	}

}
