package ua.epamcourses.natalia_markova.homework.problem11;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by natalia_markova on 07.07.2016.
 */

class CyclicBarrier {
    private int numberOfThreads;
    private Runnable runnable;
    private volatile int count;

    public CyclicBarrier(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    public CyclicBarrier(int numberOfThreads, Runnable runnable) {
        this.numberOfThreads = numberOfThreads;
        this.runnable = runnable;
    }

    public synchronized int await() throws InterruptedException {
        int result = ++count;
        if (count < numberOfThreads) {
            wait();
        } else {
            if (runnable != null) {
                Thread thread = new Thread(runnable);
                thread.start();
                thread.join();
            }
            notifyAll();
            count = 0;
        }
        return result;
    }

}

class MyThread extends Thread {
    private CyclicBarrier cyclicBarrier;

    public MyThread(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        System.out.println("Thread " + Thread.currentThread().getName() + " is running");
        try {
            Thread.sleep(100);
            int number = cyclicBarrier.await();
            System.out.println("Thread " + Thread.currentThread().getName() + " stopped waiting (count == " + number + ")");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class CyclicBarrierExample {

    public static void main(String[] args) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Runnable");
            }
        };

        CyclicBarrier cyclicBarrier = new CyclicBarrier(5, runnable);
        List<MyThread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            threads.add(new MyThread(cyclicBarrier));
        }
        for (MyThread thread: threads) {
            thread.start();
        }
    }

}
