package ua.epamcourses.natalia_markova.classwork04;

/**
 * Created by natalia_markova on 29.04.2016.
 */

interface A {
    void f();
}

class B {
    public void g() {
        System.out.println("B");
    }
}

class NoName implements A {
    B b;

    public NoName(B b) {
        this.b = b;
    }

    @Override
    public void f() {
        b.g();
    }
}

public class Task01 {

    public static void main(String[] args) {
        int i = 10; // ������������ final, ������ ����� � ����� ������������ ������ �����-�������
        A a = () -> {System.out.println(i);};
        B b = new B();
        A a1 = b::g;

        // ������������� ������
        A a2 = new NoName(b);
    }

}
