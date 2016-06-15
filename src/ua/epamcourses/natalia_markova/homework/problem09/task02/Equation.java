package ua.epamcourses.natalia_markova.homework.problem09.task02;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * Created by natalia_markova on 14.06.2016.
 */

abstract class Function {
    abstract double f(double x);
}

class ConcreteFunction extends Function{

    @Override
    double f(double x) {
        return x*x - 1;
    }
}

class SolutionFinder extends RecursiveTask<Double> {

    private double x1;
    private double eps;
    private double x2;
    private Function function;

    public SolutionFinder(double x1, double x2, Function function, double eps) {
        this.x1 = x1;
        this.x2 = x2;
        this.function = function;
        this.eps = eps;
    }

    @Override
    protected Double compute() {
        if (function.f(x1)*function.f(x2) > 0) {
            return null;
        }
        if (Math.abs(x1 - x2) <= eps) {
            return x1;
        }
        double x3 = x1 + (x2 - x1)/2;
        SolutionFinder finder = null;
        if (function.f(x3) * function.f(x1) == 0) {
            return x3;
        } else if (function.f(x3) * function.f(x1) < 0) {
            finder = new SolutionFinder(x1, x3, function, eps);
        } else {
            finder = new SolutionFinder(x3, x2, function, eps);
        }
        finder.fork();
        return finder.join();
    }
}

public class Equation {
    public static void main(String[] args) {
        RecursiveTask<Double> finder = new SolutionFinder(-10, 0, new ConcreteFunction(), 0.0000001);
        ForkJoinPool pool = new ForkJoinPool().commonPool();
        System.out.println(pool.invoke(finder));
    }
}
