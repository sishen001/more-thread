package com.tl.think.demo;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * @Author: xue.l
 * @Date: 2018/9/20 11:24
 * @Description:
 * @Version: 1.0.0
 */
public interface ExecutorService extends Executor {

    <T> Future<T> submit(Runnable command);

    <T> Future<T> submit(Callable<T> command);

    void shutdwon();

    boolean isShutdown();
}
