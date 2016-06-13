package ua.epamcourses.natalia_markova.homework.problem08.task02.tetris;

import java.util.Random;

/**
 * Created by natalia_markova on 12.06.2016.
 */

abstract class Figure {
}

class StandardFigure extends Figure {

}

class SuperFigure extends Figure {

}

class FigureFactory {

    private static StandardFigure[] standardFigures;
    private static SuperFigure[] superFigures;
    private static final int superFigureProbability = 10;

    static {
        // initializing Figures
    }

    public static Figure getFigure() {

        int probability = getRandomNumber(0, 100);
        if (probability <= superFigureProbability) {
            int index = getRandomNumber(0, superFigures.length - 1);
            return superFigures[index];
        } else {
            int index = getRandomNumber(0, standardFigures.length - 1);
            return standardFigures[index];
        }
    }

    private static int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }
}

public class TetrisFactoryMethod {

    public static void main(String[] args) {
        Figure figure = FigureFactory.getFigure();
    }
}
