package org.java.collection.javacollection.queue;

import java.util.LinkedList;
import java.util.Queue;

public class Queue_01 {

    private Queue queue;

    public Queue getQueue() {
        return queue;
    }

    public void setQueue(Queue queue) {
        this.queue = queue;
    }

    public Queue_01(){
        queue = new LinkedList();
        for(int i = 1; i <= 100; i++){
            queue.add(i);
        }
    }

    public static void main(String[] arg0){
        Queue_01 queue = new Queue_01();
        Queue q1 = queue.getQueue();
        System.out.println(q1.poll()+"===poll---出栈后的长度="+q1.size());
        q1.offer(200);
        System.out.println(q1.poll()+"===poll---出栈后的长度="+q1.size());
        System.out.println(q1.element()+"==element---出栈后的长度="+q1.size());
        System.out.println(q1.peek()+"==peek---出栈后的长度="+q1.size());
        System.out.println(q1.remove()+"==remove---出栈后的长度="+q1.size());
    }

}
