package com.mwk.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Arry1 {

    public static void main(String[] args) {
        // 1.Collection的默认方法stream()和parallelStream()
        List<String> list = Arrays.asList("a", "b", "c");
        Stream<String> stream = list.stream();// 顺序流
        Stream<String> parellelStream = list.parallelStream();
        System.out.println(stream.findFirst().get());
        System.out.println(parellelStream.findFirst().get());

        // 2. Arrays.stream()
        IntStream intStream = Arrays.stream(new int[] { 1, 2, 3 });
        Stream<Integer> integerStream = Arrays.stream(new Integer[] { 1, 2, 3 });

    }

}
