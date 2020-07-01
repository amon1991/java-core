package com.amon.javacore.ucutils;

import java.util.concurrent.locks.StampedLock;

/**
 * 构建一个map对象，实现一组经典的StampedLock读模板和写模板
 *
 * @author yaming.chen@siemens.com
 * Created by chenyaming on 2020/6/11.
 */
public class StampedLockDemo {

    public static void main(String[] args) {

        Point point = new Point();

        Thread writeThread = new Thread(new WriteThread(point));
        Thread readThread = new Thread(new ReadThread(point));

        writeThread.start();
        readThread.start();

    }

    static class ReadThread implements Runnable {

        private Point point;

        public ReadThread(Point point) {
            this.point = point;
        }

        @Override
        public void run() {

            System.out.println("distance from origin:" + point.distanceFromOrigin());

        }

    }

    static class WriteThread implements Runnable {

        private Point point;

        public WriteThread(Point point) {
            this.point = point;
        }

        @Override
        public void run() {

            point.updatePoint(10, 20);

        }

    }

    static class Point {

        private int x, y;

        final StampedLock sl = new StampedLock();

        // 计算到原点的距离
        // 读模板
        double distanceFromOrigin() {

            // 乐观读
            long stamp = sl.tryOptimisticRead();

            int curX = x;
            int curY = y;

            // 判断在读数据的期间内，是否存在写操作，如果存在， sl.validate返回false
            if (!sl.validate(stamp)) {
                // 将乐观读升级为悲观读锁
                // 备注：这边将乐观读升级为悲观读锁，就不需要使用while循环不断进行验证判断了，代码简练不易出错，可以作为一种最佳实践
                stamp = sl.readLock();
                try {
                    curX = x;
                    curY = y;
                } finally {
                    sl.unlockRead(stamp);
                }
            }

            return Math.sqrt(curX * curX + curY * curY);

        }

        // 更新对象的局部变量
        // 写模板
        void updatePoint(int x, int y) {

            long stamp = sl.writeLock();

            try {

                this.x = x;
                this.y = y;

            } finally {
                sl.unlockWrite(stamp);
            }

        }

    }


}
