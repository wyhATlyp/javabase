package com.wyh.javabase.thread;

/**
 * 多线程举例：
 * 生产者-消费者：生产者最多生产20个产品
 */
public class ThreadDemo {


    public static void main(String[] args) throws InterruptedException {
        Factory factory = new Factory();

        for(int i = 0; i < 100; i ++) {

            ProducerThread producerThread = new ProducerThread(factory);
            producerThread.setName("生产者线程" + i + ":");

            ConsumerThread consumerThread = new ConsumerThread(factory);
            consumerThread.setName("消费者线程" + i + ":");

            producerThread.start();
            consumerThread.start();
        }

        while (Thread.activeCount() > 2)
            Thread.yield();
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
        System.out.println(Thread.currentThread().getName() + num);
        notifyAll();
    }

    public synchronized void consumer() throws InterruptedException {
        if(num < 0)
            wait();
        Thread.sleep(10);
        System.out.println(Thread.currentThread().getName() + num);
        num--;
    }

}

/**
 * 生产者线程
 */
class ProducerThread extends Thread {

    private Factory factory;

    public ProducerThread(Factory factory) {
        this.factory = factory;
    }

    @Override
    public void run() {
        try {
            factory.product();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

/**
 * 消费者线程
 */
class ConsumerThread extends Thread {

    private Factory factory;

    public ConsumerThread(Factory factory) {
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
