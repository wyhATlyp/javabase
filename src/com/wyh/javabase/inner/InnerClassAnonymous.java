package com.wyh.javabase.inner;

/**
 * 匿名内部类
 */
public class InnerClassAnonymous {
	
	private static int i = 0;
	
	public static void main(String[] args) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(i);
			}
		}).start();
		
	}

}
