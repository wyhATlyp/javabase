package com.wyh.javabase.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 使用原子类保证变量操作(自增)的原子性
 *  CAS：Unsafe + 自旋锁（硬件对并发操作原子性的支持）
 *  volatile：保证可见性
 */
public class AtomicIncrement {

//    private static int num = 0;

    private static AtomicInteger num = new AtomicInteger(0);

    public static void main(String[] args) {
        int length = 100000;

        increment(length);
        decrement(length / 2);

        while(Thread.activeCount() > 2) {
            Thread.yield();
        }
        System.out.println("num为：" + AtomicIncrement.num);
    }

    public static void increment(int length) {
        ExecutorService es = Executors.newFixedThreadPool(100);
        Thread t = new Thread(() -> {
            incc();
        });
        for(int i = 0; i < length; i ++) {
            es.execute(t);
        }
        es.shutdown();
    }

    public static void decrement(int length) {
        ExecutorService es = Executors.newFixedThreadPool(100);
        Thread t = new Thread(() -> {
            dec();
        });
        for(int i = 0; i < length; i ++) {
            es.execute(t);
        }
        es.shutdown();
    }

    public static void incc() {
//        num ++;
        num.getAndIncrement();
    }

    public static void dec() {
//        num --;
        num.getAndDecrement();
    }

}
