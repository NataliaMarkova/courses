package ua.epamcourses.natalia_markova.homework.task08.subtask04;


import java.util.*;

/**
 * Created by natalia_markova on 25.05.2016.
 */
public class HashTable<K,V> implements Map<K,V>{

    private int bucketSize = 10;
    private List<List<Node<K,V>>> data;
    private int size;

    private class Node<K,V> implements Map.Entry<K,V>{
        private K key;
        private V value;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
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
        this(0);
    }

    public HashTable(int bucketSize) {
        if (bucketSize > 0) {
            this.bucketSize = bucketSize;
        }
        data = new ArrayList<>(this.bucketSize);
        for (int i = 0; i < this.bucketSize; i++) {
            data.add(new LinkedList<>());
        }
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
        Node<K,V> node = getNodeByKey(data.get(index), key);
        return node != null;
    }

    @Override
    public V get(K key) {
        int index = getKeyIndex(key);
        Node<K,V> node = getNodeByKey(data.get(index), key);
        return (node == null ? null : node.value);
    }

    @Override
    public V put(K key, V value) {
        int index = getKeyIndex(key);
        List<Node<K,V>> list = data.get(index);
        V oldValue = null;
        for (Node<K,V> node: list) {
            if (node.key.equals(key)) {
                oldValue = node.value;
                node.value = value;
                break;
            }
        }
        if (oldValue == null) {
            list.add(new Node<K, V>(key, value));
            size++;
        }
        return oldValue;
    }

    @Override
    public V remove(K key) {
        int index = getKeyIndex(key);
        List<Node<K,V>> list = data.get(index);
        V value = null;
        Node<K,V> node = getNodeByKey(list, key);
        if (node != null) {
            value = node.value;
            list.remove(node);
            size--;

        }
        return value;
    }

    @Override
    public void clear() {
        data = new ArrayList<>(this.bucketSize);
        for (int i = 0; i < this.bucketSize; i++) {
            data.add(new LinkedList<>());
        }
        size = 0;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> entrySet = new HashSet<>();
        for (List<Node<K,V>> list : data) {
            for (Node<K,V> node : list) {
                entrySet.add(node);
            }
        }
        return entrySet;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
        for (List<Node<K,V>> list : data) {
            for (Node<K,V> node : list) {
                keySet.add(node.key);
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
        int index = key.hashCode();
        return index % bucketSize;
    }

    private Node<K,V> getNodeByKey(List<Node<K,V>> list, K key) {
        for (Node<K,V> node : list) {
            if (node.key.equals(key)) {
                return node;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Map<Integer,Integer> map = new HashTable<>();
        for (int i = 0; i < 25; i++){
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
