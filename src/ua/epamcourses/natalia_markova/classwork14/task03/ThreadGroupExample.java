package ua.epamcourses.natalia_markova.classwork14.task03;

/**
 * Created by natalia_markova on 03.06.2016.
 */

public class ThreadGroupExample {
    public static void main(String[] args) {
        ThreadGroup group = new ThreadGroup("GroupName");
        Thread thread = new Thread(group, "ThreadName");
    }
}
