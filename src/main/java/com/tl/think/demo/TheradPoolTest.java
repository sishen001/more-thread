package com.tl.think.demo;

import java.util.concurrent.*;

/**
 * @Author: xue.l
 * @Date: 2018/9/20 13:04
 * @Description: 线程池测试类
 * @Version: 1.0.0
 */
public class TheradPoolTest {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        final ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 50, 10, new ArrayBlockingQueue<Runnable>(1));
      /*  new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                poolExecutor.execute(() -> { });
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();*/
        Future<Object> submit = poolExecutor.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return "hello world";
            }
        });
        System.out.println(submit.get());

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            System.out.println("当前线程池中的线程数：" + poolExecutor.getPoolSize());
            System.out.println("当前线程状态: " + (poolExecutor.isShutdown() ? "线程已经关闭" : "线程已经启动"));
            System.out.println("核心线程数: " + poolExecutor.getCorePoolSize());
            System.out.println("最大线程数: " + poolExecutor.getMaximunPoolSize());
        }, 0, 2, TimeUnit.SECONDS);

        Thread.sleep(1000);
        poolExecutor.shutdwon();
    }
}
