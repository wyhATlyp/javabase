package com.wyh.javabase.juc;

public class Singleton {

    public static void main(String[] args) {
        for(int i = 0; i < 10; i ++) {
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + getInstance());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private static Singleton instance = null;

    private Singleton() {

    }

    public static Singleton getInstance() throws InterruptedException {
        if(instance == null) {
            Thread.sleep(300);
            instance = new Singleton();
        }
        return instance;
    }

}
