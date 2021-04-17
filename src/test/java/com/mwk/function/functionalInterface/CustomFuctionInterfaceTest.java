package com.mwk.function.functionalInterface;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author MinWeikai
 * @date 2021/4/17 15:44
 */
@Slf4j
@SpringBootTest
public class CustomFuctionInterfaceTest {

	@Test
	public void test1() {
		CustomFuctionInterface customFuctionInterface = (str1, str2) -> "hello " + str1 + str2;
		String printStr = customFuctionInterface.printStr("A&", "B");
		System.out.println("printStr = " + printStr);
	}

	@Test
	public void test2() {
		CustomFuctionInterface2 customFuctionInterface2 = () -> "hello world";
		String printStr = customFuctionInterface2.printStr();
		System.out.println("printStr = " + printStr);
	}

	@Test
	public void test3() {
		CustomFuctionTest.execute((s) -> System.out.println("doSomething..." + s));

	}

}
