package ua.epamcourses.natalia_markova.homework.problem05.task08.subtask02_03;

/**
 * Created by natalia_markova on 26.05.2016.
 */
public interface Set<E extends Comparable<E>> {
    int size();
    boolean isEmpty();
    boolean contains(E element);
    boolean add(E element);
    boolean remove(E element);
    void clear();

}
