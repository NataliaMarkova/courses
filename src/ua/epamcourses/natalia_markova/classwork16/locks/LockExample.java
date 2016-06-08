package ua.epamcourses.natalia_markova.classwork16.locks;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by natalia_markova on 08.06.2016.
 */

class Producer extends Thread {
    Queue<Integer> queue;
    Lock lock;
    Condition condition;

    public Producer(Queue<Integer> queue, Lock lock, Condition condition) {
        this.queue = queue;
        this.lock = lock;
        this.condition = condition;
    }

    @Override
    public void run() {
        if (lock.tryLock()) {
            int  i = 0;
            while (i < 10) {
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            condition.signal();
            lock.unlock();
        } else {
        }
    }
}

public class LockExample {

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        Queue<Integer> queue = new LinkedList<>();
        Producer producer = new Producer(queue, lock, condition);
    }
}
