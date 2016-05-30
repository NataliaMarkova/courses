package ua.epamcourses.natalia_markova.classwork12;

/**
 * Created by natalia_markova on 27.05.2016.
 */

class Sum extends Thread {

    int first;
    int last;
    int result;

    public Sum(int first, int last) {
        this.first = first;
        this.last = last;
    }

    @Override
    public void run() {
        for (int i = first; i<= last; i++) {
            result+=i;
        }
    }
}

public class Task01 {

    public static void main(String[] args) throws InterruptedException {
        Sum sum1 = new Sum(1, 5000);
        Sum sum2 = new Sum(5001, 10000);
        sum1.start();
        sum2.start();

        System.out.println(sum1.result + sum2.result);

        sum1.join();
        sum2.join();

//        while (sum1.isAlive() || sum2.isAlive()) {
//            Thread.sleep(10);
//        }

        System.out.println(sum1.result + sum2.result);

    }

}
