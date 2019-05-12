package org.thread.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 *callable 有返回值,是一个异步的接口
 */
public class CallableSimple implements Callable {

    @Override
    public String call() throws Exception {
        String s = "this is call able";
        System.out.println(s);
        return s;
    }

    public static void main(String[] arg0) throws ExecutionException, InterruptedException {
        FutureTask<String> task = new FutureTask(new CallableSimple());
        new Thread(task).start();
        String value = task.get();
        System.out.println("线程返回值："+value);

    }
}
