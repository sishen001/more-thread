package org.java.collection.javacollection.queue;

import java.util.concurrent.Callable;

/**
 * 自定义队列 验证是否线程安全辅助类
 */
public class QueueCallable implements Callable {

    private MyValidateNotSafeQueue queue ;

    public QueueCallable(MyValidateNotSafeQueue queue){
        this.queue = queue;
    }

    @Override
    public Object call() throws Exception {
        for(int i =0; i< 1000; i++){
            queue.offer(i);
        }
        return "ok";
    }
}
