package com.amon.javacore.functional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author yaming.chen@siemens.com
 * Created by chenyaming on 2019/10/21.
 */
public class StreamTest {

    public static void main(String[] args) {

        List<String> list = new ArrayList<>();
        list.add("amon");
        list.add("bob");
        list.add("amon2");

        Stream<String> stream = list.stream();

        System.out.println("Test filter ###");
        // filter and forEach
        List<String> newList = new ArrayList<>();
        stream.filter(n -> n.contains("amon")).forEach(p -> newList.add(p));

        for (String s : newList) {
            System.out.println(s);
        }

        System.out.println();
        System.out.println("Test map ###");

        stream = list.stream();
        // map and forEach
        stream.map(n -> n.concat(".txt")).forEach(System.out::println);

        System.out.println();
        System.out.println("Test flatMap ###");
        stream = list.stream();
        stream.flatMap(n -> Stream.of(n.split(""))).forEach(System.out::println);

        System.out.println();
        System.out.println("Test reduce ###");
        // reduce
        List<Integer> paramList = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println(paramList.stream().reduce(10, (a, b) -> a + b));

    }

}
