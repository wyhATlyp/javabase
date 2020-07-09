package com.wyh.javabase;

import com.wyh.javabase.User;

public class ChangeTest {

    public static void main(String[] args) {
        int i = 1;
        change(i);
        System.out.println(i);

        User user = new User();
        user.setAge(1);
        change(user);
        System.out.println(user);

        String str = "1";
        change(str);
        System.out.println(str);
    }

    public static void change(int i) {
        i = 100;
    }

    public static void change(User user) {
        user.setAge(100);
    }

    public static void change(String str) {
        str = "100";
    }

}
