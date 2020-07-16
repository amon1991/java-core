package com.amon.javacore.concurrentutil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 利用 CompletionService 来同时请求多个服务，有任一服务先返回就直接返回结果的效果
 *
 * @author yaming.chen@siemens.com
 * Created by chenyaming on 2020/7/16.
 */
public class CompletionServiceDemo02 {

    public static void main(String[] args) {

        // 创建线程池
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // 创建CompletionService
        CompletionService<Integer> cs = new ExecutorCompletionService<>(executor);

        // 用于保存Future对象
        List<Future<Integer>> futures = new ArrayList<>(3);

        // 异步执行任务1,2,3
        futures.add(cs.submit(() -> getService1()));
        futures.add(cs.submit(() -> getService2()));
        futures.add(cs.submit(() -> getService3()));

        try {
            // 获取报价结果
            for (int i = 0; i < 3; i++) {
                try {
                    Integer result = cs.take().get();
                    System.out.println("result " + result);
                    if (null != result) {
                        break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    //e.printStackTrace();
                }
            }
        } finally {

            try {
                for (Future<Integer> future : futures) {
                    future.cancel(true);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        executor.shutdown();

    }

    private static Integer getService1() {
        try {
            Thread.sleep(15 * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Integer(100);
    }

    private static Integer getService2() {
        try {
            Thread.sleep(20 * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Integer(100);
    }

    private static Integer getService3() {
        try {
            Thread.sleep(10 * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Integer(100);
    }

}
