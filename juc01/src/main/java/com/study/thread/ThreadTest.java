package com.study.thread;

public class ThreadTest extends  Thread {

    @Override
    public void run() {
        System.out.println("ThreadTest.....run"+Thread.currentThread().getName());
    }

    public static void main(String[] arg0) throws Exception{
        ThreadTest thread1 = new ThreadTest();
        thread1.start();
        thread1.join();
        System.out.println(Thread.currentThread().getName());
        ThreadTest thread2 = new ThreadTest();
        thread2.start();
        System.out.println(Thread.currentThread().getName());
        ThreadTest thread3 = new ThreadTest();
        thread3.start();
        thread3.join();
        System.out.println(Thread.currentThread().getName());
        ThreadTest thread4 = new ThreadTest();
        thread4.start();
        thread4.join();
    }
}
