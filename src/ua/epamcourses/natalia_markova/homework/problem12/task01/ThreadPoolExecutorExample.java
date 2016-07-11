package ua.epamcourses.natalia_markova.homework.problem12.task01;

import java.util.*;
import java.util.concurrent.RejectedExecutionException;

/**
 * Created by natalia_markova on 07.07.2016.
 */

class ThreadPoolExecutor {
    private final static int DEFAULT_QTY = 5;
    private ThreadExecutor[] threads;
    private Queue<Runnable> threadsToExecute = new LinkedList<>();
    private boolean shutdown;

    public ThreadPoolExecutor() {
        this(DEFAULT_QTY);
    }

    public ThreadPoolExecutor(int qty) {
        if (qty <= 0) {
            throw new IllegalArgumentException();
        }
        threads = new ThreadExecutor[qty];
        for (Thread thread: threads) {
            thread = new ThreadExecutor();
            thread.start();
        }
    }

    private class ThreadExecutor extends Thread {
        @Override
        public void run() {
            while (true) {
                Runnable runnable = null;
                synchronized (threadsToExecute) {
                    while (threadsToExecute.size() == 0) {
                        if (shutdown) {
                            return;
                        }
                        try {
                            threadsToExecute.wait();
                        } catch (InterruptedException e) {
                            return;
                        }
                    }
                    runnable = threadsToExecute.poll();
                }
                runnable.run();
            }
        }
    }

    public void execute(Runnable runnable) {
        if (shutdown) {
            throw new RejectedExecutionException();
        }
        synchronized (threadsToExecute) {
            threadsToExecute.add(runnable);
            threadsToExecute.notify();
        }
    }

    public synchronized void shutdown() {
        if (shutdown) {
            return;
        }
        shutdown = true;
        for (Thread thread: threads) {
            if (thread != null) {
                thread.interrupt();
            }
        }
    }
}

class TestThread extends Thread {

    private String name;

    public TestThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " is executing " + name);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class ThreadPoolExecutorExample {

    public static void main(String[] args) {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(2);
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            threads.add(new TestThread("Task" + i));
        }
        for (Thread thread: threads) {
            executor.execute(thread);
        }
        executor.shutdown();
    }

}
