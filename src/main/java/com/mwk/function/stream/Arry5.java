package com.mwk.function.stream;

import java.util.Arrays;
import java.util.List;

public class Arry5 {

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 4, 6, 2, 8, 5);
        // List<String> list = Arrays.asList("哈", "暗", "黑", "被");

        System.out.println("-------------------检查所有元素-----------------------");
        System.out.println(list.stream().allMatch(x -> x > 0));
        System.out.println("-------------------检查是否至少匹配一个元素-----------------------");
        System.out.println(list.stream().anyMatch(x -> x > 4));

    }

}
