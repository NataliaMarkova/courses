package ua.epamcourses.natalia_markova.classwork13.task02;

/**
 * Created by natalia_markova on 01.06.2016.
 */

class A {

    public synchronized void method1() {

    }

    public void method2() {
        synchronized (this) {

        }
    }

    public static synchronized void method3() {

    }

    public static void method4() {
        Class classA = A.class;
        synchronized (classA) {

        }
    }

}

public class Task02 {
}
