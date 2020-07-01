package com.amon.javacore.concurrentutil;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Lock对比synchronized的优势
 * 1、能够响应中断
 * 2、支持超时
 * 3、非阻塞地获取锁
 * <p>
 * demo模拟了一个线程在执行过程中多次加锁的场景，并保证多线程访问时对象变量的线程安全
 *
 * @author yaming.chen@siemens.com
 * Created by chenyaming on 2020/5/26.
 */
public class LockAndConditionDemo01 {

    private static final int LOOP_SIZE = 1000;

    public static void main(String[] args) {

        Resource myResource = new Resource();

        Thread t1 = new Thread(new ResourceThread(myResource));
        Thread t2 = new Thread(new ResourceThread(myResource));
        t1.start();
        t2.start();

    }

    static class ResourceThread implements Runnable {

        private Resource resource;

        public ResourceThread(Resource resource) {
            this.resource = resource;
        }

        @Override
        public void run() {
            for (int i = 0; i < LOOP_SIZE; i++) {
                resource.setValue();
            }
        }

    }

    static class Resource {

        // default is unfair lock
        private final Lock lock = new ReentrantLock();
        private int value;

        /**
         * get value
         *
         * @return
         */
        public int getValue() {

            lock.lock();
            try {
                System.out.println("Thread Name:" + Thread.currentThread().getName() + " / Current Value:" + value);
                return value;
            } finally {
                lock.unlock();
            }

        }

        /**
         * set value
         */
        public void setValue() {

            lock.lock();
            try {
                // reentrantlock means the same thread can get the lock object more than once
                value = 1 + getValue();
            } finally {
                lock.unlock();
            }

        }

    }


}
