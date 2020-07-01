package com.amon.javacore.ucutils;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * demo模拟了两个线程互相转账的场景，并使用随机时间尝试获取锁的方式降低了活锁的风险
 *
 * @author yaming.chen@siemens.com
 * Created by chenyaming on 2020/5/26.
 */
public class LockAndConditionDemo02 {

    private static final int TRANS_COUNT = 100;

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(TRANS_COUNT * 2);

        Account account01 = new Account(1000);
        Account account02 = new Account(1000);

        // 模拟两个账户同时互相转账的过程
        for (int i = 0; i < TRANS_COUNT; i++) {

            Thread thread01 = new Thread(new AccountThread(account01, account02, countDownLatch));
            Thread thread02 = new Thread(new AccountThread(account02, account01, countDownLatch));

            thread01.start();
            thread02.start();

        }

        // 通过conntDownLatch等待所有线程执行结束
        countDownLatch.await();

        // 验证2个账户通过多次转账后，2个账户的总金额保持不变
        System.out.println("sum num:" + (account01.getBalance() + account02.getBalance()));

    }


    static class AccountThread implements Runnable {

        private Account myAccount;
        private Account tarAccount;
        private CountDownLatch countDownLatch;

        public AccountThread(Account myAccount, Account tarAccount, CountDownLatch countDownLatch) {
            this.myAccount = myAccount;
            this.tarAccount = tarAccount;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {

            try {
                myAccount.transfer(tarAccount, 100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
            }

        }

    }

    static class Account {

        public Account(int balance) {
            this.balance = balance;
        }

        private int balance;
        private final Lock lock = new ReentrantLock();

        public void transfer(Account tar, int amt) throws InterruptedException {

            while (true) {

                /**
                 * tryLock是非阻塞的方式获取锁，破坏了不可抢占条件，不会产生死锁；
                 * 但是有可能2个账户同时执行时，导致某一个账户无法同时获取2把锁，产生了一个活锁场景；
                 * 这边通过增加一个随机获取锁的等待时间，可以大大降低活锁的概率
                 * **/
                if (this.lock.tryLock(new Random(100).nextInt(), TimeUnit.NANOSECONDS)) {

                    try {

                        if (tar.lock.tryLock(new Random(100).nextInt(), TimeUnit.NANOSECONDS)) {

                            try {

                                this.balance -= amt;
                                tar.balance += amt;
                                System.out.println("Thread Name：" + Thread.currentThread().getName());
                                break;

                            } finally {
                                tar.lock.unlock();
                            }

                        }//end if

                    } finally {
                        this.lock.unlock();
                    }

                }//end if

            }//end while

        }

        public int getBalance() {
            lock.lock();
            try {
                return balance;
            } finally {
                lock.unlock();
            }
        }

    }

}
