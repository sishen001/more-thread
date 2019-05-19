package org.java.collection.javacollection.queue;

import java.util.Queue;

/**
 * 自定义队列
 */
public class MyQueue {

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


    /**
     * 默认队列16个
     */
    public MyQueue(){
        queue = new Object[defaultQueueSize];
    }

    /**
     * 指定队列大小
     * @param initialCapacity
     * @throws Exception
     */
    public MyQueue(int initialCapacity) throws Exception {
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
        queue[tail] = o;
        size ++;
        tail++;
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
        MyQueue queue = new MyQueue();
        for(int i =0; i< 16; i++){
            queue.offer(i);
        }

       for(int i = 0; i < 10 ; i++){
           System.out.println("queue poll="+queue.poll() +"---当前队列长度="+queue.size());
       }

        for(int i =0; i< 10; i++){
            queue.add(i);
        }

        for(int i = 0; i < 16 ; i++){
            System.out.println("queue poll="+queue.poll() +"---当前队列长度="+queue.size());
        }
    }
}
