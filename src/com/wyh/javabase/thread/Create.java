package com.wyh.javabase.thread;

import java.util.concurrent.*;

/**
 * 创建线程方式
 *  1.继承Thread
 *      new MyThread().start()
 *  2.实现Runnable
 *      new Thread(new MyRunnable()).start()
 *  3.实现Callable 有返回值、支持泛型、可抛异常
 *      new Thread(new FutureTask(new MyCallable())).start()
 *  4.通过线程池获取线程：重复利用
 *      ExecutorService、Executors
 *
 *  Future接口
 *      对Runnable、Callable任务的执行结果进行取消、查询是否完成、获取结果
 *
 *  FutureTask
 *      同时实现了Future、Runnable
 *      构造方法：FutureTask(Callable<V> callable)
 */
public class Create {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Thread t1 = new ExtendsThread();
        t1.start();

        Thread t2 = new Thread(new ImplRunnable());
        t2.start();

        FutureTask<String> f1 = new FutureTask<String>(new ImplCallable());
        Thread t3 = new Thread(f1);
        t3.start();
        while (true) {
            if(f1.isDone()) {
                System.out.println(f1.get());
                break;
            }
        }

        ExecutorService es = Executors.newFixedThreadPool(100);
        ThreadPool t4 = new ThreadPool();
        for(int i = 0; i < 100; i ++) {
            Future<String> future = es.submit(t4);
            System.out.println(future.get());
        }
        es.shutdown();
    }

}

class ExtendsThread extends Thread {

    @Override
    public void run() {
        System.out.println("通过继承Thread创建的线程");
    }

}

class ImplRunnable implements Runnable {

    @Override
    public void run() {
        System.out.println("通过实现Runnable接口定义的线程");
    }

}

class ImplCallable implements Callable<String> {

    @Override
    public String call() throws Exception {
        String str = "call方法返回值";
        Thread.sleep(10);
        System.out.println("通过实现Callable接口创建线程");
        return  str;
    }

}

class ThreadPool implements Callable<String> {

    @Override
    public String call() throws Exception {
        System.out.println("通过线程池创建此线程");
        return "success";
    }
}
