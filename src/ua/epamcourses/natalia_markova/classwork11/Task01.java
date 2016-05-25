package ua.epamcourses.natalia_markova.classwork11;

/**
 * Created by natalia_markova on 25.05.2016.
 */

class FirstException extends Exception {

}

class SecondException extends Exception {
    public SecondException() {
        super();
    }

    public SecondException(Throwable cause) {
        super(cause);
    }
}

class A {
    public void test(int a) throws SecondException {
        if (a == 1) {
            try {
                throw new FirstException();
            } catch (FirstException e) {
                throw new SecondException(e);
            }
        }
    }
}

public class Task01 {

    public static void main(String[] args) {
        A a = new A();
        try {
            a.test(1);
        } catch (SecondException e) {
            e.printStackTrace();
        }
    }

}
