package ua.epamcourses.natalia_markova.classwork16.synchronisers;

/**
 * Created by natalia_markova on 08.06.2016.
 */
public class SemaphoreExample {
    int value; // qty of threads that can acquire Semaphore

    public SemaphoreExample(int value) {
        this.value = value;
    }

    public synchronized void acquire() throws InterruptedException {
        while (value == 0) {
            wait();
        }
        value--;
    }

    public synchronized void release(){
        value++;
        notifyAll();
    }
}
