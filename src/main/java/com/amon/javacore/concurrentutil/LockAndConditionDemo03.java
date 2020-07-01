package com.amon.javacore.concurrentutil;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * demo模拟了在一个RPC请求中利用条件变量Condition异步转同步的过程
 *
 * @author yaming.chen@siemens.com
 * Created by chenyaming on 2020/5/27.
 */
public class LockAndConditionDemo03 {

    public static void main(String[] args) {

        Request request = new Request();
        request.setMsg("Msg from client.");

        try {
            Response response = doGet(request, 20);
            System.out.println(response.getMsg());
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    static Response doGet(Request request, long timeout) throws TimeoutException, InterruptedException {

        Future future = new Future();

        Thread requestThread = new Thread(new RequestThread(request, future));
        requestThread.start();

        return future.doGet(timeout);

    }

    static class Future {

        private final Lock lock = new ReentrantLock();
        private final Condition done = lock.newCondition();


        private Response response = null;

        Response doGet(long timeout) throws InterruptedException, TimeoutException {

            lock.lock();

            try {

                long startTime = System.currentTimeMillis();
                while (!isDone()) {

                    // 核心逻辑，等待条件变量的通知，超时则继续执行代码逻辑进行判断
                    done.await(timeout, TimeUnit.SECONDS);
                    long currentTime = System.currentTimeMillis();
                    if (isDone() || currentTime - startTime > timeout * 1000L) {
                        break;
                    }
                }

                if (isDone()) {
                    return response;
                } else {
                    throw new TimeoutException();
                }

            } finally {
                lock.unlock();
            }

        }

        boolean isDone() {
            return response != null;
        }

        public void doReceived(Response res) {

            lock.lock();

            try {
                this.response = res;
                done.signalAll();
            } finally {
                lock.unlock();
            }

        }

    }

    static class RequestThread implements Runnable {

        private Request request;
        private Future future;

        public RequestThread(Request request, Future future) {
            this.request = request;
            this.future = future;
        }

        @Override
        public void run() {

            // mock do request
            System.out.println("Do request,request msg:" + request.getMsg());

            try {
                // 模拟远程调用过程
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Response response = new Response();
            response.setMsg("Msg from server.");

            // 核心逻辑，异步完成调用之后，通知条件变量成立并返回response结果
            future.doReceived(response);

        }

    }

    static class Request {
        private String msg;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

    static class Response {
        private String msg;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

}
