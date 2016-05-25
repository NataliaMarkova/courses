package ua.epamcourses.natalia_markova.classwork11;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by natalia_markova on 25.05.2016.
 */

class MyException extends Exception {

}

class MyException2 extends MyException {

}

class C implements Closeable {
    public void f() throws MyException {

    }

    @Override
    public void close() throws IOException {

    }
}

class D extends C {
    @Override
    public void f() throws MyException2 {

    }
}

public class Task03 {

    public static void main(String[] args) throws IOException {
        try (C c = new D()) {
            c.f();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }
}
