package com.wyh.javabase.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * volatile
 *  保证共享变量对可见性：lock前缀指令
 *      1。当前处理器缓存对数据写回到系统内存
 *      2。其它CPU里缓存的数据失效-缓存一致性协议、总线嗅探机制
 *  不保证原子性：一个原子操作(num++)被分割成几个指令(读、算、写)后，并发下由于写覆盖(一个线程覆盖了另一个线程算的结果)丢失了数据。
 *  保证有序性：禁止指令重排
 */
public class Volatile {

    private static volatile boolean flag = false;

    private static volatile int num = 0;

    public static void main(String[] args) throws InterruptedException {
//        see();
//        atomic();
        sort();
    }

    /**
     * 保证可见性
     */
    public static void see() throws InterruptedException {
        new Thread(() -> {
            while (true) {
                if(flag) {
                    System.out.println("读flag成功");
                    break;
                }
//                synchronized (Volatile.class) {}
            }
        }, "读线程").start();

        Thread.sleep(1000);

        new Thread(() -> {
            flag = true;//写
            System.out.println("写flag成功");
        }, "写线程").start();
    }

    /**
     * 不保证原子性
     *  num ++：
     *      temp = num;
     *      temp = temp + 1;
     *      num = temp;
     */
    public static void atomic() {

        Thread thread = new Thread(() -> {
            num ++;
        });

        ExecutorService es = Executors.newFixedThreadPool(100);
        for(int i = 0; i < 100000; i ++)
            es.execute(thread);
        es.shutdown();

        while(Thread.activeCount() > 2) {
            Thread.yield();
        }
        System.out.println("num为：" + Volatile.num);
    }

    static int a = 0;
    static boolean status = false;

    /**
     * 保证有序性：禁止指令重排
     *  期待结果：1221
     */
    public static void sort() throws InterruptedException {
        new Thread(() -> {
            a = 100;
            status = true;
        }).start();

        new Thread(() -> {
            if(status) {
                System.out.println(a);
            }
        }).start();
    }

}
