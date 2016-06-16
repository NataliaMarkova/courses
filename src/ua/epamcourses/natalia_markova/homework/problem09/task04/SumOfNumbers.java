package ua.epamcourses.natalia_markova.homework.problem09.task04;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by natalia_markova on 14.06.2016.
 */

class Sum extends Thread {

    private int a;
    private int b;
    private int result;

    public Sum(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public int getResult() {
        return result;
    }

    @Override
    public void run() {
        result = 0;
        for (int i = a; i <= b; i++) {
            result += i;
        }
    }
}

public class SumOfNumbers {

    public static void main(String[] args) throws InterruptedException {

        int n = 1000;
        int k = 7;
        int start = 1;
        System.out.println(getSum(start, n, k));

    }

    private  static int getSum(int start, int n, int k) throws InterruptedException {
        int qty = n / k + (n % k == 0 ? 0 : 1);
        int begin = start;
        List<Sum> threads = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            int end = begin + qty - 1;
            if (i == k - 1) {
                end = start + n - 1;
            }
            Sum thread = new Sum(begin, end);
            threads.add(thread);
            thread.start();
            thread.join();
            begin = end + 1;
        }

        int result = 0;
        for (Sum thread : threads) {
            result += thread.getResult();
        }
        return result;

    }

}
