package org.thread.lock;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.concurrent.locks.ReentrantLock;

public class ReetrantLock_01 {

    ReentrantLock lock = new ReentrantLock();
    public static int m =0;


    public void add() throws InterruptedException {

        if(!lock.tryLock()){
            System.out.println("没有获取到锁");
            lock.lockInterruptibly();

        }
        try{
            lock.lock();
            for(int i = 0; i < 10000; i++){
                m++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    /**
     * 测试一个对象多个线程线程是否安全
     * 结果 安全
     * @throws InterruptedException
     */
    public  void testLock() throws InterruptedException {
        ReetrantLock_01 reetrantLock_01 = new ReetrantLock_01();
        new Thread((()->{
            try {
                reetrantLock_01.add();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        })).start();
        new Thread((()->{
            try {
                reetrantLock_01.add();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        })).start();
        Thread.sleep(1000);//主要目的是让两个线程把事情干完
        System.out.println(m);
    }

    /**
     * 测试多个对象多个线程线程是否安全
     * 结果 不安全，因为多个对象会创建多个锁
     * @throws InterruptedException
     */
    public  void testLockMoreTask() throws InterruptedException {
        ReetrantLock_01 reetrantLock_01 = new ReetrantLock_01();

        ReetrantLock_01 reetrantLock_02 = new ReetrantLock_01();
        new Thread((()->{
            try {
                reetrantLock_01.add();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        })).start();
        new Thread((()->{
            try {
                reetrantLock_02.add();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        })).start();
        Thread.sleep(1000);//主要目的是让两个线程把事情干完
        System.out.println(m);
    }

    public static void main(String[] arg0) throws InterruptedException {
        ReetrantLock_01 reetrantLock_01 = new ReetrantLock_01();
        reetrantLock_01.testLock();

        //reetrantLock_01.testLockMoreTask();
    }
}
