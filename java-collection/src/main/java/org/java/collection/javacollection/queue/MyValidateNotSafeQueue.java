package org.java.collection.javacollection.queue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 自定义队列
 */
public class MyValidateNotSafeQueue {

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

    private final ReentrantLock lock = new ReentrantLock();

    public int size(){
        return size;
    }


    /**
     * 默认队列16个
     */
    public MyValidateNotSafeQueue(){
        queue = new Object[defaultQueueSize];
    }

    /**
     * 指定队列大小
     * @param initialCapacity
     * @throws Exception
     */
    public MyValidateNotSafeQueue(int initialCapacity) throws Exception {
        if(initialCapacity >MAX_CAPACITY ){
            throw new Exception("too large");
        }
        if(initialCapacity <= 0){
            throw new Exception("太小");
        }
        queue = new Object[initialCapacity];
        defaultQueueSize = initialCapacity;
    }


    public boolean add(Object o) throws Exception {
        if(size >= defaultQueueSize ){
            throw new Exception("out size");
        }
        if(tail > maxIndex){
            tail = 0;
        }
        queue[tail] = o;
        size ++;
        tail ++;
        return true;
    }


    public boolean offer(Object o) {

        if(size >= defaultQueueSize ){
            return false;
        }
        if(tail > maxIndex){
            tail = 0;
        }
        lock.lock();
        try{
            queue[tail] = o;
            size ++;
            tail++;
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return true;
    }


    public Object remove() throws Exception {
        if(size <= 0){
            throw new Exception("队列没有元素了");
        }
        if(head > maxIndex){
            head = 0;
        }
        Object value = queue[head];
        queue[head] = null;
        size --;
        head ++;
        return value;
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
        size --;
        head ++;
        return value;
    }

    public Object element() throws Exception {
        if(size <= 0){
            throw new Exception("队列没有元素了");
        }
        if(head > maxIndex){
            head = 0;
        }
        Object value = queue[head];
        return value;
    }


    public Object peek() {
        if(size <= 0){
            return null;
        }
        if(head > maxIndex){
            head = 0;
        }
        Object value = queue[head];
        return value;
    }

    public static void main(String[] arg0) throws Exception {
        MyValidateNotSafeQueue queue = new MyValidateNotSafeQueue(100000);
        ExecutorService service = Executors.newCachedThreadPool();
        for(int i = 0; i < 10; i++){
            QueueCallable able = new QueueCallable(queue);
            service.submit(able);
        }
        service.shutdown();
        Thread.sleep(10000);
        System.out.println(queue.size);
    }
}
