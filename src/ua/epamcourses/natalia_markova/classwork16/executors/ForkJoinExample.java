package ua.epamcourses.natalia_markova.classwork16.executors;

/**
 * Created by natalia_markova on 08.06.2016.
 */

import javafx.concurrent.Task;
import oracle.jrockit.jfr.jdkevents.ThrowableTracer;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import static java.lang.Math.abs;

class ForkSolution extends RecursiveTask<Double> {

    @Override
    protected Double compute() {
        return null;
    }
}

class Solution implements Runnable {
    double x1;
    double x2;
    double eps;
    double result;

    public Solution(double x1, double x2, double eps) {
        this.x1 = x1;
        this.x2 = x2;
        this.eps = eps;
    }


    @Override
    public void run() {
        if (abs(x2 - x1) > eps) {
            double x3 = x1 + (x2 - x1)/2;
            Solution runnable = null;
            if (f(x3)*f(x1) == 0) {
                result = x3;
                return;
            } else if (f(x3)*f(x1) < 0) {
                runnable = new Solution(x1, x3, eps);
            } else {
                runnable = new Solution(x3, x2, eps);
            }
            Thread thread = new Thread(runnable);
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            result = runnable.result;

        } else {
            result = x1;
        }
    }

    public double f(double x) {
        return x*x + 2*x - 3;
    }
}

public class ForkJoinExample {

    public static void main(String[] args) throws InterruptedException {
        Solution s = new Solution(-100, 0, 0.000000000001);
        Thread thread = new Thread(s);
        thread.start();
        thread.join();
        System.out.println(s.result);

//        ForkSolution task = new ForkSolution();
////        ForkJoinPool pool = new ForkJoinPool().invoke(task);
//        System.out.println(task.join());
    }

}
