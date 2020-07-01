package com.amon.javacore.concurrentutil;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 模拟了经典的烧水泡茶程序流程，使用FutureTask实现了线程之间的互相等待，以及任务执行完成之后获取运行结果
 *
 * @author yaming.chen@siemens.com
 * Created by chenyaming on 2020/7/1.
 */
public class FutureTaskDemo {


    public static void main(String[] args) throws ExecutionException, InterruptedException {

        FutureTask<String> ft2 = new FutureTask<>(new Task2());

        FutureTask<String> ft1 = new FutureTask<>(new Task1(ft2));

        Thread t1 = new Thread(ft1);
        t1.start();
        Thread t2 = new Thread(ft2);
        t2.start();

        System.out.println(ft1.get());


    }

    static class Task1 implements Callable<String> {

        FutureTask<String> ft2;

        public Task1(FutureTask<String> ft2) {
            this.ft2 = ft2;
        }

        @Override
        public String call() throws Exception {

            System.out.println("T1:洗茶壶");

            System.out.println("T1:烧开水");

            System.out.println("等待T2拿茶叶");
            System.out.println(ft2.get());

            System.out.println("T1:泡茶");

            return "T1:喝茶了";
        }

    }

    static class Task2 implements Callable<String> {

        @Override
        public String call() throws Exception {

            System.out.println("T2:洗茶壶");

            System.out.println("T2:洗茶杯");

            return "T2：茶叶拿好了";
        }


    }

}
