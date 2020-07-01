package com.amon.javacore.ucutils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 构建一个map对象，实现线程安全的对map中的数据进行读写，并实现了按需加载内存中不存在的数据
 * 备注：读写锁支持写锁降级为读锁，不支持读锁升级为写锁
 *
 * @author yaming.chen@siemens.com
 * Created by chenyaming on 2020/6/11.
 */
public class ReadWriteLockDemo {

    public static void main(String[] args) {

        Cache cache = new Cache<String, String>();

        Thread writeThread = new Thread(new WriteCacheThread(cache));
        Thread readThread = new Thread(new ReadCacheThread(cache));

        writeThread.start();
        readThread.start();

    }

    static class ReadCacheThread implements Runnable {

        private Cache cache;

        public ReadCacheThread(Cache cache) {
            this.cache = cache;
        }

        @Override
        public void run() {

            String key = "mykey";
            System.out.println("get value from cache:" + cache.get(key));

        }

    }

    static class WriteCacheThread implements Runnable {

        private Cache cache;

        public WriteCacheThread(Cache cache) {
            this.cache = cache;
        }

        @Override
        public void run() {

            cache.put("mykey", "myvalue");

        }

    }

    static class Cache<K, V> {

        final Map<K, V> m = new HashMap<>();

        final ReadWriteLock rwl = new ReentrantReadWriteLock();
        final Lock readLock = rwl.readLock();
        final Lock writeLock = rwl.writeLock();

        // 读缓存
        V get(K key) {

            V v;
            // 读缓存
            readLock.lock();
            try {
                v = m.get(key);
            } finally {
                readLock.unlock();
            }

            // 缓存存在，直接返回
            if (v != null) {
                return v;
            }

            // 尝试写数据
            writeLock.lock();

            try {

                // 二次判断，防止多线程场景下多次加载缓存的问题
                v = m.get(key);
                if (null == v) {
                    v = (V) new Object();
                    m.put(key, v);
                }

            } finally {
                writeLock.unlock();
            }

            return v;

        }

        // 写缓存
        V put(K key, V value) {

            writeLock.lock();

            try {

                return m.put(key, value);

            } finally {
                writeLock.unlock();
            }

        }

    }


}
