package ua.epamcourses.natalia_markova.homework.task08.subtask02;

/**
 * Created by natalia_markova on 26.05.2016.
 */
public interface Set<E extends Comparable> {
    int size();
    boolean isEmpty();
    boolean contains(E element);
    boolean add(E element);
    boolean remove(E element);
    void clear();
}
