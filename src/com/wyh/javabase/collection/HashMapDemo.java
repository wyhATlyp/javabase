package com.wyh.javabase.collection;

import java.util.HashMap;
import java.util.Map;

/**
 *	jdk8中HashMap存储结构：数组 + 链表 + 红黑树
 *	
 *	数组初始容量为16，阈值为16 * 0.75 = 12，元素个数达到阈值后扩大一倍变为32(左移一位)
 *	
 *	发生hash冲突后用拉链法解决冲突：
 *		hash值相同的元素放在一个链上，为了提高性能，当链当长度大于8时转化为红黑树进行存储
 */
public class HashMapDemo {
	
	private static Map<String, Object> map;
	
	public static void main(String[] args) {
		init();
		index();
		map.put("KEY", "VALUE");
	}
	
	/**
	 * 1。创建对象
	 * 初始容量：16
	 * 负载因子：0.75
	 */
	public static void init() {
		map = new HashMap<String, Object>();
		System.out.println("初始化map完成");
	}
	
	/**
	 * 	2。元素保存在数组中位置的计算过程：根据key来确定的(以key = "KEY"为例)
	 * 		2.1	获取元素key的hashCode 74303
	 * 			int hashCode = key.hashCode();
	 * 		2.2	获取hashCode的高十六位 1
	 * 			int high = hashCode >>> 16;
	 * 		2.3	高十六位 异或 hashCode 74302
	 * 			int hash = hashCode ^ high;
	 * 		2.4 (容量 - 1) 与 hash值 14
	 * 			int index = (n - 1) & hash;
	 *
	 * 		源码中的hash()方法：2.1、2.2、2.3
	 * 			int hash = key.hashCode() ^ (key.hashCode >>> 16)
	 * 		put时确定数组索引：
	 * 			int index = (n - 1) & hash
	 */
	public static void index() {
		String key = "KEY";
		System.out.println("\nkey为\"KEY\"的元素保存在数组中位置计算过程：");
		/**
		 * 字符串"KEY"的哈希码：K = 75, E = 69, Y = 89
		 * 		h = 31 * 0 + char('K') = 75
		 * 		h = 31 * 75 + char('E') = 2394
		 * 		h = 31 * 2394 + char('Y') = 74303
		 */
		int h = key.hashCode();
		System.out.println("hashCode为：" + toBinary(h) + "\t十进制为：" + h);//74303

		int high = h >>> 16;//高十六位
		System.out.println("hashCode的高16位：" + toBinary(high) + "\t十进制为：" + high);

		int hash = h ^ high;
		System.out.println("hash值为：" + toBinary(hash) + "\t十进制为：" + hash);//74302

		int index = (16 - 1) & hash;
		System.out.println("数组中的位置：" + toBinary(index) + "\t十进制为：" + index);//14
	}

	/**
	 * 放入第一个元素的逻辑
	 */
	public static void putFirst() {
		//1。
	}
	
	public static String toBinary(int num) {
        return Integer.toBinaryString(num);
    }

}
