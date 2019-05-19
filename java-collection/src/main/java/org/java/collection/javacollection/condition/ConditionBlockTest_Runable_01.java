package org.java.collection.javacollection.condition;

/**
 * 阻塞队列测试辅助类
 */
public class ConditionBlockTest_Runable_01 implements Runnable{

    private ConditionBlockQueue blokQueue;

    public ConditionBlockTest_Runable_01(ConditionBlockQueue blokQueue){
        this.blokQueue = blokQueue;
    }

    @Override
    public void run() {
        while(true){
            try{
                Thread.sleep(100);
                System.out.println(Thread.currentThread().getName()+"获取阻塞队列的值"+blokQueue.poll()+"--当前阻塞队列大小="+blokQueue.size());
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
