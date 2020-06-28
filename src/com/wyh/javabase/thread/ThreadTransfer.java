package com.wyh.javabase.thread;

/**
 * 线程交替执行：
 *  wait() 当前线程进入阻塞状态，会释放锁，直到被其它线程唤醒（notify(),notifyAll()）。
 *  notify()、notifyAll() 唤醒被wait()的线程，如果有多个线程被wait(),则notify()唤醒一个优先级高的线程，notifyAll()唤醒所有线程。需要等待notify()的线程执行结束后，自动释放锁后，wait()的线程才会继续执行。
 *      wait(),notify(),notifyAll()三个方法是Object类中的方法，使用时必须在同步方法或同步代码块中,调用者必须为同步监视器(锁)，lock都不可以。
 *      一般情况下需要确保wait()线程先执行wait()，然后notify()线程在执行notify()，否则，wait线程会一直阻塞下去。
 *
 *  实例：
 *      1.线程交替执行时使用。交替打印奇数、偶数
 *      2.生产者、消费者 
 */
public class ThreadTransfer {

    public static boolean flag = false;

    public static Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread notify = new NotifyThread();
        Thread wait = new WaitThread();

        notify.start();
//        Thread.sleep(5000);
        wait.start();
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
