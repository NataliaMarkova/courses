package ua.epamcourses.natalia_markova.classwork16.executors;

import java.util.concurrent.*;

/**
 * Created by natalia_markova on 08.06.2016.
 */
class MyTask implements Callable<Integer> {
    int a;
    int b;

    public MyTask(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public Integer call() throws Exception {
        return a + b;
    }
}


public class CallableExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyTask myTask = new MyTask(10, 30);
        ExecutorService executor = Executors.newFixedThreadPool(5);
        Future<Integer> future = executor.submit(myTask);
        System.out.println(future.get());
        executor.shutdown();
    }
}
