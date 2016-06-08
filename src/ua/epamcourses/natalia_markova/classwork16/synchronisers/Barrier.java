package ua.epamcourses.natalia_markova.classwork16.synchronisers;

/**
 * Created by natalia_markova on 08.06.2016.
 */
public class Barrier {
    int value;

    public Barrier(int value) {
        this.value = value;
    }

    public synchronized void await() throws InterruptedException {
        value--;
        while (value > 0) {
            wait();
        }
        notifyAll();
    }

}
