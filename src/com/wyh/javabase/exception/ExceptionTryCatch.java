package com.wyh.javabase.exception;

import com.wyh.javabase.User;

/**
 * try-catch-finally
 *  0。finally一定会执行
 *  1。return
 *      finally和外部不能同时return
 *      try与catch同时有return，外部不可以有return
 *  2.整体执行顺序
 *      try 无异常 finally finally-return try-return out-return
 *          有异常 catch finally-return catch-return out-return
 */
public class ExceptionTryCatch {

    public static void main(String[] args) {
//        String result = tryCatch();
//        System.out.println("reuslt=" + result);
        System.out.println(constant());
        System.out.println(str());
        System.out.println(obj());
    }

    public static String tryCatch() {
        try {
            int i = 100 / 0;
            return "r-try";
        } catch (ArithmeticException e) {
            System.out.println("算数异常：" + e);
            return "r-ArithmetcException";
        } catch (RuntimeException e) {
            System.out.println("运行时异常：" + e);
            return "r-RuntimeException";
        } catch (Exception e) {
            System.out.println("未知异常：" + e);
            return "r-Exception";
        } finally {
            System.out.println("finally语句");
            return "r-finally";
        }
//        return "r-out";
    }

    /**
     *
     *  ExceptionTable
     *  0   5   11  try中有异常，进入catch
     *  0   5   24  catch没有捕获到,athrow(包含finally)
     *  11  18  24  catch中有异常，athrow(包含finally)
     *
     *  try(0-4)
     *  int i0 = 100;
     *  int i1 = i0;
     *
     *  finally(5-8)
     *  i0 = 300;
     *
     *  try(9-10)
     *  return i1;
     *
     *  catch(11-17)
     *  Exception e1 = "异常";
     *  i0 = 200;
     *  int i2 = i0;
     *
     *  finally(18-21)
     *  i0 = 300;
     *
     *  catch(22-23)
     *  return i2;
     *
     *  任意异常(24)
     *  Exception e3 = "异常";
     *
     *  finally(25-28)
     *  i0 = 300;
     *
     *  throw(28-30)
     *  throw e3;
     *
     *
     *  try -> finally -> try(return)
     *  try -> finally -> throw
     *
     *  try -> catch -> finally -> catch(return)
     *  try -> catch -> finally -> throw
     */
    public static int constant() {
        int i;
        try {
            i = 100;
            return i;
        } catch (Exception e) {
            i = 200;
            return i;
        } finally {
            i = 300;
        }
    }

    /**
     * String str0 = "init";
     * str0 = "s-try";
     *
     * String str1 = str0;
     * str0 = "s-finally";
     *
     * return str1;
     */
    public static String str() {
        String str = "init";
        try {
            str = "s-try";
            return str;
        } finally {
            str = "s-finally";
        }
    }

    /**
     * User user0 = new User();
     * user0.setAge(10);
     *
     * User user1 = user0;
     * user0.setAge(30);
     *
     * return user1;
     */
    public static User obj() {
        User user = new User();
        try {
            user.setAge(10);
            return user;
        } finally {
            user.setAge(30);
        }
    }

}
