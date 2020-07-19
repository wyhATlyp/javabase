package com.wyh.javabase.extend;

/**
 * 静态方法重写时不可以加@Override
 */
public class OverideDemo {

    public static void main(String[] args) {
        Parent p = new Child();
        p.method();

        Parent.static_method();
        Child.static_method();
    }

    static class Parent {

        public static void static_method() {
            System.out.println("父类静态方法");
        }

        public void method() {
            System.out.println("父类普通方法");
        }

    }

    static class Child extends Parent {

//        @Override
        public static void static_method() {
            System.out.println("子类静态方法");
        }

        @Override
        public void method() {
            System.out.println("子类普通方法");
        }
    }

}
