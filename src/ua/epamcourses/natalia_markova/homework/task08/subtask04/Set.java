package ua.epamcourses.natalia_markova.homework.task08.subtask04;

/**
 * Created by natalia_markova on 24.05.2016.
 */
public interface Set<T> {
    boolean add(T element);
    boolean contains(T element);
    boolean remove(T element);
    boolean isEmpty();
    int size();
    void clear();
}
