package com.wyh.javabase.inner;

/**
 * 局部内部类
 */
public class InnerClassLocal {

    private int out_i;

    public void out_method() {
    	System.out.println("out_method start");

        class Inner {

        	private int in_i = out_i;
        	
        	public void in_method() {
        		System.out.println("in_method start");
        		System.out.println(in_i);
        		System.out.println("in_method end");
        	}
            
        }
        
        new Inner().in_method();
        
        
        System.out.println("out_method end");

    }
    
    public static void main(String[] args) {
    	InnerClassLocal out = new InnerClassLocal();
    	out.out_method();
	}

}
