package ua.epamcourses.natalia_markova.classwork13.task01;

import ua.epamcourses.natalia_markova.homework.task04.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by natalia_markova on 01.06.2016.
 */

class Producer implements Runnable{

    Queue queue;

    public Producer(Queue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            Integer element = getElement();
            synchronized (queue) {
                while (queue.size() >= 5) {
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                queue.add(element);
                System.out.println("Thread " + Thread.currentThread() + " put an object in Queue: " + queue.size());
                queue.notifyAll();
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private int getElement() {
        return (int) (Math.random() + 100);
    }
}

class Consumer implements Runnable {
    Queue queue;

    public Consumer(Queue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            Integer element = null;
            synchronized (queue) {
                while (queue.size() == 0) {
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                queue.remove();
                System.out.println("Thread " + Thread.currentThread() + " got an object from Queue: " + queue.size());
                queue.notifyAll();
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class Task01 {
    public static void main(String[] args) throws InterruptedException {
        Queue<Integer> queue = new PriorityQueue<>();
        List<Runnable> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(new Producer(queue));
        }
        for (int i = 0; i < 2; i++) {
            list.add(new Consumer(queue));
        }
        for (Runnable runnable: list) {
            Thread thread = new Thread(runnable);
            thread.start();
        }

        Thread.currentThread().sleep(1000);
        System.exit(0);


    }
}
