package com.wyh.javabase.juc;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class JUCWait {

    private static LinkedList<Object> products = new LinkedList<Object>();

    private static Lock lock = new ReentrantLock();

    private static Condition condition = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        //10个消费者
        for(int i = 0; i < 10; i ++) {
            new Thread(() -> {
                try {
                    lock.lock();
                    condition.await();
                    products.poll();
                    System.out.println(Thread.currentThread().getName() + ":" + products.size());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }, "消费者" + i).start();
        }

        //10个生产者
        for(int i = 0; i < 10; i ++) {
            new Thread(() -> {
                try {
                    lock.lock();
                    products.add(new Object());
                    System.out.println(Thread.currentThread().getName() + ":" + products.size());
                    condition.signal();
                } finally {
                    lock.unlock();
                }
            }, "生产者" + i).start();
        }

        TimeUnit.SECONDS.sleep(3);
        System.out.println("最终产品数量：" + products.size());
    }

}
