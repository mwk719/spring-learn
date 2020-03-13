package com.mwk.stream;

import com.mwk.entity.Person;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

public class sum {

	public static void main(String[] args) {
		List<Integer> list = Arrays.asList(1, 4, 6, 2, 8, 5);

		//统计求和
		IntSummaryStatistics collect = list.stream().collect(Collectors.summarizingInt(value -> value));
		System.out.println(collect);
		System.out.println("统计集合元素的个数：" + collect.getCount());
		System.out.println("集合元素累加之和：" + collect.getSum());
		System.out.println("集合中最小值：" + collect.getMax());
		System.out.println("集合中最大值：" + collect.getMin());
		System.out.println("集合中平均值：" + collect.getAverage());

		Person p1 = new Person("张三", new BigDecimal("10.0"),1);
		Person p2 = new Person("王五", new BigDecimal("10.0"),1);
		Person p3 = new Person("李四", new BigDecimal("10.0"),2);
		Person p4 = new Person("王五", new BigDecimal("10.0"),2);
		Person p5 = new Person("赵六", new BigDecimal("10.0"),3);
		List<Person> pList = new ArrayList<>();
		pList.add(p1);
		pList.add(p2);
		pList.add(p3);
		pList.add(p4);
		pList.add(p5);

		//对象属性求和
		BigDecimal reduce = pList.stream().map(Person::getMoney).reduce(BigDecimal.ZERO, BigDecimal::add);
		System.out.println("工资总计：" + reduce);

		//数字求和
		Integer sum= pList.stream().mapToInt(Person::getAge).sum();
		System.out.println(sum);
	}

}
