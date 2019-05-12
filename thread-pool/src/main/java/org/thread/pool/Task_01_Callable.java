package org.thread.pool;

import java.util.concurrent.Callable;

public class Task_01_Callable implements Callable {

    public static int m = 0;

    @Override
    public Object call() throws Exception {
        Thread.sleep(30);
        m ++;
        System.out.println("这是一个单例的任务，只会被初始化一次m="+m+"--当前线程名字="+Thread.currentThread().getName());
        return m;
    }
}
