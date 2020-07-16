package com.wyh.javabase.collection;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

/**
 * 快速失败机制：fail-fast
 * 	并发修改异常：ConcurrentModificationException
 * 	遍历集合时不允许对集合进行修改
 * 		直接遍历集合：list.forEach()
 * 		通过迭代器遍历结婚：iterator.next() - toString()、for(List l : list){}
 */
public class FailFastDemo {
	
//	private static List<Integer> list = new ArrayList<Integer>() {
	private static List<Integer> list = new CopyOnWriteArrayList<Integer>() {
		private static final long serialVersionUID = 1L;

		{
			for(int i = 0; i < 10; i ++) {
				add(i + 100);
			}
		}
	};

	/**
	 * 相当于for(Integer obj : list) {}
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("初始list：" + list);
		sinThread();
		mulThread();
		System.out.println("移除元素后list:" + list);
	}

	/**
	 * 单线程下
	 */
	public static void sinThread() {
		for (Iterator<Integer> iterator = list.iterator();iterator.hasNext();) {
			Integer obj = iterator.next();
			list.remove(obj);
//			iterator.remove();
		}
	}

	/**
	 * 多线程下
	 */
	public static void mulThread() {
		new Thread(() -> {
			try {
				while(true) {
					for (int i = 0; i < list.size(); i++) {
						System.out.println(list);
					}
					TimeUnit.SECONDS.sleep(1);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, "遍历元素线程").start();

		new Thread(() -> {
			try {
				for (int i = 0; i < 60; i++) {
					TimeUnit.SECONDS.sleep(1);
					list.add(123);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}, "修改元素线程").start();
	}

}
