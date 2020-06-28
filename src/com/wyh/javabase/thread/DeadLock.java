package com.wyh.javabase.thread;

/**
 * 死锁：多线程相互占用对方需要的锁，都卡在等待对方释放锁的步骤导致的问题。
 *  两个线程、两把锁。同时执行到：
 *  t1线程：获取锁1后，等待着获取锁2-无法释放锁1
 *  t2线程：获取锁2后，等待着获取锁1-无法释放锁2
 *  结果：程序卡住，不会继续执行、无异常抛出
 *
 *  产生死锁原因：
 *      锁相互嵌套导致，开发中应尽量避免锁嵌套。eg：调用一个api中的同步方法时就可能会出现死锁问题
 */
public class DeadLock {

    static Object lock1 = new Object();
    static Object lock2 = new Object();

    public static void main(String[] args) {
        demo();
    }

    /**
     * 演示死锁问题
     */
    public static void demo() {
        new Thread(() -> {
            synchronized (lock1) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock2) {
                    System.out.println("线程1执行");
                }
            }
        },"t1").start();

        new Thread(() -> {
            synchronized(lock2) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock1) {
                    System.out.println("线程2执行");
                }
            }
        }, "t2").start();
    }

}
