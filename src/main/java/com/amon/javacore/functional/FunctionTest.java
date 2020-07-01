package com.amon.javacore.functional;

import java.util.function.Function;

/**
 * @author yaming.chen@siemens.com
 * Created by chenyaming on 2019/10/21.
 */
public class FunctionTest {

    public static void main(String[] args) {

        Function<Integer, Integer> f = s -> {
            return s + 5;
        };
        Function<Integer, Integer> g = s -> {
            s = s * s;
            s = s - 10;
            return s;
        };

        // 先执行g，再用g的运行结果执行f (结果95)
        System.out.println(f.compose(g).apply(10));

        // 先执行f，再用f的执行结果执行g (结果215)
        System.out.println(f.andThen(g).apply(10));

    }

}
