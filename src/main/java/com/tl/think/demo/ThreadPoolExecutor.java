package com.tl.think.demo;

import java.util.HashSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: xue.l
 * @Date: 2018/9/20 11:21
 * @Description:
 * @Version: 1.0.0
 */
public class ThreadPoolExecutor extends AbstractExecutorService {

    /**
     * 核心线程数
     */
    private volatile int corePoolSize;

    /**
     * 最大线程数
     */
    private volatile int maximunPoolSize;

    /**
     * 线程存活时间
     */
    private volatile long keepAliveTime;

    /**
     * 线程池
     */
    private final HashSet<Worker> workers = new HashSet<>();

    /**
     * 核心线程数控制
     */
    private final AtomicInteger ctl = new AtomicInteger(0);

    /**
     * 运行状态
     */
    private static final int RUNNING = -1;

    /**
     * 关闭状态
     */
    private static final int SHUTDOWN = 2;

    /**
     * 线程池状态
     */
    private volatile int state;

    private final RejectedExecutionHandler rejectedExecutionHandler = new AbortPolicy();

    /**
     * 线程池队列
     */
    private final BlockingQueue<Runnable> workerQueue;

    private final ReentrantLock mainLock = new ReentrantLock();

    public ThreadPoolExecutor(int corePoolSize, int maximunPoolSize, long keepAliveTime, BlockingQueue<Runnable> workerQueue) {
        this.corePoolSize = corePoolSize;
        this.maximunPoolSize = maximunPoolSize;
        this.keepAliveTime = keepAliveTime;
        this.workerQueue = workerQueue;
        this.state = RUNNING;
    }

    @Override
    public void execute(Runnable command) {

        if (null == command) {
            throw new NullPointerException();
        }
        int c = ctl.get();
        if (c < corePoolSize) {
            addWorker(command, true);

        } else if (isRunning(state) && workerQueue.offer(command)) {
            if (!isRunning(state) && workerQueue.remove(command)) {
                reject(command);
            }
        } else if (!addWorker(command, false)) {
            reject(command);
        }
    }

    private boolean isRunning(int state) {
        return state == RUNNING;
    }


    private void reject(Runnable command) {
        rejectedExecutionHandler.rejectExecution(command, this);
    }

    private boolean addWorker(Runnable command, boolean core) {
        if (!isRunning(state)) {
            return false;
        }
        int num = core ? corePoolSize : maximunPoolSize;
        if (ctl.get() > num) {
            return false;
        }
        Worker w = new Worker(command);
        boolean workerStarted = false;
        boolean workerAdded = false;
        try {
            final Thread t = w.thread;
            if (t != null) {
                final ReentrantLock lock = this.mainLock;
                try {
                    lock.lock();
                    if (isRunning(state)) {
                        workers.add(w);
                        workerAdded = true;
                    }
                } finally {
                    lock.unlock();
                }
                if (workerAdded) {
                    t.start();
                    workerStarted = true;
                    ctl.incrementAndGet();
                }
            }
        } finally {
            if (!workerAdded) {
                addWorkerFailed(w);
            }
        }
        return workerStarted;
    }

    private void addWorkerFailed(Worker w) {
        final ReentrantLock lock = this.mainLock;
        try {
            lock.lock();
            workers.remove(w);
        } finally {
            lock.unlock();
        }
    }

    final void runWorker(Worker w) {
        Runnable task = w.firstTask;
        w.firstTask = null;
        try {
            while (null != task || (task = getTask()) != null) {
                try {
                    w.lock();
                    task.run();
                } finally {
                    task = null;
                    w.unlock();
                }
            }
        } finally {
            processWorkerExit(w);
        }
    }

    private void processWorkerExit(Worker w) {
        final ReentrantLock lock = this.mainLock;
        try {
            lock.lock();
            int c = ctl.decrementAndGet();
            if (isShutdown() || c >= corePoolSize) {
                workers.remove(w);
            }
        } finally {
            lock.unlock();
        }
    }

    private Runnable getTask() {
        try {

            for ( ;; ) {
                int c = ctl.get();
                if (!isRunning(state) || workers.isEmpty() || c > maximunPoolSize) {
                    return null;
                }
                return (c > corePoolSize) ? workerQueue.poll(keepAliveTime, TimeUnit.SECONDS) : workerQueue.take();
            }
        } catch (InterruptedException e) {
            return null;
        }
    }

    @Override
    public void shutdwon() {
        final ReentrantLock lock = this.mainLock;
        try {
            lock.lock();
            interruptIdleWorkers();
            state = SHUTDOWN;
        } finally {
            lock.unlock();
        }
    }

    private void interruptIdleWorkers() {
        final ReentrantLock lock = this.mainLock;
        try {
            lock.lock();
            for (Worker w : workers) {
                Thread t = w.thread;
                if (!t.isInterrupted() && w.tryLock()) {
                    try {
                        t.interrupt();
                    } finally {
                        w.unlock();
                    }
                }
            }
        } finally {
            lock.unlock();
        }
    }


    @Override
    public boolean isShutdown() {
        return state == SHUTDOWN;
    }

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public int getPoolSize() {
        return workers.size();
    }

    public int getMaximunPoolSize() {
        return maximunPoolSize;
    }

    private final class Worker extends AbstractQueuedSynchronizer implements Runnable {

        final Thread thread;

        Runnable firstTask;

        public Worker(Runnable firstTask) {
            this.firstTask = firstTask;
            thread = new Thread(this);
        }

        @Override
        public void run() {
            runWorker(this);
        }

        void lock() {
            acquire(1);
        }

        boolean tryLock() {
            return tryAcquire(1);
        }

        void unlock() {
            release(1);
        }

        @Override
        protected boolean tryRelease(int arg) {
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        @Override
        protected boolean tryAcquire(int arg) {
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }
    }

    public static class AbortPolicy implements RejectedExecutionHandler {

        @Override
        public void rejectExecution(Runnable command, ThreadPoolExecutor executor) {
            throw new RuntimeException("请求过快");
        }
    }
}
