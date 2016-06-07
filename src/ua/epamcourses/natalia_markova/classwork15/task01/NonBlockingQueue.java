package ua.epamcourses.natalia_markova.classwork15.task01;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by natalia_markova on 06.06.2016.
 */
public class NonBlockingQueue<E> {
    private AtomicReference<Node<E>> first;
    private AtomicReference<Node<E>> last;
    AtomicInteger size = new AtomicInteger(0);

    class Node<E> {
        E value;
        Node<E> next;
    }

    public void add(E element) {
        Node<E> node = new Node<>();
        node.value = element;

        while (true) {
            Node<E> head = first.get();
            Node<E> end = last.get();

            if (head == null) {
                if (first.compareAndSet(end, node)) {
                    last.set(node);
                    size.incrementAndGet();
                    break;
                }
            } else {
                node.next = head;
                if (first.compareAndSet(head, node)) {
                    size.incrementAndGet();
                    break;
                }
            }
        }
    }

    public E delete() {
        if (last.get() == null) {
            return null;
        }

        E element = null;

        while (true) {
            Node<E> head = first.get();
            Node<E> end = last.get();
            if (head == end) {
                if (first.compareAndSet(end, null)) {
                    last = null;
                    element = head.value;
                    size.decrementAndGet();
                    break;
                }
            } else {
                Node<E> prev = first.get();
                while (prev!= null && prev.next != end) {
                    prev = prev.next;
                }
                if (prev != null && last.compareAndSet(end, prev)) {
                    element = end.value;
                    size.decrementAndGet();
                    break;
                }
            }
        }

        return element;
    }

}
