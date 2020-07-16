package com.wyh.javabase.collection;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * HashMap与Hashtable区别：
 *	1.Hashtable是线程安全对 synchronized
 *	2.HashTable不允许key/value为空
 */
public class HashtableDemo {
	
	public static void main(String[] args) {
		testMap();
		testTable();
	}
	
	public static void testMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put(null, null);
		System.out.println(map);
	}
	
	public static void testTable() {
		Map<String, String> map = new Hashtable<String, String>();
		map.put(null, null);
		System.out.println(map);
	}

}
