package ua.epamcourses.natalia_markova.homework.problem09.task03;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by natalia_markova on 14.06.2016.
 */

abstract class Function {
    abstract double f(double x);
}

class ConcreteFunction extends Function{
    @Override
    double f(double x) {
        return 1 / x;
    }
}

class ComputationThreadPool {
    private List<Computation> computations;
    private int index = 0;
    private ComputationThread[] threads;

    public ComputationThreadPool() {
        this(5);
    }

    public ComputationThreadPool(int qtyOfThreads) {
        if (qtyOfThreads < 1) {
            throw new IllegalArgumentException();
        }
        threads = new ComputationThread[qtyOfThreads];
    }

    public double compute(List<Computation> computations) throws InterruptedException {
        this.computations = computations;
        threads = new ComputationThread[threads.length];
        index = 0;
        for (int i = 0; i < threads.length; i++) {
            ComputationThread thread = new ComputationThread();
            threads[i] = thread;
            thread.start();
            thread.join();
        }

        double result = 0;
        for (ComputationThread thread : threads) {
            result += thread.getResult();
        }
        return result;
    }

    private class ComputationThread extends Thread {
        private double result;

        public double getResult() {
            return result;
        }

        @Override
        public void run() {
            Computation computation = null;
            result = 0;
            while (index < computations.size()) {
                synchronized (computations) {
                    computation = computations.get(index++);
                }
                result += computation.compute();
            }
        }
    }
}

class Computation {

    private double x1;
    private double x2;
    private double eps;
    private Function function;

    public Computation(double x1, double x2, Function function, double eps) {
        this.x1 = x1;
        this.x2 = x2;
        this.eps = eps;
        this.function = function;
    }

    public double compute() {
        int qtyOfOperation = (int) (Math.abs(x1 - x2) / eps) + (Math.abs(x1 - x2) % eps == 0 ? 0 : 1);

        double result = 0;
        double tempX1 = x1;
        for (int i = 0; i < qtyOfOperation; i ++) {
            double tempX2 = tempX1 + eps;
            if (i == qtyOfOperation - 1) {
                tempX2 = x2;
            }
            result += (function.f(tempX1) + function.f(tempX2)) * (Math.abs(tempX1 - tempX2)) / 2;
            tempX1 = tempX2;
        }
        return result;
    }
}

public class DefiniteIntegralComputation {

    private static final int OPERATIONS_LIMIT = 1000;

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        double x1 = 1;
        double x2 = 10;
        Function function = new ConcreteFunction();
        double eps = 0.00001;

        double result = 0;
        long minimumTime = Long.MAX_VALUE;
        int optimalQtyOfThreads = 0;

        for (int i = 1; i <= 20; i++) {
            long start = System.currentTimeMillis();
            result = compute(x1, x2, function, eps, i);
            long end = System.currentTimeMillis();
            long time = end - start;
            if (time < minimumTime) {
                minimumTime = time;
                optimalQtyOfThreads = i;
            }
            System.out.println("Threads: " + i + ", time == " + time);
        }
        System.out.println(result);
        System.out.println("OPTIMAL: " + optimalQtyOfThreads + ", time == " + minimumTime);
    }

    private static double compute(double x1, double x2, Function function, double eps, int qtyOfThreads) throws InterruptedException, ExecutionException {

        int qtyOfOperation = (int) (Math.abs(x1 - x2) / eps) + (Math.abs(x1 - x2) % eps == 0 ? 0 : 1);
        qtyOfOperation = (qtyOfOperation / OPERATIONS_LIMIT) + (qtyOfOperation % OPERATIONS_LIMIT == 0 ? 0 : 1);

        ComputationThreadPool pool = new ComputationThreadPool(qtyOfThreads);
        List<Computation> computations = new ArrayList<>();
        double tempX1 = x1;
        for (int i = 0; i < qtyOfOperation; i ++) {
            double tempX2 = tempX1 + OPERATIONS_LIMIT * eps;
            if (i == qtyOfOperation - 1) {
                tempX2 = x2;
            }
            Computation computation = new Computation(tempX1, tempX2, function, eps);
            computations.add(computation);
            tempX1 = tempX2;
        }

        return pool.compute(computations);
    }

}
