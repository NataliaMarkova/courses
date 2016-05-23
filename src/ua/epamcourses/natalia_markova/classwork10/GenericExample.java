package ua.epamcourses.natalia_markova.classwork10;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by natalia_markova on 23.05.2016.
 */

class Set<T extends Comparable<? super T>> {
    Container<T> container;

    public Set(Container<T> container) {
        this.container = container;
    }

    public Set<T> union(Set<? extends T> otherSet) {
        Set<T> newSet = new Set<>(this.container.clone());
        for (int i = 0; i < otherSet.container.size(); i++) {
            T element = otherSet.container.get(i);
            if (!newSet.container.contains(element)) {
                newSet.container.add(element);
            }
        }
        return newSet;
    }
}

interface Container<T extends Comparable<? super T>> extends Cloneable {
    Container<T> clone();
    int size();
    T get(int index);
    boolean contains(T element);
    void add(T element);
}

class MyArray<T extends Comparable<? super T>> implements Container<T> {
    List<T> list = new ArrayList<T>();

    @Override
    public Container<T> clone() {
        MyArray<T> newArray = new MyArray<>();
        newArray.list.addAll(list);
        return newArray;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public T get(int index) {
        return list.get(index);
    }

    @Override
    public boolean contains(T element) {
        for (T el  : list) {
            if (el.compareTo(element) == 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void add(T element) {
        list.add(element);
    }
}

class Table implements  Comparable<Table> {
    int height;

    public Table(int height) {
        this.height = height;
    }

    @Override
    public int compareTo(Table o) {
        if (o == null) {
            return 1;
        }
        return height - o.getHeight();
    }

    public int getHeight() {
        return height;
    }
}

class OtherTable extends Table {

    public OtherTable(int hight) {
        super(hight);
    }
}

class Chair implements Comparable<Chair> {
    int width;

    public Chair(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }


    @Override
    public int compareTo(Chair o) {
        if (o == null) {
            return 1;
        }

        return width - o.getWidth();
    }
}

public class GenericExample {

    public static void main(String[] args) {

        Table table1 = new Table(10);
        Table table2 = new OtherTable(20);
        Chair chair1 = new Chair(5);
        Chair chair2 = new Chair(15);

        Container<Table> cont1 = new MyArray<>();
        cont1.add(table1);

        Container<Table> cont3 = new MyArray<>();
        cont3.add(table2);

        Container<Chair> cont2 = new MyArray<>();
        cont2.add(chair1);
        cont2.add(chair2);

        Set<Table> set1 = new Set(cont1);
        Set<Chair> set2 = new Set(cont2);
        Set<OtherTable> set3 = new Set(cont3);

        //set1.union(set2); нельзя к столам добавить стулья
        set1.union(set3);

    }
}
