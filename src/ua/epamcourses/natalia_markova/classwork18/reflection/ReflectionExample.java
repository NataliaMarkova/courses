package ua.epamcourses.natalia_markova.classwork18.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by natalia_markova on 13.06.2016.
 */

class A {
    int a;


    public A(int a) {
        this.a = a;
    }

    public int f(int b, int c) {
        return a + b + c;
    }
}

public class ReflectionExample {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class cl = Class.forName("ua.epamcourses.natalia_markova.classwork18.reflection.A");
        Class[] par = {int.class};
        Constructor constructor = cl.getConstructor(par);
        Object[] paramsConst = {new Integer(30)};
        A a = (A) constructor.newInstance(paramsConst);
//        A a = (A) cl.newInstance();
        Class[] params = {int.class, int.class};
        Method method = cl.getDeclaredMethod("f", params);
        Object[] paramsObj = {new Integer(10), new Integer(20)};
        int res = (int) method.invoke(a, paramsObj);
        System.out.println(res);
        int mod = method.getModifiers();
        System.out.println(Modifier.toString(mod));
    }

}
