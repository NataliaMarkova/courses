package ua.epamcourses.natalia_markova.homework.problem05.task08.subtask05.service;

import ua.epamcourses.natalia_markova.homework.problem05.task08.subtask05.model.Cell;
import ua.epamcourses.natalia_markova.homework.problem05.task08.subtask05.model.Ship;

/**
 * Created by natalia_markova on 13.05.2016.
 */
public class Ship3DecksInitializer extends ShipInitializer {

    public Ship3DecksInitializer() {
        qtyOfVariants = 6;
    }

    public int getQtyOfVariants() {
        return qtyOfVariants;
    }

    @Override
    public Ship getShip(Cell initialCell, int variant) {
        Cell[] cells = new Cell[3];
        cells[0] = initialCell;

        if (variant == 1) {
            //* // //
            cells[1] = new Cell(initialCell.getX() + 1, initialCell.getY());
            cells[2] = new Cell(initialCell.getX() + 2, initialCell.getY());
        } else if (variant == 2) {
            //*
            //
            //
            cells[1] = new Cell(initialCell.getX(), initialCell.getY() + 1);
            cells[2] = new Cell(initialCell.getX(), initialCell.getY() + 2);
        } else if (variant == 3) {
            //*
            // //
            cells[1] = new Cell(initialCell.getX(), initialCell.getY() + 1);
            cells[2] = new Cell(initialCell.getX() + 1, initialCell.getY() + 1);
        } else if (variant == 4) {
                //
            //* //
            cells[1] = new Cell(initialCell.getX() + 1, initialCell.getY());
            cells[2] = new Cell(initialCell.getX() + 1, initialCell.getY() - 1);
        } else if (variant == 5) {
            //* //
            //
            cells[1] = new Cell(initialCell.getX(), initialCell.getY() + 1);
            cells[2] = new Cell(initialCell.getX() + 1, initialCell.getY());
        } else {
            //* //
                //
            cells[1] = new Cell(initialCell.getX() + 1, initialCell.getY());
            cells[2] = new Cell(initialCell.getX() + 1, initialCell.getY() + 1);

        }
        return new Ship(cells);
    }
}
