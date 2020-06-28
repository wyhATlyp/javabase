package com.wyh.javabase.thread;

/**
 * synchronized解决多线程安全问题
 *
 * 同步监视器：俗称锁。
 *  任何一个类的对象都可以充当锁。多个线程必须用同一个对象(锁)，使用时必须考虑对象的唯一性。否则不起作用。一般可以使用：
 *      1.new Object();
 *      2.this
 *      3.Object.class
 * 同步代码：操作共享数据的代码
 *
 * 同步代码块
 *  synchronized(同步监视器){
 *      //同步代码
 *  }
 *
 *  同步方法
 *      静态方法：相当于synchronized(Object.class){方法体}
 *      非静态方法：相当于synchronized(this){方法体}
 *  public synchronized void method(){
 *      //方法体
 *  }
 *
 */
public class Synchronized {

    private static int num = 0;

    private static final Object lock = new Object();

    public synchronized static void incc() {
        num ++;
        System.out.printf("线程为：%s, num为：%d\n", Thread.currentThread().getName(), num);
    }

    public static void main(String[] args) {
        for(int i = 0; i < 100; i ++) {
            new Thread(() -> {
                try {
                    Thread.sleep(2000);//放大线程安全问题
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                /**
                 * 14 getstatic #22 <com/wyh/javabase/thread/Synchronized.lock>
                 * 17 dup
                 * 18 astore_0
                 * 19 monitorenter
                 * 20 getstatic #13 <com/wyh/javabase/thread/Synchronized.num>
                 * 23 iconst_1
                 * 24 iadd
                 * 25 putstatic #13 <com/wyh/javabase/thread/Synchronized.num>
                 * 28 aload_0
                 * 29 monitorexit
                 * 30 goto 38 (+8)
                 * 33 astore_1
                 * 34 aload_0
                 * 35 monitorexit
                 * 36 aload_1
                 * 37 athrow
                 */
//                synchronized (new Object()) {//由于不同线程锁的不是同一个对象，不起作用
//                synchronized (Synchronized.class) {//使用Class类型对象(唯一)充当锁，避免创建锁对象
//                synchronized(lock) {
//                    num++;
//                    System.out.printf("线程为：%s, num为：%d\n", Thread.currentThread().getName(), num);
//                }

                incc();//同步方法
            }).start();
        }

        while(Thread.activeCount() > 2)
            Thread.yield();
        System.out.println("最终num为：" + num);
    }

}
