package ua.epamcourses.natalia_markova.homework.problem12.task02;

import java.util.*;

/**
 * Created by natalia_markova on 07.07.2016.
 */

class ConcurrentSkipListMap<K extends Comparable<K>,V> {
    private int size;
    private Node<K,V> root;
    private Set<Integer> blockedLevels = new HashSet<>();
    private Set<Node<K,V>> blockedNodes = new HashSet<>();

    public static class Node<K extends Comparable<K>, V> {
        private K key;
        private V value;
        private Map<Integer, Node<K,V>> next = new HashMap<>();
        private Map<Integer, Node<K,V>> prev = new HashMap<>();

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Node<?, ?> node = (Node<?, ?>) o;

            return key.equals(node.key);

        }

        @Override
        public int hashCode() {
            return key.hashCode();
        }

        @Override
        public String toString() {
            return "Node{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }
    }

    public ConcurrentSkipListMap() {
    }

    public synchronized void clear() {
        root = null;
        blockedLevels = new HashSet<>();
        size = 0;
    }

    public int size() {
        return size;
    }

    public V put(K key, V value) {
        if (key == null) {
            throw new NullPointerException();
        }
        synchronized (this) {
            if (root == null) {
                root = new Node<>(key, value);
                System.out.println("New node: " + root  + ": " + Thread.currentThread().getName() + " " + new Date().getTime());
                size++;
                return null;
            }
        }
        int levelIndex = root.next.size();
        if (levelIndex == 0) {
            try {
                blockLevel(levelIndex);
            } catch (InterruptedException e) {
                return  null;
            }
        }
        Map<String,Object> result = put(key, value, root, levelIndex);
        V returnedValue = (V) result.get("value");
        boolean insert = (boolean) result.get("insert");
        if (insert) {
            // node was inserted on the last level
            // need to release it
            Node<K,V> node =  (Node<K,V>) result.get("node");
            releaseNode(node);
        }
        if (returnedValue == null) {
            size++;
        }
        return returnedValue;
    }

    public V get(K key) {
        if (key == null) {
            throw new NullPointerException();
        }
        synchronized (this) {
            if (root == null) {
                return null;
            }
        }
        return get(key, root, root.next.size() - 1);
    }

