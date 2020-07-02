package com.wyh.javabase.inner;

/**
 * 成员内部类：
 */
public class InnerClassMember {
    
	private int out_i;

	public void out_m() {
	    System.out.println("外部类方法");
    }

    public static void out_static_m() {
	    System.out.println("外部类静态方法");
    }

    public static void main(String[] args) throws ClassNotFoundException {
//    	Class.forName("com.wyh.javabase.inner.InnerClassMember$Inner");
    	
        InnerClassMember out = new InnerClassMember();
        Inner inner = out.new Inner();

        inner.in_m();
        
        System.out.println(Inner.static_final_in_i);
    }

    class Inner {

    	private int in_i = out_i;//InnerClassMember.acceess$000()

//        private static int static_in_i;//成员内部类不可以拥有static的成员

        private static final int static_final_in_i = 2;//

        public void in_m() {
            out_m();//this$0.out_m()
            out_static_m();//InnerClassMember.out_static_m()
            System.out.println("内部类方法");
            System.out.println(in_i);
            System.out.println(static_final_in_i);
        }

    }

}
