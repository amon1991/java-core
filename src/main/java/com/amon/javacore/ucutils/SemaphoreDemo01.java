package com.amon.javacore.ucutils;

import java.util.concurrent.Semaphore;

/**
 * 通过信号量实现addOne函数的原子性
 *
 * @author yaming.chen@siemens.com
 * Created by chenyaming on 2020/6/8.
 */
public class SemaphoreDemo01 {

    public static void main(String[] args) {

        for (int i = 0; i < 10000; i++) {

            Thread thread = new Thread(new Runner());
            thread.start();

        }

        // 主线程休眠一段时间，保证其他线程执行完毕
        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Counter.getCount());


    }

    public static class Runner implements Runnable {

        @Override
        public void run() {

            try {
                Counter.addOne();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    public static class Counter {

        private static int count;

        private static final Semaphore s = new Semaphore(1);

        public static void addOne() throws InterruptedException {

            s.acquire();
            try {
                count++;
            } finally {
                s.release();
            }

        }

        public static int getCount() {
            return count;
        }

    }

}
