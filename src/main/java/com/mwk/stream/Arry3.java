package com.mwk.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Arry3 {

    public static void main(String[] args) {

        System.out.println("------------------------------------------");
        Stream<String> stream = Stream.of("I", "love", "java");
        stream.map(s -> s.toUpperCase()).forEach(System.out::println);
        System.out.println("------------------------------------------");
        Stream<List<String>> streamList = Stream.of(Arrays.asList("a", "b", "c"), Arrays.asList("d", "e", "f"));
        streamList.flatMap(list -> list.stream()).forEach(System.out::println);
       
    }

}
