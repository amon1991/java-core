package com.amon.javacore.ucutils;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.function.Function;

/**
 * @author yaming.chen@siemens.com
 * Created by chenyaming on 2020/6/8.
 */
public class SemaphoreDemo02 {

    public static void main(String[] args) throws InterruptedException {

        // 创建对象池
        ObjPool<String, String> pool = new ObjPool<String, String>(10, 5 + "");

        pool.exec(t -> {
            System.out.println(t);
            return t.toString();
        });

    }

    static class ObjPool<T, R> {

        final List<T> pool;
        final Semaphore sem;

        public ObjPool(int size, T t) {
            pool = new Vector<T>();
            for (int i = 0; i < size; i++) {
                pool.add(t);
            }
            sem = new Semaphore(size);
        }

        // 利用对象池的对象，调用func
        R exec(Function<T, R> func) throws InterruptedException {
            T t = null;
            sem.acquire();
            try {
                t = pool.remove(0);
                return func.apply(t);
            } finally {
                pool.add(t);
                sem.release();
            }
        }

    }

}
