package com.wyh.javabase.thread;

import java.util.concurrent.locks.ReentrantLock;

/**
 * jdk5.0新增Lock接口
 *
 * 1.ReentrantLock(fair)
 *  fair:默认false true时先来的先执行，有序的执行
 *
 */
public class Lock {

    private static int num = 0;

    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        for(int i = 0; i < 100; i ++) {
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    lock.lock();
                    num ++;
                    System.out.println(Thread.currentThread().getName() + ":" + num);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }).start();
        }

        while(Thread.activeCount() > 2)
            Thread.yield();

        System.out.println(num);
    }

}