package com.mwk.function.stream.extend;

import one.util.streamex.StreamEx;

/**
 * @author MinWeikai
 * @date 2021/5/10 17:15
 */
public class pairMap {

    /**
     * StreamEx允许您处理流中的相邻对象对，并对它们应用给定的函数。它可以通过使用pairMap函数来实现。
     * 在下面可见的代码片段中，我计算流中每对相邻数字的和。
     * @param args
     */
    public static void main(String[] args) {
        StreamEx<Integer> s1 = StreamEx.of(1, 4, 1, 2, 1);
        StreamEx<Integer> s2 = s1.pairMap(Integer::sum);
        s2.forEach(System.out::println);

    }
}
