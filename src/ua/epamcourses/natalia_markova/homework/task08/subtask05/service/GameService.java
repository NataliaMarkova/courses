package ua.epamcourses.natalia_markova.homework.task08.subtask05.service;

import java.util.Random;

/**
 * Created by natalia_markova on 24.05.2016.
 */
public class GameService {

    private GameService() {

    }

    public static int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    public static boolean cellIsOk(int x, int y) {
        if (x >= 0 && x < 10 && y >=0 && y < 10) {
            return true;
        }
        return false;
    }
}
