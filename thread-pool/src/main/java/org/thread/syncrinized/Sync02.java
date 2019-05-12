package org.thread.syncrinized;

/**
 * 测试代码块级别的synchronized
 * 同一个任务，多个线程，能保证线程安全
 * 多个任务，多个线程，不能保证线程安全
 */
public class Sync02 implements Runnable {
  static int i = 0;

  @Override
  public   void run() {
    add();
  }

  /***
   * 代码块级别
   */
  private void add() {
    synchronized (this) {
      for (int j = 0; j < 10000; j++) {
        i++;
      }
    }
  }

  /**
   * 测试多个任务，多个线程，一个static 变量，
   *结果 无法保证线程安全
   * @throws InterruptedException
   */
  public static  void testMoreObjMoreThread() throws InterruptedException {
    Sync02 sync01 = new Sync02();
    Sync02 sync02 = new Sync02();
    Thread thread = new Thread(sync01);
    Thread thread2 = new Thread(sync02);
    thread.start();
    thread2.start();
    thread.join();thread2.join();
    System.out.println(i);
  }

  /**
   * 测试一个任务，多个线程，一个static 变量，
   *结果 可以保证线程安全
   * @throws InterruptedException
   */
  public static  void testOneTaskMoreThread() throws InterruptedException {
    Sync02 sync01 = new Sync02();
    Thread thread = new Thread(sync01);
    Thread thread2 = new Thread(sync01);
    thread.start();
    thread2.start();
    thread.join();thread2.join();
    System.out.println(i);
  }

  public static void main(String[] args)throws  Exception {
    testOneTaskMoreThread();
    testMoreObjMoreThread();

  }


}