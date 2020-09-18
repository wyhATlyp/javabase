package com.wyh.javabase.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.DiscardPolicy;
import java.util.concurrent.TimeUnit;

/**
 * 线程池参数:
 * 
 * 定时任务：ScheduledThreadPoolExecutor
 * 非定时任务：ThreadPoolExecutor
 * 
 * 工作流程：
 * 	提交一个任务时，立即执行
 * 	当超出corePoollSize时，尝试把任务放入到队列中
 * 	如果队列不接受或已达到上限尝试执行任务
 *  如果正在运行的任务数（核心线程数+不能放入到队列的任务数）超出maximumPoolSize，执行拒绝策略
 * 
 * 工作队列：
 * 	ArrayBlockingQueue;基于数组的，并发线程数=核心线程数+（提交任务数-核心线程数-队列大小）=提交任务数-队列大小（但不会超过最大线程数，会丢任务）
 *	PriorityBlockingQueue;一个具有优先级得无限阻塞队列
 *	LinkedBlockingQueue; 基于链表的，使用此队列时，线程可以提交到队列中，最大并发线程数=核心线程数（默认链表大小无限制）
 *	SynchronousQueue;使用此队列时，线程不可以提交到队列中，最大并发线程数=最大线程数
 *
 * 拒绝策略：
 * 	AbortPolicy：直接抛出异常-默认策略
 * 	CallerRunsPolicy：只用调用者所在线程来运行任务。
 *	DiscardOldestPolicy：丢弃队列里最近的一个任务，并执行当前任务。
 *	DiscardPolicy：不处理，丢弃掉。
 * 
 */
public class ThreadPoolParamters {
	
	public static void main(String[] args) throws InterruptedException {
		/**
		 * int corePoolSize, 核心线程数：工作中的线程数，超出时-影响效率
         * int maximumPoolSize, 最大线程数-影响效率
         * long keepAliveTime, 空闲线程存活时间
         * TimeUnit unit, 空闲线程存活时间单位
         * BlockingQueue<Runnable> workQueue, 工作队列-OOM
         * ThreadFactory threadFactory, 线程工厂
         * RejectedExecutionHandler handler 拒绝策略-抛出异常
		 */
		ThreadPoolExecutor executorService = new ThreadPoolExecutor(
				0, Integer.MAX_VALUE, 
				60L, TimeUnit.SECONDS, 
				new SynchronousQueue<Runnable>(),
				Executors.defaultThreadFactory(),
				new DiscardPolicy()
				);
		
		for(int i = 0; i < 10; i ++) {
			executorService.execute(new Task());
		}
		
	}
	
	static class Task implements Runnable {

		@Override
		public void run() {
			try {
				TimeUnit.SECONDS.sleep(3);
				System.out.println("当前线程：" + Thread.currentThread());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

}
