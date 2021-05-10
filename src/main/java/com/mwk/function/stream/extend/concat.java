package com.mwk.function.stream.extend;

import com.google.common.collect.Streams;

import java.util.stream.Stream;

/**
 * @author MinWeikai
 * @date 2021/5/10 17:06
 */
public class concat {

    /**
     * 这是一个非常简单的场景。javastreamapi提供了一种用于连接的静态方法，但只适用于两个流。
     * 有时在一个步骤中浓缩多个流是很方便的。guava跟jOOλ为此提供专用方法。
     * @param args
     */
    public static void main(String[] args) {
        Stream<Integer> s1 = Stream.of(1, 2, 3);
        Stream<Integer> s2 = Stream.of(4, 5, 6);
        Stream<Integer> s3 = Stream.of(7, 8, 9);
        Stream<Integer> s4 = Streams.concat(s1, s2, s3);
//        Assertions.assertEquals(9, s4.count());
        s4.forEach(System.out::println);
        System.out.println(s4);
    }
}
