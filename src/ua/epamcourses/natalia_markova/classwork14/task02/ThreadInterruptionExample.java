package ua.epamcourses.natalia_markova.classwork14.task02;

/**
 * Created by natalia-markova on 03.06.2016.
 */


class MyThread extends Thread {
    @Override
    public void run() {
        synchronized (this) {
            while (!isInterrupted()) {
                System.out.println("running");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    System.out.println("sleep interrupted");
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("interrupted");
        }
    }
}

public class ThreadInterruptionExample {

    public static void main(String[] args) throws InterruptedException {
        MyThread thread = new MyThread();
        System.out.println(thread.getState());
        synchronized (thread) {
            thread.start();
            Thread.sleep(100);
            System.out.println(thread.getState());
        }
        System.out.println(thread.getState());
        Thread.sleep(100);
        thread.interrupt();
        System.out.println(thread.getState());
        Thread.sleep(100);
        System.out.println(thread.getState());
    }



}
