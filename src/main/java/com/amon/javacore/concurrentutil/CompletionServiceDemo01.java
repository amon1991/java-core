package com.amon.javacore.concurrentutil;

import java.util.concurrent.*;

/**
 * 利用 CompletionService 来实现批量异步执行多个任务并获取执行结果
 *
 * @author yaming.chen@siemens.com
 * Created by chenyaming on 2020/7/16.
 */
public class CompletionServiceDemo01 {

    public static void main(String[] args) {

        // 创建线程池
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // 创建CompletionService
        CompletionService<Integer> cs = new ExecutorCompletionService<>(executor);

        // 异步执行任务1
        cs.submit(() -> getPriceS1());

        cs.submit(() -> getPriceS2());

        cs.submit(() -> getPriceS3());

        // 获取报价结果
        for (int i = 0; i < 3; i++) {

            try {
                Integer result = cs.take().get();
                System.out.println("result " + i + " : " + result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }

        executor.shutdown();

    }

    private static Integer getPriceS1() {
        try {
            Thread.sleep(15 * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Integer(150);
    }

    private static Integer getPriceS2() {
        try {
            Thread.sleep(20 * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Integer(200);
    }

    private static Integer getPriceS3() {
        try {
            Thread.sleep(10 * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Integer(100);
    }


}
