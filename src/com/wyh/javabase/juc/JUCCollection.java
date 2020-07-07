package com.wyh.javabase.juc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * java.util.concurrent.集合
 */
public class JUCCollection {

//    private static Map<String, String> map = new HashMap<String, String>();
//	private static Map<String, String> map = new Hashtable<String, String>();
    private static Map<String, String> map = new ConcurrentHashMap<String, String>();

//    private static List<Integer> list = new ArrayList<Integer>();
    private static List<Integer> list = new CopyOnWriteArrayList<Integer>();

    private static CountDownLatch count = new CountDownLatch(10);

    static {
        for(int i = 0; i < 100; i ++) {
            list.add(i);
        }
    }

    public static void main(String[] args) {
//        testMap();
        testList();
    }

    public static void testList() {
        for(Integer i : list) {
            System.out.println("消费了" + i);
            list.remove(i);
        }
        System.out.println(list.size());
    }
    
    public static void testMap() {
    	for(int i = 0; i < 10; i ++) {
            final String key = "" + i;
            new Thread(() -> {
                try {
                    mapWrite(key);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    count.countDown();
                }
            }).start();
        }

        while(count.getCount() > 0) {}
        mapRead();
    }

    /**
     *  如果使用HashMap，多个线程同时放数据时，数据会丢失
     */
    public static void mapWrite(String key) throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        map.put(key, key);
        System.out.println("map中添加了新的元素：" + key + "哈希值为：" + (key.hashCode() ^ (key.hashCode() >>> 16)));
    }
    
    public static void mapRead() {
    	System.out.println("结束时map大小为：" + map.size());
        System.out.println("最终map为：" + map.entrySet());
        for(int i = 0; i < 10; i ++) {
        	String key = "" + i;
        	System.out.printf("key=%s,value=%s\n", key, map.get(key));
        }
    }

}
