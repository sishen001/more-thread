package org.thread.pool;

import java.util.concurrent.*;

public class ThreadPoolTest {

    /**
     * 不带返回值的多线程
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void test() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
                System.out.println("this is more thread");
            }
        });
        executorService.shutdown();
    }

    /**
     * 測試開啓的任務數量
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void testThreadBum() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for(int i =0; i< 10000; i++){
            Future future =  executorService.submit(new Task_01_Runable());
            System.out.println("返回值="+future.get());
        }
        executorService.shutdown();
    }

    /**
     * 測試開啓的任務數量
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void testThreadBum_Call() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for(int i =0; i< 10000; i++){
            Future future =  executorService.submit(new Task_01_Callable());
           // System.out.println("返回值="+future.get());
        }
        executorService.shutdown();
    }

    /**
     * 带返回值的多线程
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void test1() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future future = executorService.submit(new Callable<Object>() {
            public Object  call() throws InterruptedException {
                System.out.println("test1 this is more thread");
                Thread.sleep(3000);
                return "ok";
            }
        });
        executorService.shutdown();

        System.out.println(future.get());
    }

    /**
     * 无限制的线程测试
     */
    public void testThreadCoreSizeAndMaxSizeNotLimit() throws ExecutionException, InterruptedException {
        LinkedBlockingQueue queue =  new LinkedBlockingQueue<Runnable>(3);
        ExecutorService executorService = new ThreadPoolExecutor(2, 5,
                0, TimeUnit.SECONDS,
                queue);
        Task_01_Runable task = new Task_01_Runable();
        for(int i = 0; i < 10000; i++){
            executorService.execute(task);
            ThreadPoolExecutor tpe = ((ThreadPoolExecutor) executorService);
            System.out.println("当前线程数量="+tpe.getQueue().size()+"----当前任务序号 i = "+i);
        }
        executorService.shutdown();
    }

    public static  void main(String[] arg0) throws ExecutionException, InterruptedException {
       // new ThreadPoolTest().test1();
        //new ThreadPoolTest().testThreadCoreSizeAndMaxSizeNotLimit();
        //new ThreadPoolTest().testThreadBum();

        new ThreadPoolTest().testThreadBum_Call();
    }
}
