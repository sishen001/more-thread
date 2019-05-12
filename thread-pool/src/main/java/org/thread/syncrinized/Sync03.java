package org.thread.syncrinized;

/**
 * 测试synchronized 锁定静态方法
 * 结果，可以保证线程安全，因为锁住的是class对象
 */
public class Sync03 implements Runnable {
  static int i = 0;

  @Override
  public   void run() {
    add();
  }

  /***
   * 关键字
   */
  private static  synchronized void add() {
    for (int j = 0; j < 10000; j++) {
      i++;
    }
  }

  /**
   * 测试一个任务多个线程是否保证线程安全
   * 结果：可以保证线程安全
   * @throws InterruptedException
   */
  public static  void testOneTaskMoreThread() throws InterruptedException {
    Sync03 sync01 = new Sync03();
    Thread thread = new Thread(sync01);
    Thread thread2 = new Thread(sync01);
    thread.start();
    thread2.start();
    thread.join();thread2.join();
    System.out.println(i);
  }

  /**
   * 测试多个任务多个线程是否保证线程安全
   * 结果：可以保证线程安全
   * @throws InterruptedException
   */
  public static  void testMoreTaskMoreThread() throws InterruptedException {
    Sync03 sync01 = new Sync03();
    Sync03 sync02 = new Sync03();
    Thread thread = new Thread(sync01);
    Thread thread2 = new Thread(sync02);
    thread.start();
    thread2.start();
    thread.join();thread2.join();
    System.out.println(i);
  }

  public static void main(String[] args)throws  Exception {
    testOneTaskMoreThread();
    testMoreTaskMoreThread();


  }


}