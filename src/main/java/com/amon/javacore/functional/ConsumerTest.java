package com.amon.javacore.functional;

import java.util.function.Consumer;

/**
 * @author yaming.chen@siemens.com
 * Created by chenyaming on 2019/10/21.
 */
public class ConsumerTest {

    public static void main(String[] args) {

        // consumer test
        Consumer f = System.out::println;
        Consumer f2 = (n)-> System.out.println(n+"F2");

        System.out.println("test----");
        f.andThen(f2).accept("test");

        System.out.println("test2----");
        f.andThen(f).andThen(f).andThen(f2).accept("test2");


    }

}
