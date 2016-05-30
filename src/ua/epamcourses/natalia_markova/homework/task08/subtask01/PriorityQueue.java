package ua.epamcourses.natalia_markova.homework.task08.subtask01;


import java.util.Arrays;

/**
 * Created by natalia_markova on 24.05.2016.
 */
public class PriorityQueue<E> {
    private int size;
    private E[] data;

    public PriorityQueue() {
        this(10);
    }

    public PriorityQueue(int initialCapacity) {
        data = (E[]) new Object[initialCapacity];
    }

    public boolean add(E element) {
        if (element == null) {
            throw new NullPointerException();
        }
        if (data[0] == null) {
            data[0] = element;
            size++;
            return true;
        }
        return add(0, element);
    }

    private boolean add(int index, E element) {
        if (index >= data.length) {
            resize();
        }
        if (data[index] == null) {
            data[index] = element;
            size++;
            return true;
        }
        Comparable<? super E> parent = (Comparable<? super E>)data[index];
        int res = parent.compareTo(element);
        if (res == 0) {
            return false;
        }
        if (res > 0) {
            // left child
            index = (index<<1) + 1;
        } else {
            // right child
            index = (index<<1) + 2;
        }
        return add(index, element);
    }

    public E peek() {
        // returns root
        return (size == 0) ? null : data[0];
    }

    public E poll() {
        // returns maximum
        if (size == 0) {
            return null;
        }
        return poll(0);
    }

    public E poll(int index) {
        int nextIndex = (index<<1) + 2;
        if (nextIndex < size && data[nextIndex] != null) {
            return poll(nextIndex);
        } else {
            return data[index];
        }
    }

    public boolean remove(E element) {
        if (element == null) {
            throw new NullPointerException();
        }
        int index = getIndex(element);
        if (index == -1) {
            return false;
        }
        remove(index);
        return true;
    }

    private int getIndex(E element) {
        if (size == 0) {
            return -1;
        }
        return getIndex(0, element);
    }

    private int getIndex(int index, E element) {
        if (index >= data.length) {
            return -1;
        }
        Comparable<? super E> parent = (Comparable<? super E>)data[index];
        int res = parent.compareTo(element);
        if (res == 0) {
            return index;
        } else if (res < 0) {
            return getIndex((index<<1) + 2, element);
        } else {
            return getIndex((index<<1) + 1, element);
        }
    }

    private void remove(int index) {
        int indexLeftChild = (index<<1) + 1;
        int indexRightChild = (index<<1) + 2;
        if ((indexLeftChild >= data.length || data[indexLeftChild] == null)
                && (indexRightChild >= data.length || data[indexRightChild] == null)) {
            // both children are null
            data[index] = null;
            size--;
            return;
        }
        if (indexLeftChild < data.length && data[indexLeftChild] != null
                && indexRightChild < data.length && data[indexRightChild] != null) {
                // has both children
            int i = indexRightChild;
            int leftI = (i<<1) + 1;
            while (leftI < data.length && data[leftI] != null) {
                i = leftI;
                leftI = (i<<1) + 1;
            }
            // change value
            data[index] = data[i];
            remove(i);

        } else if (indexLeftChild < data.length && data[indexLeftChild] != null) {
            // has only left child
            data[index] = data[indexLeftChild];
            data[indexLeftChild] = null;
            updateIndexes(index, indexLeftChild);
        } else {
            data[index] = data[indexRightChild];
            data[indexRightChild] = null;
            updateIndexes(index, indexRightChild);
        }
        size--;
    }

    private void updateIndexes(int newParentIndex, int oldParentIndex) {
        int indexLeftChild = (oldParentIndex<<1) + 1;
        int indexRightChild = (oldParentIndex<<1) + 2;
        int newIndexLeftChild = (newParentIndex<<1) + 1;
        int newIndexRightChild = (newParentIndex<<1) + 2;

        boolean updateLeft = false;
        boolean updateRight = false;
        if (indexLeftChild < data.length && data[indexLeftChild] != null) {
            data[newIndexLeftChild] = data[indexLeftChild];
            data[indexLeftChild] = null;
            updateLeft = true;
        }
        if (indexRightChild < data.length && data[indexRightChild] != null) {
            data[newIndexRightChild] = data[indexRightChild];
            data[indexRightChild] = null;
            updateRight = true;
        }
        if (updateLeft) {
            updateIndexes(newIndexLeftChild, indexLeftChild);
        }
        if (updateRight) {
            updateIndexes(newIndexRightChild, indexRightChild);
        }
    }

    public void clear() {
        data = (E[]) new Object[data.length];
        size = 0;
    }

    public int size() {
        return size;
    }

    private void resize() {
        data = Arrays.copyOf(data,  (data.length<<1) + 1);
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }
        StringBuilder str = new StringBuilder("[");
        toString(str, 0);
        str.delete(str.length() - 2, str.length());
        str.append("]");
        return str.toString();
    }

    private void toString(StringBuilder str, int index) {
        if (index >= data.length || data[index] == null) {
            return;
        }
        toString(str, (index<<1) + 1);
        str.append(data[index]).append(", ");
        toString(str, (index <<1) + 2);
    }

    public static void main(String[] args) {
        PriorityQueue<String> queue = new PriorityQueue<>();

        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();

        queue.add("P");
        queue.add("H");
        queue.add("N");
        queue.add("T");
        queue.add("Q");
        queue.add("A");
        queue.add("M");
        queue.add("G");
        queue.add("Z");
        queue.add("B");
        queue.add("W");
        queue.add("F");
        queue.add("K");
        queue.add("O");
        queue.add("I");
        queue.add("D");
        queue.add("S");
        queue.add("L");
        queue.add("X");
        queue.add("F");
        queue.add("Y");
        queue.add("U");
        queue.add("C");
        queue.add("E");
        queue.add("J");
        queue.add("V");
        queue.add("R");

        for (char ch: alphabet) {
            String letter = String.valueOf(ch);
//            System.out.println(queue);
            System.out.println(letter);
            queue.remove(letter);
            System.out.println(queue);
            System.out.println();
        }

        System.out.println(queue.size());

    }

}
