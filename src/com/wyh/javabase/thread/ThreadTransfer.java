package com.wyh.javabase.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * 线程交替执行：
 *  wait() 当前线程进入阻塞状态，会释放锁，直到被其它线程唤醒（notify(),notifyAll()）。
 *      1。开发中一定要注意的问题：wait()方法要确保可以被notify()，否则线程会一直阻塞下去
 *      2。wait()存在虚假唤醒问题：多个线程在某个时刻同时处于wait()状态，另外一个线程进行notifyAll()时，同时唤醒了所有线程。导致同步机制失效。
 *          解决：将wait()放在while循环中或修改为notify()
 *
 *
 *  notify()、notifyAll() 唤醒被wait()的线程，如果有多个线程被wait(),则notify()唤醒一个优先级高的线程，notifyAll()唤醒所有线程。需要等待notify()的线程执行结束后，自动释放锁后，wait()的线程才会继续执行。
 *      wait(),notify(),notifyAll()三个方法是Object类中的方法，使用时必须在同步方法或同步代码块中,调用者必须为同步监视器(锁)，lock都不可以。
 *
 *  实例：
 *      1.线程交替执行时使用。交替打印奇数、偶数
 *      2.生产者、消费者 
 */
public class ThreadTransfer {

    public static boolean flag = false;

    public static Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
//        normal();
        mockWait();
    }

    private static void normal() {
        Thread notify = new NotifyThread();
        Thread wait = new WaitThread();

        notify.start();
//        Thread.sleep(5000);
        wait.start();
    }

    /**
     * 虚假唤醒:
     *  期待结果：1 0 1 0
     */
    private static int i = 0;

    public static void mockWait() {
        ThreadTransfer lock = new ThreadTransfer();

        class IncrementTask implements Callable {
            @Override
            public Object call() throws Exception {
                TimeUnit.SECONDS.sleep(2);
                synchronized (lock) {
                    System.out.println(Thread.currentThread().getName() + ++i);
                    lock.notifyAll();
                }
                return "SUCCESS";
            }
        }

        class DecTask implements Callable {
            @Override
            public Object call() throws Exception {
                synchronized (lock) {
                    while (i <= 0) {//此处如果使用if，则结果会变为1 0 -1 0
                        lock.wait();
                    }
                    System.out.println(Thread.currentThread().getName() + -- i);
                }
                return "SUCCESS";
            }
        }

        new Thread(new FutureTask(new IncrementTask()), "加一线程1：").start();
        new Thread(new FutureTask(new IncrementTask()), "加一线程2：").start();

        new Thread(new FutureTask(new DecTask()),"减一线程1：").start();
        new Thread(new FutureTask(new DecTask()),"减一线程2：").start();
    }

}

class WaitThread extends Thread {

    public WaitThread() {
        super("wait线程:");
        setPriority(Thread.MAX_PRIORITY);
    }

    @Override
    public void run() {
        synchronized(ThreadTransfer.lock) {
            try {
                if(!ThreadTransfer.flag) {//如果NotifyThread的notify()已经执行过了，就不可以在wait了
                    System.out.println(getName() + "wait");
                    ThreadTransfer.lock.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(ThreadTransfer.flag) {
                System.out.println(getName() + "线程已被唤醒");
            }
        }
    }

}

class NotifyThread extends Thread {


    public NotifyThread() {
        super("notify线程:");
        setPriority(Thread.MIN_PRIORITY);
    }

    @Override
    public void run() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (ThreadTransfer.lock) {
            ThreadTransfer.flag = true;
            ThreadTransfer.lock.notify();
            System.out.println(getName() + "唤醒线程");
            try {
                Thread.sleep(3000);//放慢释放锁的时间
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(getName() + "释放锁");
        }
    }

}
