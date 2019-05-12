package org.thread.syncrinized;


import java.util.stream.IntStream;

/**
 * 测试synchronized 锁方法
 * 在同一个对象可以锁住
 * 不同对象不能锁住
 */
public class Sync01 implements Runnable {
    static int i = 0;

    @Override
    public   void run() {
        //重构
        add();
    }
    private synchronized void add(){
        for (int j = 0; j < 10000; j++) {
            i++;
        }
    }

    /**
     * 测试 两个对象 多个线程，sync 不能锁住，导致线程不安全
     * @return
     */
    public static void testNotLock() throws InterruptedException {
        Sync01 sync01 = new Sync01();
        Sync01 sync02 = new Sync01();
        Thread thread = new Thread(sync01);
        Thread thread2 = new Thread(sync02);
        thread.start();
        thread2.start();
        thread.join();thread2.join();
        System.out.println(i);
    }

    /**
     * 测试一个对象多个线程，sync 是线程安全的
     * @throws InterruptedException
     */
    public static void testLock() throws InterruptedException {
        Sync01 sync01 = new Sync01();
        Thread thread = new Thread(sync01);
        Thread thread2 = new Thread(sync01);
        thread.start();
        thread2.start();
        thread.join();thread2.join();
        System.out.println(i);
    }

    /**
     * 异步处理线程
     */
    public void asyncHandlerThread(){
        Sync01 sync01 = new Sync01();
        IntStream.range(0,1).forEach(i-> {
            System.out.println("aaaaa");
            Thread a = new Thread(sync01);
            a.start();
        });
    }

    public static void main(String[] args)throws  Exception {
        testLock();
        testNotLock();
    }


}
