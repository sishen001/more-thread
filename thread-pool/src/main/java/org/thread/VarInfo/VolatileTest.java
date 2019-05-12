package org.thread.VarInfo;

public class VolatileTest {

    private volatile int i = 0;

    public void addOne(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                i ++;
                System.out.println(i);
            }
        }).start();
    }

    public void addtwo(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                i ++;
                System.out.println(i);
            }
        }).start();
    }

    public static void main(String[] arg0){
        VolatileTest test = new VolatileTest();
        for(;;){
            test.addOne();
            test.addtwo();
        }

    }
}
