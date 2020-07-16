package com.amon.javacore.concurrentutil;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * 使用ForkJoin并行计算框架计算斐波那契数列
 * 经过测试，并行度和cpu相同时，计算时间最短，但是比并行度为4时时间减少并不明显
 *
 * @author yaming.chen@siemens.com
 * Created by chenyaming on 2020/7/16.
 */
public class ForkJoinDemo01 {

    public static void main(String[] args) {

        // 4是并行度
        ForkJoinPool fjp = new ForkJoinPool(4);

        // 创建分治任务
        Fibonacci fib = new Fibonacci(45);

        long bgTime = System.currentTimeMillis();
        // 启动分治任务
        Integer result = fjp.invoke(fib);
        long endTime = System.currentTimeMillis();

        System.out.println("Use time：" + (endTime - bgTime) + "ms");
        System.out.println(result);

    }

    /**
     * 递归计算任务
     */
    static class Fibonacci extends RecursiveTask<Integer> {

        final int n;

        public Fibonacci(int n) {
            this.n = n;
        }

        @Override
        protected Integer compute() {

            if (n <= 1) {
                // 递归出口
                return n;
            }

            Fibonacci f1 = new Fibonacci(n - 1);
            // 创建子任务
            f1.fork();

            Fibonacci f2 = new Fibonacci(n - 2);
            // 等待子任务结果，并合并结果
            return f2.compute() + f1.join();
        }

    }


}
