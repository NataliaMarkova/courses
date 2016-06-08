package ua.epamcourses.natalia_markova.classwork16.synchronisers;

/**
 * Created by natalia_markova on 08.06.2016.
 */
public class CountDownLatch {
    int value;

    public CountDownLatch(int value) {
        this.value = value;
    }

    public synchronized void await() throws InterruptedException {
        while (value > 0) {
            wait();
        }
    }

    public synchronized void countdown() {
        value--;
        notify();
    }

}
