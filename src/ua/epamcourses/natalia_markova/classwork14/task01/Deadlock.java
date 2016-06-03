package ua.epamcourses.natalia_markova.classwork14.task01;

/**
 * Created by natalia_markova on 03.06.2016.
 */

class MyThread extends Thread {
    Object o1;
    Object o2;

    public MyThread(Object o1, Object o2) {
        this.o1 = o1;
        this.o2 = o2;
    }

    public void run () {
        synchronized (o1) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (o2) {

            }
        }
    }
}

public class Deadlock {
    public static void main(String[] args) {
        Object o1 = new Object();
        Object o2 = new Object();
        MyThread thread1 = new MyThread(o1, o2);
        MyThread thread2 = new MyThread(o2, o1);
        thread1.start();
        thread2.start();
    }
}
