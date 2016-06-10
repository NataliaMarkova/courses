package ua.epamcourses.natalia_markova.homework.problem05.task08.subtask05.service;

import ua.epamcourses.natalia_markova.homework.problem05.task08.subtask05.model.Cell;
import ua.epamcourses.natalia_markova.homework.problem05.task08.subtask05.model.Ship;

import java.security.InvalidParameterException;

/**
 * Created by natalia_markova on 13.05.2016.
 */
public class ShipFactory {

    ShipInitializer initializer;

    public ShipFactory(int size) {
        switch (size) {
            case 1 : initializer = new Ship1DeckInitializer(); break;
            case 2 : initializer = new Ship2DecksInitializer(); break;
            case 3 : initializer = new Ship3DecksInitializer(); break;
            case 4 : initializer = new Ship4DecksInitializer(); break;
        }
        if (initializer == null) {
            throw new InvalidParameterException();
        }
    }

    public Ship getShip(Cell initialCell, int variant) {
        return initializer.getShip(initialCell, variant);
    }

    public int getQtyOfVariants() {
        return initializer.getQtyOfVariants();
    }
}
