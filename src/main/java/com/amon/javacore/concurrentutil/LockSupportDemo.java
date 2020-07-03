package com.amon.javacore.concurrentutil;

import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport是一个编程工具类，可以用于阻塞和唤醒线程
 * 和wait/notify的对比：
 * （1）wait和notify都是Object中的方法,在调用这两个方法前必须先获得锁对象，但是park不需要获取某个对象的锁就可以锁住线程。
 * （2）notify只能随机选择一个线程唤醒，无法唤醒指定的线程，unpark却可以唤醒一个指定的线程。
 *
 * @author yaming.chen@siemens.com
 * Created by chenyaming on 2019/4/10.
 */
public class LockSupportDemo {

    public static void main(String[] args) throws InterruptedException {

        Thread testThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int sum = 0;
                for (int i = 0; i < 10; i++) {
                    sum += i;
                }
                try {
                    Thread.sleep(4000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                LockSupport.park();
                System.out.println("Sum:" + sum);
            }
        });

        testThread.start();

        System.out.println("Wait for notifiy");
        Thread.sleep(2000L);

        // 唤醒指定线程
        LockSupport.unpark(testThread);

    }

}
