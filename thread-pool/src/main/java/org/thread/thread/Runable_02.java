package org.thread.thread;

/**
 * wait notify 辅助类
 */
public class Runable_02 implements Runnable {
    private Object lock;

    public Runable_02(Object lock) {
        super();
        this.lock = lock;
    }

    @Override
    public void run() {
        add();
    }

    public void add() {
        Service service = new Service();
        service.synNotifyMethod(lock);
    }
}
