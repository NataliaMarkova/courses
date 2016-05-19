package ua.epamcourses.natalia_markova.classwork02;

/**
 * Created by naatalia_markova on 25.04.2016.
 */
public class Task01 {


    static class A {
        static int a = B.fB();
        public static int fA() {
            return a + 1;
        }
    }

    static class B {
        static int b = A.fA();
        public static int fB() {
            return b + 1;
        }
    }

    public static void main(String[] args) {
        System.out.println(A.a); // 2
        System.out.println(B.b); // 1
    }


}
