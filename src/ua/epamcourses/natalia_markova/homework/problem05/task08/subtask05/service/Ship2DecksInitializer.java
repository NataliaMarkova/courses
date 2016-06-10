package ua.epamcourses.natalia_markova.homework.problem05.task08.subtask05.service;

import ua.epamcourses.natalia_markova.homework.problem05.task08.subtask05.model.Cell;
import ua.epamcourses.natalia_markova.homework.problem05.task08.subtask05.model.Ship;

/**
 * Created by natalia_markova on 13.05.2016.
 */
public class Ship2DecksInitializer extends ShipInitializer {

    public Ship2DecksInitializer() {
        qtyOfVariants = 2;
    }

    public int getQtyOfVariants() {
        return qtyOfVariants;
    }

    @Override
    public Ship getShip(Cell initialCell, int variant) {
        Cell[] cells = new Cell[2];
        cells[0] = initialCell;

        if (variant == 1) {
            //* //
            cells[1] = new Cell(initialCell.getX() + 1, initialCell.getY());
        } else {
            //*
            //
            cells[1] = new Cell(initialCell.getX(), initialCell.getY() + 1);
        }

        return new Ship(cells);
    }

}
