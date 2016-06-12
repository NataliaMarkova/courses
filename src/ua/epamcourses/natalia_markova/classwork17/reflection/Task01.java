package ua.epamcourses.natalia_markova.classwork17.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by natalia_markova on 10.06.2016.
 */

class A {
    int i;

    public void f() {
        System.out.println("inside f()");
    }

    public void f(int b, String str) {

    }

}

public class Task01 {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        A a = new A();
        Class cl = a.getClass();
        Class c2 = Class.forName("ua.epamcourses.natalia_markova.classwork17.reflection.A");
        A newA = (A) c2.newInstance();

        Field[] fields = cl.getDeclaredFields();
        Field f = fields[0];
        System.out.println(f.getName());
        System.out.println(f.getType());
        f.set(newA, new Integer(10));
        System.out.println(f.get(newA));

        Method[] methods = c2.getMethods();
        Method method = methods[0];
        method.invoke(newA);
                System.out.println(method.getName());
        System.out.println(Arrays.toString(method.getParameterTypes()));

    }
}
