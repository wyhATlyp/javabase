package com.wyh.javabase.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 练习线程交替执行 三个线程ABC,每个线程循环循环十次，打印自己的名字。
 *  输出结果为：ABCABCABC...
 */
public class AlternateTest {

    private static String flag = "A";//A最先执行

    public static void main(String[] args) throws InterruptedException {
        AlternateTest test = new AlternateTest();
        new Thread(test.new A(), "A").start();
        new Thread(test.new B(), "B").start();
        new Thread(test.new C(), "C").start();
    }

    public synchronized static void print() {
        try {
            for(int i = 0; i < 10; i ++) {
                while(!flag.equals(Thread.currentThread().getName())) {
                    AlternateTest.class.wait();
                }
                System.out.print(Thread.currentThread().getName());

                //控制顺序
                if("A".equals(flag)) {
                    flag = "B";
                } else if("B".equals(flag)) {
                    flag = "C";
                } else {
                    flag = "A";
                }
                AlternateTest.class.notifyAll();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class A implements Runnable {
        @Override
        public void run() {
            print();
        }
    }

    class B implements Runnable {
        @Override
        public void run() {
            print();
        }
    }

    class C implements Runnable {
        @Override
        public void run() {
            print();
        }
    }

}
