package ua.epamcourses.natalia_markova.classwork11;

/**
 * Created by natalia_markova on 25.05.2016.
 */

class B {
    static B b;
    int field;

    public B(int field) throws Exception {
        this.field = field;
        b = this;
        if (field == 0) {
            throw new Exception();
        }
    }
}

public class Task02 {
    public static void main(String[] args) {
        B b = null;
        try {
            b = new B(0);
        } catch (Exception e) {
//            e.printStackTrace();
        }
        System.out.println(b);
        System.out.println(B.b);
    }
}
