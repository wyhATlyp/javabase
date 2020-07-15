package com.wyh.javabase.collection;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Set：集合
 * 无序：所有方法均继承自父类
 */
public class SetDemo {

    public static void main(String[] args) {
        Set<String> sets = new HashSet<String>();
        sets.add("123");
        sets.add("12343");
        sets.add("456");
        sets.add("12");
        sets.add("1334");
        sets.add("1");
        Iterator<String> iterator = sets.iterator();
        while(iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

}
