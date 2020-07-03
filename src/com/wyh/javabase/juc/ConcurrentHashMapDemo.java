package com.wyh.javabase.juc;

import java.util.concurrent.ConcurrentHashMap;

/**
 * ConcurrentHashMap：JDK5后：锁分段机制，默认十六段。 JDK8后：改为CAS
 *      segment0 -> [0,1,2...] -> 链表
 *      segment1 -> [10,11,12...] -> 链表
 *  线程安全的哈希表：HashTable的改进版
 */
public class ConcurrentHashMapDemo {

    public static void main(String[] args) {
        ConcurrentHashMap map = new ConcurrentHashMap();
    }

}
