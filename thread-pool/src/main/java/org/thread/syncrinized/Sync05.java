package org.thread.syncrinized;

/**
 * 测试synchronized 锁住class 对象
 * 可以保证线程安全
 */
public class Sync05 implements Runnable {
  static int i = 0;

  @Override
  public   void run() {
    add();
  }

  /***
   * 关键字
   */
  private   void add() {
    //要改成Sync05.class
    synchronized (Sync05.class){
    for (int j = 0; j < 10000; j++) {
      i++;
    }
  }
  }

  /**
   * 测试多任务，多线程是否线程安全
   * 结果，可以保证线程安全，因为锁住的是class对象
   * @throws InterruptedException
   */
  public static void testMoreTaskMoreThread() throws InterruptedException {
    Sync05 sync01 = new Sync05();
    Sync05 sync02 = new Sync05();
    Thread thread = new Thread(sync01);
    Thread thread2 = new Thread(sync02);
    thread.start();
    thread2.start();
    thread.join();
    thread2.join();
    System.out.println(i);
  }

  /**
   * 测试一个任务，多线程是否线程安全
   * 结果，可以保证线程安全，因为锁住的是class对象
   * @throws InterruptedException
   */
  public static void testOneTaskMoreThread() throws InterruptedException {
    Sync05 sync01 = new Sync05();
    Thread thread = new Thread(sync01);
    Thread thread2 = new Thread(sync01);
    thread.start();
    thread2.start();
    thread.join();
    thread2.join();
    System.out.println(i);
  }

  public static void main(String[] args)throws  Exception {
    testMoreTaskMoreThread();
    testOneTaskMoreThread();
  }

  }