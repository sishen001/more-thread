package org.thread.test;

public class Test_01 implements Runnable{

    @Override
    public void run() {
        int i =0;
        System.out.println(Thread.currentThread().getName());
        try {
            for(int m = 0; m < 1000; m++){
                if(m == 100){
                    Thread.sleep(10000);
                }
                i ++;
            }

            System.out.println(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] arg0){
        Test_01 test01 = new Test_01();
        for(int i=0; i < 10; i++){
            Thread thread_1 = new Thread(test01);
            thread_1.start();
        }


    }
}
