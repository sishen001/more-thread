package com.study.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class RunableTest implements  Runnable {

    //private Map<String,Object> tempMap = new HashMap<>();
    private ConcurrentHashMap tempMap = new ConcurrentHashMap();
    private Integer value = null;

    @Override
    public void run() {
        try{
            System.out.println("this="+Thread.currentThread().getName()+" -"+this);
            value = new Random().nextInt(10);
            tempMap.put("value",value);
            System.out.println("当前线程放入的值"+Thread.currentThread().getName()+"---"+tempMap);
            Thread.sleep(10);
            System.out.println("runnable....线程+"+Thread.currentThread().getName()+"---"+tempMap);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main (String[] arg0) throws Exception{
        Runnable able = new RunableTest();
        Thread aa = new Thread(able);
        aa.start();
        aa.wait();
        aa.join();
        Thread.yield();
        new Thread(able).start();
        new Thread(able).start();
        new Thread(able).start();
    }
}
