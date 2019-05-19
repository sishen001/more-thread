package org.java.collection.javacollection.test;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class Test  implements  Runnable{
    public static int i = 0;
    final ReentrantLock lock = new ReentrantLock();
    public void add(){

        lock.lock();
        try{
            for(int m = 0; m < 10000; m++){
                i++;
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
            System.out.println(i);
        }
    }
    public static void main(String[] arg0) throws InterruptedException {
        Test test = new Test();
        Thread thread = new Thread(test);
        Thread thread2 = new Thread(test);
        thread.start();
        thread2.start();
    }

    @Override
    public void run() {
        add();
    }
}
