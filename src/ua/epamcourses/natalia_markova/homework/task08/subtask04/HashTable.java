package ua.epamcourses.natalia_markova.homework.task08.subtask04;


import java.util.*;

/**
 * Created by natalia_markova on 25.05.2016.
 */
public class HashTable<K,V> implements Map<K,V>{

    private Node<K,V>[] data;
    private int size;

    private static final float RATIO = 0.75f;

    private static class Node<K,V> implements Map.Entry<K,V>{
        private K key;
        private V value;
        private Node<K,V> next;

        public Node(K key, V value, Node<K,V> next) {
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
    }

    public HashTable() {
        data = (Node<K,V>[]) new Node[10];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(K key) {
        int index = getKeyIndex(key);
        Node<K,V> node = getNodeByKey(data[index], key);
        return node != null;
    }

    @Override
    public V get(K key) {
        int index = getKeyIndex(key);
        Node<K,V> node = getNodeByKey(data[index], key);
        return (node == null ? null : node.value);
    }

    @Override
    public V put(K key, V value) {
        int index = getKeyIndex(key);
        Node<K,V> node = data[index];
        if (node == null) {
            data[index] = new Node<K,V>(key, value, null);
            size++;
            return null;
        }
        V oldValue = null;
        Node<K,V> prevNode = null;
        while (node != null) {
            if (node.key.equals(key)) {
                oldValue = node.value;
                node.value = value;
                break;
            }
            prevNode = node;
            node = node.next;
        }
        if (oldValue == null) {
            int maxSize = (int) (data.length * RATIO);
            if (maxSize < size) {
                resize();
            }
            Node<K, V> newNode = new Node<K, V>(key, value, null);
            if (prevNode != null) {
                prevNode.next = newNode;
            }
            size++;
        }
        return oldValue;
    }

    @Override
    public V remove(K key) {
        int index = getKeyIndex(key);
        Node<K,V> node = data[index];
        if (node == null) {
            return null;
        }
        V value = null;
        Node<K,V> prevNode = null;
        while (node != null) {
            if (node.key.equals(key)) {
                value = node.value;
                size--;
                break;
            }
            prevNode = node;
            node = node.next;
        }
        if (value != null) {
            if (prevNode != null) {
                prevNode.next = node.next;
            } else {
                data[index] = node.next;
            }
        }
        return value;
    }

    @Override
    public void clear() {
        data = (Node<K,V>[]) new Node[data.length];
        size = 0;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> entrySet = new HashSet<>();
        for (Node<K,V> node : data) {
            while (node != null) {
                entrySet.add(node);
                node = node.next;
            }
        }
        return entrySet;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
        for (Node<K,V> node : data) {
            while (node != null) {
                keySet.add(node.key);
                node = node.next;
            }
        }
        return keySet;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }
        StringBuilder str = new StringBuilder("[");
        for (Entry<K,V> entry: entrySet()) {
            str.append(entry.geyKey()).append(" - ").append(entry.getValue()).append("; ");
        }
        str.append("]");
        return str.toString();
    }

    private int getKeyIndex(K key) {
        int index = (key == null ? 0 : key.hashCode());
        return Math.abs(index % data.length);
    }

    private Node<K,V> getNodeByKey(Node<K,V> firstNode, K key) {
        if (firstNode == null) {
            return null;
        }
        Node<K,V> node = firstNode;
        while (node != null) {
            if (node.key.equals(key)) {
                return node;
            }
        }
        return null;
    }

    private void resize() {
        int newLength = data.length * 2;
        Node<K,V>[] oldData = data;
        data = (Node<K,V>[]) new Node[newLength];
        size = 0;
        for (Node<K,V> oldNode : oldData) {
            while (oldNode != null) {
                put(oldNode.key, oldNode.value);
                oldNode = oldNode.next;
            }
        }
    }

    public static void main(String[] args) {
        Map<Integer,Integer> map = new HashTable<>();
        for (int i = 0; i < 100; i++){
            map.put(i, i);
        }
        System.out.println(map);
        System.out.println(map.remove(-10));
        System.out.println(map.remove(10));
        System.out.println(map);
        map.clear();
        map.put(2, 200);
        System.out.println(map);
    }
}
