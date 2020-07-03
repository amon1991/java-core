package com.amon.javacore.concurrentutil;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * CompletableFuture结合函数式编程和异步编程，完成了多任务间串行、and汇聚、or汇聚等复杂逻辑，并且重点关注异常处理逻辑
 *
 * @author yaming.chen@siemens.com
 * Created by chenyaming on 2020/7/2.
 */
public class CompletableFutureDemo01 {

    public static void main(String[] args) {

        System.out.println("1、serial test ------");
        serialDemo();
        System.out.println("2、add test ------");
        andDemo();
        System.out.println("3、or test ------");
        orDemo();
        System.out.println("4、or test ------");
        exceptionDemo();

    }

    /**
     * 串行逻辑
     * <p>
     * CompletionStage<R> thenApply(fn);
     * CompletionStage<R> thenApplyAsync(fn);
     * CompletionStage<Void> thenAccept(consumer);
     * CompletionStage<Void> thenAcceptAsync(consumer);
     * CompletionStage<Void> thenRun(action);
     * CompletionStage<Void> thenRunAsync(action);
     * CompletionStage<R> thenCompose(fn);
     * CompletionStage<R> thenComposeAsync(fn);
     */
    private static void serialDemo() {

        CompletableFuture<String> fo = CompletableFuture.supplyAsync(() -> "Hello")
                .thenApply(s -> s + " amon")
                .thenApply(String::toUpperCase);

        System.out.println(fo.join());

    }

    /**
     * and汇聚关系
     * <p>
     * CompletionStage<R> thenCombine(other, fn);
     * CompletionStage<R> thenCombineAsync(other, fn);
     * CompletionStage<Void> thenAcceptBoth(other, consumer);
     * CompletionStage<Void> thenAcceptBothAsync(other, consumer);
     * CompletionStage<Void> runAfterBoth(other, action);
     * CompletionStage<Void> runAfterBothAsync(other, action);
     */
    private static void andDemo() {

        // 任务执行完无返回
        CompletableFuture<Void> f1 = CompletableFuture.runAsync(() -> {
            System.out.println("T1: 0001");
            new CompletableFutureDemo01().sleep(1, TimeUnit.SECONDS);
            System.out.println("T1: 0002");
        });

        // 任务直接性完有结果返回
        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("T2: 0001");
            new CompletableFutureDemo01().sleep(1, TimeUnit.SECONDS);
            System.out.println("T2: 0002");
            return "T2:finished";
        });

        // 汇聚t1和t2，待t1、t2完成后执行
        CompletableFuture<String> f3 = f1.thenCombine(f2, (s, tf) -> {
            System.out.println("T3 started after " + tf);
            System.out.println("T3: 0001");
            new CompletableFutureDemo01().sleep(1, TimeUnit.SECONDS);
            System.out.println("T3: 0002");
            return "T3:finished";
        });

        System.out.println(f3.join());


    }

    /**
     * or 汇聚关系
     * <p>
     * CompletionStage applyToEither(other, fn);
     * CompletionStage applyToEitherAsync(other, fn);
     * CompletionStage acceptEither(other, consumer);
     * CompletionStage acceptEitherAsync(other, consumer);
     * CompletionStage runAfterEither(other, action);
     * CompletionStage runAfterEitherAsync(other, action);
     */
    private static void orDemo() {

        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
            int t = new Random().nextInt(10);
            new CompletableFutureDemo01().sleep(t, TimeUnit.SECONDS);
            System.out.println("T1");
            return String.valueOf(t);
        });

        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
            int t = new Random().nextInt(10);
            new CompletableFutureDemo01().sleep(t, TimeUnit.SECONDS);
            System.out.println("T2");
            return String.valueOf(t);
        });

        CompletableFuture<String> f3 = f1.applyToEither(f2, s -> s);

        System.out.println(f3.join());

    }

    /**
     * 异常处理
     * <p>
     * CompletionStage exceptionally(fn); -> 类似于 try{}catch{}中的 catch{}
     * CompletionStage<R> whenComplete(consumer); -> 类似于 try{}finally{}中的 finally{},不支持返回结果
     * CompletionStage<R> whenCompleteAsync(consumer);
     * CompletionStage<R> handle(fn); -> 类似于 try{}finally{}中的 finally{},支持返回结果
     * CompletionStage<R> handleAsync(fn);
     */
    private static void exceptionDemo() {

        // 出现异常，则将函数执行结果强制设置为0
        // 注意,使用CompletableFuture异步函数式编程时，是没有办法直接通过try-catch捕获异常的
        CompletableFuture<Integer> f0 = CompletableFuture
                .supplyAsync(() -> 7 / 0)
                .thenApply(r -> r * 10)
                .exceptionally(e -> 0);
        System.out.println(f0.join());

    }

    void sleep(int t, TimeUnit u) {
        try {
            u.sleep(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
