package com.amon.javacore.ucutils;

import java.util.concurrent.locks.LockSupport;

/**
 * @author yaming.chen@siemens.com
 * Created by chenyaming on 2019/4/10.
 */
public class LockSupportTest {

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

        LockSupport.unpark(testThread);

    }

}
