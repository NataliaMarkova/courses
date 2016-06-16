package ua.epamcourses.natalia_markova.homework.problem09.task4;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by natalia_markova on 14.06.2016.
 */

class Sum implements Callable<Integer> {

    private int a;
    private int b;

    public Sum(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public Integer call() throws Exception {
        int result = 0;
        for (int i = a; i <= b; i++) {
            result += i;
        }
        return result;
    }
}


public class SumOfNumbers {

    public static void main(String[] args) {

        int n = 1000;
        int k = 5;
        int start = 1;


    }

    private  static int getSum(int start, int n, int k) {

        ExecutorService executor = Executors.newFixedThreadPool(k);
        List<Future<Double>> futures = new ArrayList<>();

        Integer result = 0;
        return result;


    }

}
