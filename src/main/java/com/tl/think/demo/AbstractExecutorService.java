package com.tl.think.demo;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @Author: xue.l
 * @Date: 2018/9/20 11:24
 * @Description:
 * @Version: 1.0.0
 */
public abstract class AbstractExecutorService implements ExecutorService {

    @Override
    public <T> Future<T> submit(Callable<T> command) {
        FutureTask<T> task = new FutureTask<>(command);
        execute(task);
        return task;
    }

    @Override
    public <T> Future<T> submit(Runnable command) {
        FutureTask<T> task = new FutureTask<>(command, null);
        execute(task);
        return task;
    }
}
