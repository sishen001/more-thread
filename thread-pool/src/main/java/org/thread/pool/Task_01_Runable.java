package org.thread.pool;

public class Task_01_Runable implements Runnable {

    public static int m = 0;

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
            m++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("这是一个单例的任务，只会被初始化一次m=" + m + "--当前线程名字=" + Thread.currentThread().getName());

    }
}
