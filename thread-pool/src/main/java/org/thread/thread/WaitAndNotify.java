package org.thread.thread;

/**
 * wait notify 测试类
 */
public class WaitAndNotify {



    public static void main(String[] arg0) throws InterruptedException {

        Object lock = new Object();
        Thread thread1 = new Thread(new Runable_01(lock));
        Thread thread3 = new Thread(new Runable_01(lock));
        Thread thread2 = new Thread(new Runable_02(lock));
        thread1.start();
        thread3.start();
        thread2.start();
    }
}
