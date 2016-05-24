package ua.epamcourses.natalia_markova.homework.task08.subtask01;


/**
 * Created by natalia_markova on 24.05.2016.
 */
public interface PriorityQueue<K extends Comparable<K>, V> {
    int size();
    void insert(K key, V value);
    Entry<K, V> get();

    interface Entry<K,V> {
        K geyKey();
        V getValue();
    }
}
