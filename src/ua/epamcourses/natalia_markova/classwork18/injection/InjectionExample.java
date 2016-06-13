package ua.epamcourses.natalia_markova.classwork18.injection;

import java.io.*;
import java.lang.reflect.Field;

/**
 * Created by natalia_markova on 13.06.2016.
 */

interface A {
    void f();
}

class C implements  A {

    @Override
    public void f() {
        System.out.println("C");
    }
}

class D implements A {

    @Override
    public void f() {
        System.out.println("D");
    }
}

class MyClass {
    private A a;
    public  void method() {
        a.f();
    }
}

public class InjectionExample {

    public static void main(String[] args) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchFieldException {
        File file = new File("config.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        String[] elements = line.split(";");
        String className = "ua.epamcourses.natalia_markova.classwork18.injection." + elements[2];
        Class injClass = Class.forName(className);
        A a = (A) injClass.newInstance();

        Class myClass = Class.forName("ua.epamcourses.natalia_markova.classwork18.injection." + elements[0]);
        MyClass obj = (MyClass) myClass.newInstance();
        Field field = myClass.getDeclaredField(elements[1]);
        field.setAccessible(true);
        field.set(obj, a);

        obj.method();
    }

}
