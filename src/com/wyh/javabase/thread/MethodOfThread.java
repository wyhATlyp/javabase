package com.wyh.javabase.thread;

/**
 * 线程中常见方法
 * 1.start()
 * 2.run()
 * 3.currentThread()
 * 4.getName()
 * 5.setName() 默认Thread-0、Thread-1
 * 6.static sleep() InterruptedException，当前线程进入阻塞状态
 *
 * 7.static yield() 线程让步，释放当前线程cpu的执行权，进入就绪状态
 * 8.join() 其它线程对象加入到当前线程中优先执行，当其它线程执行完再继续执行当前线程，当前线程进入阻塞状态 InterruptedException
 * 9.stop() 强制当前线程停掉-已过时、不安全
 *
 * 10.getPriority() //默认5 1-10 10:优先执行
 * 11.setPriority(int) //高优先级线程高概率被先执行，并不一定先执行
 *
 * 12.sleep() 当前线程进入阻塞状态，可以指定时间，不会释放锁
 *
 */
public class MethodOfThread extends Thread {

    static Thread join = new JoinThread();

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new MethodOfThread();
        thread.setName("自定义线程");
        thread.start();//启动当前线程，调用run方法

        Thread maxPriority = new PriorityThread();
        maxPriority.start();

        while(Thread.activeCount() > 2) {
            Thread.yield();//其它线程都执行完时在执行主线程
        }

//        Thread.currentThread().stop(); //已过时
        System.out.println("3.主线程：" + Thread.currentThread().getName() + ",默认优先级：" + Thread.currentThread().getPriority());
    }

    @Override
    public void run() {
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        join.start();
        try {
            join.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("2.分线程：" + Thread.currentThread().getName());
    }
}

class JoinThread extends Thread {

    @Override
    public void run() {
        try {
            sleep(1000);//睡一秒钟保证正常情况下会最后执行，通过join方法先执行了
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("1.join线程");
    }

}

class PriorityThread extends Thread {

    @Override
    public void run() {
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("0.优先级最高的线程");
    }

}