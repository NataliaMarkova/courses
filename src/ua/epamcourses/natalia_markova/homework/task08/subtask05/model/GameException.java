package ua.epamcourses.natalia_markova.homework.task08.subtask05.model;

/**
 * Created by natalia_markovs on 13.05.2016.
 */
public class GameException extends Exception {
    public GameException() {
    }

    public GameException(String message) {
        super(message);
    }

    public GameException(String message, Throwable cause) {
        super(message, cause);
    }
}
