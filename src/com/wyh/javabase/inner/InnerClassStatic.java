package com.wyh.javabase.inner;

/**
 * 静态内部类
 */
public class InnerClassStatic {

    private int out_i;

    public static int out_static_i;

    public void out_method() {
        System.out.println("外部类方法" + out_i);
    }

    public static void out_static_method() {
        System.out.println("外部类静态方法" + out_static_i);
    }

    public static void main(String[] args) {
    	Inner.in_static_i = 10;
    	Inner.in_static_method();
    	
    	System.out.println();
    	
        Inner in = new Inner();
        in.in_i = 20;
        in.in_method();
    }

    static class Inner {

        private int in_i;

        private static int in_static_i;

        public void in_method() {
        	System.out.println("内部类方法" + in_i);
//            in_i = out_i; //static的内部类不能访问外部类中的非静态的属性
//            out_method(); //static的内部类不能访问外部类中的非静态方法
        }

        public static void in_static_method() {
        	System.out.println("内部类静态方法" + in_static_i);
            in_static_i = out_static_i;
            out_static_method();
        }

    }

}
