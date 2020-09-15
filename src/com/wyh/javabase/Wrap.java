package com.wyh.javabase;

public class Wrap {

    public static void main(String[] args) {
        Integer a = new Integer(3);//new Integer(3)
        Integer b = 3;//Integer.valueOf(3)
        Integer c = 3;//Integer.valueOf(3)

        System.out.println(a == b);
        System.out.println(b == c);
    }

}
