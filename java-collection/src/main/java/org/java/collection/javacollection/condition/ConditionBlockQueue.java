package org.java.collection.javacollection.condition;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 基于并发包的condion 实现的阻塞队列
 */
public class ConditionBlockQueue {

    private Object[] queue;

    private int maxCapacity = 1<< 30;

    private int head = 0;

    private int tail = 0;

    private volatile  int size = 0;

    private int maxIndex = 15;

    private int defalutCapacity = 16;

    private final ReentrantLock lock = new ReentrantLock();

    /**
     * 添加元素的信号
     */
    private Condition addCondition = lock.newCondition();

    /**
     * 移除元素的信号
     */
    private Condition removeCondition = lock.newCondition();

    public ConditionBlockQueue(){
        queue = new Object[defalutCapacity];
    }

    public int size(){
        return size;
    }
    /**
     * 初始化指定容量队列
     * @param initCapacity
     * @throws Exception
     */
    public ConditionBlockQueue(int initCapacity) throws Exception {
        if(initCapacity > maxCapacity){
            throw new Exception("too large");
        }

        if(initCapacity <= 0){
            throw new Exception("too small");
        }
        queue = new Object[initCapacity];
        defalutCapacity = initCapacity;
        maxIndex = initCapacity - 1;
    }

    /**
     * 新增
     * @param o
     */
    public boolean offer(Object o){
        lock.lock();
        try{
            /**
             * 如果数组的真是长度 》 总容量 则返回添加失败
             */
            /**
             * 通过信号量控制阻塞
             */
            if(size >= defalutCapacity){
                try {
                    System.out.println(Thread.currentThread().getName()+"队列满了 我要开始等待了");
                    addCondition.await();
                    System.out.println(Thread.currentThread().getName()+"我被唤醒了，要开始offer了");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            /**
             * 如果size = 16  如果添加地17个元素，则直接返回false
             * 如果这时取出一个 size = 15 则还会进来
             * 但是tail 不变 此时tail = 16 则重置队尾
             */
            if(tail > maxIndex){
                tail = 0;
            }

            /**
             * 添加元素
             */
            queue[tail] = o;
            tail ++;
            size ++;
            if(size == 1){
                removeCondition.signalAll();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return true;
    }

    /**
     * 获取
     * @return
     */
    public Object poll(){
        lock.lock();
        try{
            if(size <= 0){
                //进入阻塞
                removeCondition.await();
            }
            if(head > maxIndex){
                head = 0;
            }

            Object o = queue[head];
            head ++;
            size --;

            /**
             * 如果队列长度满了被取走一个，则通知所有的插入阻塞
             */
            if(size == (defalutCapacity -1)){
                System.out.println(Thread.currentThread().getName()+"我要去唤醒等待offer的多线程");
                addCondition.signalAll();
            }
            return o;

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return null;
    }

    public static void main(String[] arg0) throws Exception {
        ConditionBlockQueue queue = new ConditionBlockQueue(1000);
        ExecutorService service = Executors.newCachedThreadPool();
        for(int i = 0; i < 4; i++){
            service.submit(new ConditionBlockTest_Runable_01(queue));
        }
        int i = 0;
        while(i<2500){
            i++;
            queue.offer(i);
        }
        System.out.println("offer 完成");

        Thread.sleep(5000);

        System.out.println("最后queue的大小="+queue.size());
    }
}
