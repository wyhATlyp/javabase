package com.wyh.javabase.collection;

/**
 * Queue：队列、FIFO
 *  新增了一些操作队列的方法。
 *      element 从队列中获取一个元素，不remove，如果队列空，抛出异常
 *      peek 从队列中获取一个元素，不remove，如果队列空，返回null
 *      remove remove，空时抛异常
 *      poll remove，空时返回null
 *      offer 入队，有限的队列中优于add方法，具体实现中一般直接调用add方法
 *
 */
public class QueueDemo {
}
