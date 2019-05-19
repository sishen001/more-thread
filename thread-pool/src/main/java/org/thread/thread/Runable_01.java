package org.thread.thread;

/**
 * wait notify 辅助类
 */
public class Runable_01 implements Runnable {

    private Object lock;

    public Runable_01(Object lock) {
        super();
        this.lock = lock;
    }

    @Override
    public void run() {
        add();
    }

    public void add() {
        Service service = new Service();
        service.testMethod(lock);
    }
}
