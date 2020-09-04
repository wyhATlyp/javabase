package com.wyh.javabase.collection;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * HashMap：适用于在Map中插入、删除和定位元素。
 * TreeMap：适用于按自然顺序或自定义顺序遍历键(key)。
 */
public class MapCompare {

    public static void main(String[] args) {
        Map<String, String> hash = new HashMap<String, String>();


        Map<String, String> tree = new TreeMap<String, String>();

        for(int i = 0; i < 15; i ++) {
            hash.put("" + i, "hash");
            tree.put("" + i, "tree");
        }

        System.out.println("HashMap遍历结果：");
        hash.forEach((k, v) -> {
            System.out.print(k + " ");
        });

        System.out.println("\nTreeMap遍历结果：");
        tree.forEach((k, v) -> {
            System.out.print(k + " ");
        });
    }

}
