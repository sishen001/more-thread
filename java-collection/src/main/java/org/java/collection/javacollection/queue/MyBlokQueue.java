package org.java.collection.javacollection.queue;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 自定义队列
 */
public class MyBlokQueue {

    //定义容器，存放数据
    private Object[] queue;
    //定义数组的容量
    private final static int MAX_CAPACITY = 1 << 30;
    //定义数组的头部下标
    private int head = 0;
    //定义数组的尾部下标
    private int tail = 0;
    //定义默认数组长度大小
    private int defaultQueueSize = 16;
    //当前队列大小
    private int size = 0;
    //最大下标是多少
    private int maxIndex = 15;

    public int size(){
        return size;
    }

    private ReentrantLock lock = new ReentrantLock();

    /**
     * 默认队列16个
     */
    public MyBlokQueue(){
        queue = new Object[defaultQueueSize];
    }

    /**
     * 指定队列大小
     * @param initialCapacity
     * @throws Exception
     */
    public MyBlokQueue(int initialCapacity) throws Exception {
        if(initialCapacity >MAX_CAPACITY ){
            throw new Exception("too large");
        }
        if(initialCapacity <= 0){
            throw new Exception("太小");
        }
        queue = new Object[initialCapacity];
        defaultQueueSize = initialCapacity;
    }





    public  boolean  offer(Object o) {
        lock.lock();
        try{
            if(size >= defaultQueueSize ){
                return false;
            }
            if(tail > maxIndex){
                tail = 0;
            }
            queue[tail] = o;
            size ++;
            tail++;
            if(size == 1){
                //通知等待线程去获取队列元素
                synchronized (queue){
                    queue.notifyAll();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return true;
    }

    public Object poll() {

        if(size <= 0){
            return null;
        }
        if(head > maxIndex){
            head = 0;
        }
        Object value = queue[head];
        queue[head] = null;
        System.out.println();
        size --;
        head ++;
        return value;
    }

    public  synchronized Object get() {
        if(size <= 0){
            synchronized (queue){
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return get();
        }else{
            if(head > maxIndex){
                head = 0;
            }
            Object value = queue[head];
            queue[head] = null;
            size --;
            head ++;
            return value;
        }
    }

    public static void main(String[] arg0) throws Exception {
        MyBlokQueue queue = new MyBlokQueue();
        for(int i = 0; i < 10; i++){
            Thread thread1 = new Thread(new BlockTest_Runable_01(queue));
            thread1.start();

        }
        Thread.sleep(5000);
        int i = 1;
        while (true){
             i ++;
            queue.offer(i);
        }

    }
}
