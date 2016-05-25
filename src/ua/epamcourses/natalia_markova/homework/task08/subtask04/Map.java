package ua.epamcourses.natalia_markova.homework.task08.subtask04;

import java.util.Set;

/**
 * Created by natalia_markova on 24.05.2016.
 */
public interface Map<K,V> {
    int size();
    boolean isEmpty();
    boolean containsKey(K key);
    V get(K key);
    V put(K key, V value);
    V remove(K key);
    void clear();
    Set<Entry<K,V>> entrySet();
    Set<K> keySet();

    interface Entry<K,V> {
        K geyKey();
        V getValue();
    }
}
