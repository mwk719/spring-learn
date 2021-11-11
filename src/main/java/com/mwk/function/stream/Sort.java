package com.mwk.function.stream;

import cn.hutool.json.JSONUtil;
import com.mwk.entity.Person;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Sort {

    public static void main(String[] args) {
        Person p1 = new Person("张三", new BigDecimal("10.0"), 1);
        Person p2 = new Person("王五", new BigDecimal("10.0"), 1);
        Person p3 = new Person("李四", new BigDecimal("10.0"), 2);
        Person p4 = new Person("王五", new BigDecimal("10.0"), null);
        Person p5 = new Person("赵六", new BigDecimal("10.0"), 3);
        List<Person> pList = new ArrayList<>();
        pList.add(p1);
        pList.add(p2);
        pList.add(p3);
        pList.add(p4);
        pList.add(p5);


        //适应属性值可能为null的排序

        //将属性值可能为null的元素排在前面
        pList = pList.stream()
                .sorted(Comparator.comparing(Person::getAge, Comparator.nullsFirst(Integer::compareTo)))
                .collect(Collectors.toList());
        System.out.println("将属性值可能为null的元素排在前面 "+ pList);

        //将属性值可能为null的元素排在后面
        pList = pList.stream()
                .sorted(Comparator.comparing(Person::getAge, Comparator.nullsLast(Integer::compareTo)))
                .collect(Collectors.toList());
        System.out.println("将属性值可能为null的元素排在后面 "+ pList);

        //添加序号
        Integer[] arr = {1};
        pList.stream().forEach(e -> e.setId(arr[0]++));
        System.out.println(JSONUtil.toJsonPrettyStr(pList));
    }

}
