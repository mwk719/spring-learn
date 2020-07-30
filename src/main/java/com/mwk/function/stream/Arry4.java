package com.mwk.function.stream;

import java.util.Arrays;
import java.util.List;

public class Arry4 {

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 4, 6, 2, 8, 5);
        // List<String> list = Arrays.asList("哈", "暗", "黑", "被");

        System.out.println("-------------------自然排序-----------------------");
        list.stream().sorted().forEach(System.out::println);
        System.out.println("-------------------定制排序-----------------------");
        list.stream().sorted((x, y) -> y.compareTo(x)).forEach(System.out::println);

    }

}
