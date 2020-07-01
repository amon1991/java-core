package com.amon.javacore.ucutils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author yaming.chen@siemens.com
 * Created by chenyaming on 2020/6/11.
 */
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {


        Executor executor = Executors.newFixedThreadPool(2);

        CountDownLatch cdl = new CountDownLatch(2);

        executor.execute(() -> {
            getPOrders();
            cdl.countDown();
        });

        executor.execute(() -> {
            getDOrders();
            cdl.countDown();
        });

        // 等待
        cdl.await();

        checkDiff();

    }

    static private void getPOrders() {

        System.out.println("start get p order");

        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("finish get p order");

    }

    static private void getDOrders() {

        System.out.println("start get d order");

        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("finish get d order");

    }

    static private void checkDiff() {

        System.out.println("check diff finished");

    }

}
