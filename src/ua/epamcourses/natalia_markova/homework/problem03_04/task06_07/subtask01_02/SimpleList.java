package ua.epamcourses.natalia_markova.homework.problem03_04.task06_07.subtask01_02;

/**
 * Created by natalia_markova on 12.05.2016.
 */
public class SimpleList<T> implements List<T> {

    private static class Node<T> {
        T value;
        Node<T> next;

        public Node(T value) {
            this.value = value;
        }
    }

    private int size;
    private Node<T> first;

    public SimpleList() {
    }

    @Override
    public boolean contains(T value) {
        if (size == 0) {
            return false;
        }
        Node<T> node = first;
        while (node != null) {
            if (node.value.equals(value)) {
                return true;
            }
            node = node.next;
        }
        return false;
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            first = new Node<>(value);
        } else {
            Node<T> node = first;
            for (int i = 1; i < size; i++) {
                node = node.next;
            }
            Node<T> newNode = new Node<>(value);
            node.next = newNode;
        }
        size++;
    }

    @Override
    public void add(int index, T value) {
        checkIndex(index);
        Node<T> newNode = new Node<>(value);
        if (index == 0) {
            newNode.next = first;
            first = newNode;
        } else {
            Node<T> node = getNodeByIndex(index - 1);
            newNode.next = node.next;
            node.next = newNode;
        }
        size++;
    }

    @Override
    public void set(int index, T value) {
        checkIndex(index);
        Node<T> node = getNodeByIndex(index);
        node.value = value;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> node = getNodeByIndex(index);
        return node.value;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = null;
        if (index == 0) {
            node = first;
            first = node.next;
        } else {
            Node<T> prevNode = getNodeByIndex(index - 1);
            node = prevNode.next;
            prevNode.next = node.next;
        }
        size--;
        return node.value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        first = null;
        size = 0;
    }

    @Override
    public String toStringReverse() {
        return toString();
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }
        StringBuilder str = new StringBuilder("[");
        Node<T> node = first;
        str.append(node.value);
        while (node.next != null) {
            node = node.next;
            str.append(", ").append(node.value);
        }
        str.append("]");
        return str.toString();
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> node = first;
        for (int i = 1; i <= index; i++) {
            node = node.next;
        }
        return node;
    }
}
