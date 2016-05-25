package ua.epamcourses.natalia_markova.homework.task08.subtask05.service;

/**
 * Created by natalia_markovs on 13.05.2016.
 */
public class GameException extends RuntimeException {
    public GameException() {
    }

    public GameException(String message) {
        super(message);
    }

    public GameException(String message, Throwable cause) {
        super(message, cause);
    }

    public GameException(Throwable cause) {
        super(cause);
    }
}
