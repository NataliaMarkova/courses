package ua.epamcourses.natalia_markova.classwork04;

/**
 * Created by natalia_markova on 29.04.2016.
 */

interface C {
    final B b = new B();
    class B {
        int x = 4;
    }
}

public class Task02 {

    public static void main(String[] args) {
        C.b.x = 50;
    }

}
