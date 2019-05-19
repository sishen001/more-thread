package org.java.collection.javacollection.queue;

/**
 * 阻塞队列测试辅助类
 */
public class BlockTest_Runable_01 implements Runnable{

    private MyBlokQueue blokQueue;

    public BlockTest_Runable_01(MyBlokQueue blokQueue){
        this.blokQueue = blokQueue;
    }

    @Override
    public void run() {
        while(true){

            System.out.println(Thread.currentThread().getName()+"获取阻塞队列的值"+blokQueue.get()+"--当前阻塞队列大小="+blokQueue.size());
        }
    }
}
