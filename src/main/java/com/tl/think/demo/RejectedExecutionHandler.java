package com.tl.think.demo;

/**
 * @Author: xue.l
 * @Date: 2018/9/20 12:44
 * @Description:
 * @Version: 1.0.0
 */
public interface RejectedExecutionHandler {

    void rejectExecution(Runnable command, ThreadPoolExecutor executor);
}
