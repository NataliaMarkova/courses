package ua.epamcourses.natalia_markova.homework03.task06.subtask01_02;

/**
 * Created by natalia_markova on 12.05.2016.
 */
public class LinkedList<T> implements List<T> {

    private static class Node<T> {
        T value;
        Node<T> left;
        Node<T> right;

        public Node(T value) {
            this.value = value;
        }
    }

    private int size;
    private Node<T> header; // header.left == last element

    public LinkedList() {

    }

    @Override
    public boolean contains(T value) {
        if (size == 0) {
            return false;
        }
        Node<T> node = header;
        while (node != null) {
            if (node.value.equals(value)) {
                return true;
            }
            node = node.right;
        }
        return false;
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            header = new Node<>(value);
            header.left = header;
        } else {
            Node<T> lastNode = header.left;
            Node<T> newNode = new Node<>(value);
            header.left = newNode;
            newNode.left = lastNode;
            lastNode.right = newNode;
        }
        size++;
    }

    @Override
    public void set(int index, T value) {
        checkIndex(index);
        Node<T> node = getNodeByIndex(index);
        Node<T> newNode = new Node<>(value);

        if (node != header) {
            node.left.right = newNode;
        }
        newNode.left = node.left;
        newNode.right = node;
        node.left = newNode;

        if (node == header) {
            header = newNode;
        }
        size++;
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
        Node<T> node = getNodeByIndex(index);
        if (node == header) {
            header.right.left = header.left;
            header = header.right;
        } else if (node == header.left) {
            header.left = node.left;
            node.left.right = null;
        } else {
            node.left.right = node.right;
            node.right.left = node.left;
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
        header = null;
        size = 0;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }
        StringBuilder str = new StringBuilder("[");
        Node<T> node = header;
        str.append(node.value);
        while (node.right != null) {
            node = node.right;
            str.append(", ").append(node.value);
        }
        str.append("]");
        return str.toString();
    }

    @Override
    public String toStringReverse() {
        if (size == 0) {
            return "[]";
        }
        StringBuilder str = new StringBuilder("[");
        Node<T> node = header.left;
        str.append(node.value);
        while (node != header) {
            node = node.left;
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
        Node<T> node = null;
        if (index < size / 2) {
            node = header;
            for (int i = 1; i <= index; i++) {
                node = node.right;
            }
        } else {
            node = header.left;
            for (int i = 1; i <= size - index; i++) {
                node = node.left;
            }
        }
        return node;
    }

}