    public V remove(K key) {
        if (key == null) {
            throw new NullPointerException();
        }
        synchronized (this) {
            if (root == null) {
                return null;
            }
        }
        Map<String,Object> result = remove(key, root, Math.max(root.next.size() - 1, 0));
        Node<K,V> nodeToRemove = (Node<K,V>) result.get("node");
        V value = (V) result.get("value");
        if (value != null) {
            size--;
        }
        if (nodeToRemove != null) {
            releaseNode(nodeToRemove);
        }
        return value;
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    @Override
    public synchronized String toString() {
        if (root == null) {
            return "[]";
        }
        StringBuilder str = new StringBuilder();
        if (root.next.size() == 0) {
            return "[" + root + "]";
        }
        for (int i = 0; i < root.next.size(); i++) {
            str.append("[");
            Node<K, V> node = root;
            while (node != null) {
                str.append(node.key).append(" - ").append(node.value).append(", ");
                node = node.next.get(i);
            }
            str.delete(str.length() - 2, str.length());
            str.append("]").append(System.getProperty("line.separator"));
        }
        return str.toString();
    }

    private Map<String,Object> put(K key, V value,  Node<K, V> startNode, int levelIndex) {
        Map<String,Object> result = new HashMap<>();
        result.put("value", null);
        result.put("insert", false);
        result.put("node", null);

        Node<K, V> node;
        Node<K, V> nextNode;

        // block level
        try {
            blockLevel(levelIndex);
        } catch (InterruptedException e) {
            return null;
        }
        node = startNode;
        nextNode = node.next.get(levelIndex);
        while (nextNode != null && nextNode.key.compareTo(key) <= 0) {
            node = nextNode;
            nextNode = node.next.get(levelIndex);
        }
        // block node in case another thread wants to delete it at the same time
        try {
            blockNode(node);
            if (node.key.compareTo(key) == 0) {
                result.put("value", node.value);
                System.out.println("Update node: " + node + ": " + Thread.currentThread().getName() + " " + new Date().getTime());
                node.value = value;
                releaseNode(node);
                releaseLevel(levelIndex);
                return result;
            }
            releaseNode(node);
        } catch (InterruptedException e) {
            return result;
        }
        if (levelIndex == 0) {
            Node<K,V> newNode = new Node<>(key, value);
            insertNode(node, newNode, 0);
            System.out.println("New node: " + newNode + ": " + Thread.currentThread().getName() + " " + new Date().getTime());
            result.put("node", newNode);
            result.put("insert", true);
            // block node for insertion
            try {
                blockNode(newNode);
            } catch (InterruptedException e) {
                return result;
            }
            releaseLevel(levelIndex);
            return result;
        } else {
            releaseLevel(levelIndex);
            result = put(key, value, node, levelIndex - 1);
            boolean insert = (boolean) result.get("insert");
            insert = insert && insertNodeOnLevel();
            if (insert) {
                // block current level
                try {
                    blockLevel(levelIndex);
                } catch (InterruptedException e) {
                    return null;
                }
                // find previous node
                node = startNode;
                nextNode = node.next.get(levelIndex);
                while (nextNode != null && nextNode.key.compareTo(key) <= 0) {
                    node = nextNode;
                    nextNode = node.next.get(levelIndex);
                }
                Node<K,V> newNode = (Node<K,V>) result.get("node");
                insertNode(node, newNode, levelIndex);
                releaseLevel(levelIndex);
            } else {
                result.put("insert", insert);
                node = (Node<K,V>) result.get("node");
                // release node
                if (node != null) {
                    releaseNode(node);
                }
            }
        }
        return result;
    }

    private boolean insertNodeOnLevel() {
        Random random = new Random();
        double bound = 0.5;
        double probability = random.nextDouble();
        return probability <= bound;
    }

    private V get (K key, Node<K, V> startNode, int levelIndex) {
        if (levelIndex < 0) {
            return null;
        }
        Node<K, V> node;
        Node<K, V> nextNode;
        try {
            blockLevel(levelIndex);
        } catch (InterruptedException e) {
            return null;
        }
        node = startNode;
        nextNode = node.next.get(levelIndex);
        while (nextNode != null && nextNode.key.compareTo(key) <= 0) {
            node = nextNode;
            nextNode = node.next.get(levelIndex);
        }
        // block node in case another thread wants to delete it or change it's value at the same time
        try {
            blockNode(node);
        } catch (InterruptedException e) {
            return null;
        }
        if (node.key.compareTo(key) == 0) {
            releaseNode(node);
            releaseLevel(levelIndex);
            return node.value;
        }
        releaseNode(node);
        releaseLevel(levelIndex);
        return get(key, node, levelIndex - 1);
    }

    private void insertNode(Node<K, V> node, Node<K, V> newNode, int levelIndex) {
        if (node.next.get(levelIndex) != null) {
            newNode.next.put(levelIndex, node.next.get(levelIndex));
            node.next.get(levelIndex).prev.put(levelIndex, newNode);
        }
        newNode.prev.put(levelIndex, node);
        node.next.put(levelIndex, newNode);
    }

    private Map<String,Object> remove (K key, Node<K, V> startNode, int levelIndex) {
        Map<String,Object> result = new HashMap<>();
        result.put("value", null);
        result.put("node", null);
        if (levelIndex < 0) {
            return result;
        }
        Node<K, V> node;
        Node<K, V> nextNode;
        try {
            blockLevel(levelIndex);
        } catch (InterruptedException e) {
            return result;
        }
        node = startNode;
        nextNode = node.next.get(levelIndex);
        while (nextNode != null && nextNode.key.compareTo(key) <= 0) {
            node = nextNode;
            nextNode = node.next.get(levelIndex);
        }
        if (levelIndex == 0) {
            try {
                blockNode(node);
            } catch (InterruptedException e) {
                return result;
            }
            if (node.key.compareTo(key) == 0) {
                result.put("node", node);
                result.put("value", node.value);
                System.out.println("Remove node: " + node + ": " + Thread.currentThread().getName() + " " + new Date().getTime());
                removeNode(node, levelIndex);
            } else {
                releaseNode(node);
            }
            releaseLevel(levelIndex);
            return result;
        } else {
            releaseLevel(levelIndex);
            result = remove(key, node, levelIndex - 1);
            Node<K,V> nodeToRemove = (Node<K,V>) result.get("node");
            if (nodeToRemove != null) {
                try {
                    blockLevel(levelIndex);
                } catch (InterruptedException e) {
                    return result;
                }
                node = startNode;
                nextNode = node.next.get(levelIndex);
                while (nextNode != null && nextNode.key.compareTo(key) <= 0) {
                    node = nextNode;
                    nextNode = node.next.get(levelIndex);
                }
                if (node != nodeToRemove) {
                    releaseNode(nodeToRemove);
                    result.put("node", null);
                } else {
                    removeNode(nodeToRemove, levelIndex);
                }
                releaseLevel(levelIndex);
            }
        }
        return result;
    }

    private void removeNode(Node<K, V> nodeToRemove, int levelIndex) {
        if (nodeToRemove.prev.get(levelIndex) == null) { // nodeToDelete == root
            if (nodeToRemove.next.get(levelIndex) != null) {
                if (levelIndex == 0) {
                    root = nodeToRemove.next.get(levelIndex);
                }
                root.prev.remove(levelIndex);
                if (root.next.get(levelIndex) == null) {
                    if (nodeToRemove.next.get(levelIndex) != root) {
                        root.next.put(levelIndex, nodeToRemove.next.get(levelIndex));
                    }
                }
                nodeToRemove.next.remove(levelIndex);
            } else {
                if (levelIndex == 0) {
                    root = null;
                } else {
                    root.next.remove(levelIndex);
                }
            }
        } else {
            Node<K, V> prevNode = nodeToRemove.prev.get(levelIndex);
            if (nodeToRemove.next.get(levelIndex) != null) {
                prevNode.next.put(levelIndex, nodeToRemove.next.get(levelIndex));
                nodeToRemove.next.get(levelIndex).prev.put(levelIndex, prevNode);
            } else {
                prevNode.next.remove(levelIndex);
            }
        }
    }

    private void blockLevel(int levelIndex) throws InterruptedException {
        synchronized (blockedLevels) {
            while (blockedLevels.contains(levelIndex)) {
                System.out.println("Waiting for the level: " + levelIndex + ": " + Thread.currentThread().getName() + " " + new Date().getTime());
                blockedLevels.wait();
            }
            blockedLevels.add(levelIndex);
            System.out.println("Level: " + levelIndex + " is blocked: " + Thread.currentThread().getName() + " " + new Date().getTime());
        }
    }

    private void releaseLevel(int levelIndex) {
        synchronized (blockedLevels) {
            blockedLevels.remove(levelIndex);
            System.out.println("Level: " + levelIndex + " is released: " + Thread.currentThread().getName() + " " + new Date().getTime());
            blockedLevels.notifyAll();
        }
    }

    private void blockNode(Node<K,V> node) throws InterruptedException {
        synchronized (blockedNodes) {
            while (blockedNodes.contains(node)) {
                System.out.println("Waiting for the node: " + node + ": " + Thread.currentThread().getName() + " " + new Date().getTime());
                blockedNodes.wait();
            }
            blockedNodes.add(node);
            System.out.println("Node: " + node + " is blocked: " + Thread.currentThread().getName() + " " + new Date().getTime());
        }
    }

    private void releaseNode(Node<K,V> node) {
        synchronized (blockedNodes) {
            blockedNodes.remove(node);
            System.out.println("Node: " + node + " is released: " + Thread.currentThread().getName() + " " + new Date().getTime());
            blockedNodes.notifyAll();
        }
    }

}

public class ConcurrentSkipListMapRealization {

    private static ConcurrentSkipListMap<Integer, Integer> map = new ConcurrentSkipListMap<>();

    static class MyThread extends Thread {

        int num;

        public MyThread(int num) {
            this.num = num;
        }

        @Override
        public void run() {
            for (int i = 1; i <= 10; i++) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (num % 5 == 4) {
                    System.out.println("remove(" + i + ") == " + map.remove(i));
                } else {
                    map.put(i, i * num * 10);
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        List<MyThread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            threads.add(new MyThread(i));
        }
        for (MyThread thread: threads) {
            thread.start();
        }
        Thread.sleep(3000);
        System.out.println(map);

//        for (int i = 1; i <= 10; i++) {
//            System.out.println("remove(" + i + ") == " + map.remove(i));
//            System.out.println(map);
//        }

    }
}
