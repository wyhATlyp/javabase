package com.wyh.javabase.exception;

import java.util.concurrent.TimeUnit;

/**
 * 异常分类:
 *  Throwable
 *      Error：JVMError
 *      Exception：
 *          非RuntimeException(可检查)：ClassNotFountdException、FileNotFoundException、InterruptedException
 *          RuntimException(不可检查)：空指针、除数为0、ClassCastException
 *
 *  -Xms100m -Xmx100m
 */
public class ExceptionTypes {

    private byte[] bytes = new byte[1024 * 1024 * 200];

    public static void main(String[] args) {
        check();
        uncheck();
        error();
    }

    /**
     * Error
     */
    public static void error() {
        new ExceptionTypes();
    }

    /**
     * 可检查异常
     */
    public static void check() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 不可检查
     */
    public static void uncheck() {
        int i = 100 / 0;
    }

}
