package ua.epamcourses.natalia_markova.homework.problem13;


import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by natalia_markova on 11.07.2016.
 */

class NonBlockingQueue<V> {

    private AtomicInteger size = new AtomicInteger(0);
    private AtomicReference<Node<V>> head;
    private AtomicReference<Node<V>> tail;

    public NonBlockingQueue() {
        head = new AtomicReference<>(null);
        tail = new AtomicReference<>(null);
    }

    static class Node<V> {
        private V value;
        private Node<V> next;

        public Node(V value, Node<V> next) {
            this.value = value;
            this.next = next;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    '}';
        }
    }

    public void put(V value) {
        while (true) {
            Node<V> tailNode = tail.get();
            Node<V> newNode;
            if (tailNode == null) {
                newNode = new Node<>(value, null);
                if (tail.compareAndSet(tailNode, newNode)) {
                    head.set(newNode);
                    size.incrementAndGet();
                    break;
                }
            } else {
                newNode = new Node<>(value, null);
                if (tail.compareAndSet(tailNode, newNode)) {
                    tailNode.next = newNode;
                    size.incrementAndGet();
                    break;
                }
            }

        }
    }

    public V poll() {
        V value = null;
        while (true) {
            Node<V> headNode = head.get();
            Node<V> tailNode = tail.get();
            if (headNode == null) {
                return null;
            }
            Node<V> nextNode = headNode.next;
            if (nextNode == null) {
                if (tail.compareAndSet(tailNode, null)) {
                    value = headNode.value;
                    head.set(null);
                    size.decrementAndGet();
                    break;
                }
            } else {
                if (head.compareAndSet(headNode, nextNode)) {
                    value = headNode.value;
                    size.decrementAndGet();
                    break;
                }
            }
        }
        return value;
    }

    public int size() {
        return size.get();
    }

    @Override
    public String toString() {
        Node<V> node = head.get();
        if (node == null) {
            return "[]";
        }
        StringBuilder str = new StringBuilder("[");
        while (node != null) {
            str.append(node.value).append(", ");
            node = node.next;
        }
        str.delete(str.length() - 2, str.length());
        str.append("]");
        return str.toString();
    }
}

class MyThreadInsert extends Thread {

    private NonBlockingQueue<Integer> queue;

    public MyThreadInsert(NonBlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            queue.put(i);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

class MyThreadDelete extends Thread {

    private NonBlockingQueue<Integer> queue;

    public MyThreadDelete(NonBlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(queue.poll());
            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class NonBlockingQueueExample {

    public static void main(String[] args) throws InterruptedException {
        NonBlockingQueue<Integer> queue = new NonBlockingQueue<>();

        MyThreadInsert threadInsert = new MyThreadInsert(queue);
        MyThreadInsert threadInsert2 = new MyThreadInsert(queue);
        MyThreadInsert threadInsert3 = new MyThreadInsert(queue);
        MyThreadDelete threadDelete = new MyThreadDelete(queue);
        MyThreadDelete threadDelete2 = new MyThreadDelete(queue);
        MyThreadDelete threadDelete3 = new MyThreadDelete(queue);

        threadInsert.start();
        threadInsert2.start();
        threadInsert3.start();
//        threadInsert.join();
        threadDelete.start();
        threadDelete2.start();
        threadDelete3.start();

        Thread.sleep(1000);
        System.out.println(queue);
        System.out.println(queue.size());

    }

}
