package ua.epamcourses.natalia_markova.homework.task08.subtask01;

/**
 * Created by natalia_markova on 24.05.2016.
 */
public class PriorityLinkedQueue<K extends Comparable<K>, V> implements PriorityQueue<K, V>{

    private int size;
    private Node<K, V> first;

    public PriorityLinkedQueue() {
    }

    private static class Node<K, V> implements PriorityQueue.Entry<K, V>{
        private K key;
        private V value;
        private Node<K,V> next;

        public Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public K geyKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void insert(K key, V value) {
        if (size() == 0) {
            first = new Node<>(key, value, null);
            size++;
        } else {
            Node<K, V> node = first;
            Node<K, V> prevNode = null;
            while (node != null) {
                if (key.compareTo(node.key) == 0) {
                    node.value = value;
                    return;
                } else if (key.compareTo(node.key) < 0) {
                    Node<K, V> newNode = new Node<>(key, value, node);
                    if (node == first) {
                        first = newNode;
                    } else {
                        prevNode.next = newNode;
                    }
                    size++;
                    return;
                }
                prevNode = node;
                node = node.next;
            }
            Node<K, V> newNode = new Node<>(key, value, null);
            prevNode.next = newNode;
            size++;
        }
    }

    @Override
    public Node<K,V> get() {
        if (size() == 0) {
            return null;
        }
        Node<K,V> res = first;
        first = first.next;
        size--;
        return res;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }
        StringBuilder str = new StringBuilder("[");
        Node<K, V> node = first;
        str.append(node.key).append(" - ").append(node.value);
        while (node.next != null) {
            node = node.next;
            str.append(", ").append(node.key).append(" - ").append(node.value);
        }
        str.append("]");
        return str.toString();
    }

    public static void main(String[] args) {

        PriorityQueue<String, Integer> queue = new PriorityLinkedQueue<>();
        System.out.println(queue);
        for (int i = 0; i < 10; i++) {
            queue.insert(String.valueOf(i * 10), i);
        }
        System.out.println(queue);
        queue.insert("0", 100);
        System.out.println(queue);
        queue.insert("5", 500);
        System.out.println(queue);
        queue.insert("-1", -1);
        System.out.println(queue);
        System.out.println(queue.get());
        System.out.println(queue);
        while (queue.size() > 0) {
            System.out.println(queue.get());
        }
        System.out.println(queue);

    }
}
