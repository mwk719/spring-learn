package com.mwk.function.stream;

import java.util.Arrays;

public class Arry2 {

    public static void main(String[] args) {
        // 过滤
        Arrays.asList(1, 3, 5, 7, 9, 3, 2, 6, 6).stream().filter(i -> i % 3 == 0).forEach(System.out::println);
        System.out.println("------------------------------------------");
        // 去重
        Arrays.asList(1, 3, 5, 7, 9, 3, 2, 6, 6).stream().distinct().forEach(System.out::println);
        System.out.println("------------------------------------------");
        // 截取保留
        Arrays.asList(1, 3, 5, 7, 9, 3, 2, 6, 6).stream().limit(3).forEach(System.out::println);
        System.out.println("------------------------------------------");
        // 跳过n个数据
        Arrays.asList(1, 3, 5, 7, 9, 3, 2, 6, 6).stream().skip(2).forEach(System.out::println);
        System.out.println("------------------------------------------");

    }

}
