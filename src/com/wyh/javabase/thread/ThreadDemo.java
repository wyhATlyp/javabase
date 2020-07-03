package com.wyh.javabase.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 多线程举例：
 * 生产者-消费者：
 *  生产者：添加、增加
 *  消费者：删除、减少
 */
public class ThreadDemo {


    public static void main(String[] args) throws InterruptedException {
        Factory factory = new Factory();

        ExecutorService es = Executors.newFixedThreadPool(200);

        for(int i = 0; i < 100; i ++)
            es.execute(new ProducerTask(factory));//1秒后开始生产产品

        for(int i = 0; i < 80; i ++)
            es.submit(new ConsumerTask(factory));//一进来就开始消费

        es.shutdown();

        while (Thread.activeCount() > 1) {
            Thread.yield();
        }
        System.out.println("最终产品数量：" + Factory.num);
    }

}

/**
 * 工厂：
 *  1.控制产品数量
 *  2.生产产品
 *  3.消费产品
 */
class Factory {

    public static int num;

    public synchronized void product() throws InterruptedException {
        Thread.sleep(10);
        num ++;
        System.out.println(Thread.currentThread().getName() + "生产：" + num);
        notifyAll();
    }

    public synchronized void consumer() throws InterruptedException {
        if(num <= 0) {//使用while代替if解决虚假唤醒问题
            System.out.println("产品不足，等待补货：" + num);
            wait();
        }
        Thread.sleep(10);
        System.out.println(Thread.currentThread().getName() + "消费：" + num);
        num--;
    }

}

/**
 * 生产者线程
 */
class ProducerTask implements Runnable {

    private Factory factory;

    public ProducerTask(Factory factory) {
        this.factory = factory;
    }

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(1);
            factory.product();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

/**
 * 消费者线程
 */
class ConsumerTask implements Runnable {

    private Factory factory;

    public ConsumerTask(Factory factory) {
        this.factory = factory;
    }

    @Override
    public void run() {
        try {
            factory.consumer();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
