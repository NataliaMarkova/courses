package ua.epamcourses.natalia_markova.homework.problem13;


import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by natalia_markova on 11.07.2016.
 */

class NonBlockingQueue<V> {

    private volatile int size;
    private AtomicReference<Node<V>> head;
    private AtomicReference<Node<V>> tail;

    public NonBlockingQueue() {
        head = new AtomicReference<>(new Node<V>(null, null));
        tail = head;
        size = 0;
    }

    static class Node<V> {
        private V value;
        private AtomicReference<Node<V>> next;

        public Node(V value, AtomicReference<Node<V>> next) {
            this.value = value;
            this.next = next;
        }
    }

    public void put(V value) {
        Node<V> newNode = new Node<>(value, null);
        while (true) {
            Node<V> tailNode = tail.get();
            Node<V> nextNode = tailNode.next.get();
            if (tailNode == tail.get()) {
                if (nextNode == null) {
                    if (tailNode.next.compareAndSet(null, newNode)) {
                        tail.compareAndSet(tailNode, nextNode);
                        size++;
                        break;
                    }
                } else {
                    // another thread entered new node to the tail
                    // try to change tail to nexNode
                    tail.compareAndSet(tailNode, nextNode);

                }
            }
        }
    }

    public V get() {
        V value = null;
        if (head.get() == null) {
            return null;
        }
        while (true) {
            Node<V> headNode = head.get();
            Node<V> tailNode = tail.get();
            Node<V> nexNode = headNode.next.get();
            if (head.compareAndSet(headNode, nexNode)) {
                tail.compareAndSet(headNode, nexNode);
                value = headNode.value;
                size--;
                break;
            }
        }
        return value;
    }


}

public class NonBlockingQueueExample {


}
