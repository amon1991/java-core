package com.amon.javacore.ucutils;

import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 构建了一个使用有界队列的线程池，自定义线程工厂和拒绝策略，并测试了线程超过额定数量后（10+5），触发拒绝策略
 *
 * @author yaming.chen@siemens.com
 * Created by chenyaming on 2020/6/30.
 */
public class ThreadPoolExecutorDemo {

    private static AtomicInteger threadNum = new AtomicInteger(0);


    private static final ThreadPoolExecutor executorService = new ThreadPoolExecutor(
            10,
            10,
            1L,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(5),
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread t = new Thread(r);
                    t.setName("Executor-Demo-thread-" + UUID.randomUUID().toString() + "-" + threadNum.incrementAndGet());
                    if (t.isDaemon()) {
                        t.setDaemon(false);
                    }
                    if (Thread.NORM_PRIORITY != t.getPriority()) {
                        t.setPriority(Thread.NORM_PRIORITY);
                    }
                    return t;
                }
            }, new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            throw new RuntimeException("Reject execution");
        }
    });

    public static void main(String[] args) {

        for (int i = 0; i < 20; i++) {
            try {
                executorService.submit(() -> {

                    System.out.println("Execute Thread Name:" + Thread.currentThread().getName());
                    try {
                        Thread.sleep(5000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Execute Thread Name:" + Thread.currentThread().getName() + ",finished");

                });
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
        executorService.shutdown();

    }

}
