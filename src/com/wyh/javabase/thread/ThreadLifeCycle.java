package com.wyh.javabase.thread;

/**
 * 线程生命周期
 *  线程状态：Thread.State
 *      新建：new -初始状态
 *      就绪：新建转换：start()  运行转换：运行状态失去cpu执行权或yield() 阻塞转换：阻塞状态结束
 *      运行：就绪转换：获取cpu资源，执行run()
 *      阻塞：运行转换：sleep()、join()、同步锁、wait()、suspend(过时了) 阻塞状态只可以转换为就绪状态
 *      死亡：运行转换：1.stop() 2.结束：执行完run()或异常 -最终状态
 *
 *                ⬇ <- 阻塞 <- ⬆
 *               ⬇            ⬆
 *      新建 -> 就绪 <------> 运行 -> 死亡
 */
public class ThreadLifeCycle extends Thread {

    public static void main(String[] args) {
        Thread thread = new Thread();
        thread.start();
    }

    @Override
    public void run() {
        System.out.println("线程运行了");
    }

}
