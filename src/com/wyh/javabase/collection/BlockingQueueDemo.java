package com.wyh.javabase.collection;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 多线程举例：模拟     BlockingQueue
 * 生产者-消费者：
 *  生产者：添加、增加 put(e) 如果队列满了，一直阻塞，直到队列有空间
 *  消费者：删除、减少 take() 如果队列空了，一直阻塞，直到有元素
 *  
 *  实现原理:lock.newCondition(), condition.await(), conditon.signal()
 *
 *  使用阻塞队列解决线程等待的问题
 */
public class BlockingQueueDemo {

    /**
     * The queued items
     */
    final Object[] items;

    /**
     * items index for next take, poll, peek or remove
     */
    int takeIndex;

    /**
     * items index for next put, offer, or add
     */
    int putIndex;

    /**
     * Number of elements in the queue
     */
    int count;

    /**
     * Main lock guarding all access
     */
    final ReentrantLock lock = new ReentrantLock(false);//非公平锁;

    /**
     * Condition for waiting takes
     */
    private final Condition notEmpty = lock.newCondition();

    /**
     * Condition for waiting puts
     */
    private final Condition notFull = lock.newCondition();

    public BlockingQueueDemo(int capacity) {
        this.items = new Object[capacity];
    }

    public Object take() throws InterruptedException {
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            while (count == 0)
                notEmpty.await();

            //出队
            final Object[] items = this.items;
            Object x = (Object) items[takeIndex];
            items[takeIndex] = null;
            if (++takeIndex == items.length)
                takeIndex = 0;
            count--;
            notFull.signal();
            return x;
        } finally {
            lock.unlock();
        }
    }

    public void put(Object e) throws InterruptedException {
        if (e == null)
            throw new NullPointerException();
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            while (count == items.length)
                notFull.await();

            //入队
            final Object[] items = this.items;
            items[putIndex] = e;
            if (++putIndex == items.length)
                putIndex = 0;
            count++;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return count;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 10个生产线程，6个消费线程，队列大小为3，最终运行队列为3
     * 
     * 解释：
     * 放入10个，去除6个，剩余4个
     * 放入三个后，由于队列满了，第四个无法放入，生产者线程会一直阻塞
     * 
     */
    public static void main(String[] args) throws InterruptedException {
    	
    	BlockingQueueDemo queue = new BlockingQueueDemo(3);
//    	BlockingQueue<Object> queue = new ArrayBlockingQueue<Object>(3);
    	
        //10个消费者
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
            	try {
            		queue.take();//如果队列空了，一直阻塞
					System.out.println(Thread.currentThread().getName() + ":" + queue.size());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            }, "消费者" + i).start();
        }

        //10个生产者
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
            	try {
            		queue.put(new Object());//如果队列满了一直阻塞
					System.out.println(Thread.currentThread().getName() + ":" + queue.size());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            }, "生产者" + i).start();
        }

        TimeUnit.SECONDS.sleep(3);
        System.out.println("最终队列大小：" + queue.size());
    }

}