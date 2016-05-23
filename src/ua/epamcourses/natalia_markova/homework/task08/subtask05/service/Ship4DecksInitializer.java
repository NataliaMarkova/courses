package ua.epamcourses.natalia_markova.homework.task08.subtask05.service;

import ua.epamcourses.natalia_markova.homework.task08.subtask05.model.Cell;
import ua.epamcourses.natalia_markova.homework.task08.subtask05.model.Ship;

/**
 * Created by natalia_markova on 13.05.2016.
 */
public class Ship4DecksInitializer extends ShipInitializer {
    public Ship4DecksInitializer() {
        qtyOfVariants = 19;
    }

    public int getQtyOfVariants() {
        return qtyOfVariants;
    }

    @Override
    public Ship getShip(Cell initialCell, int variant) {

        Cell[] cells = new Cell[4];
        cells[0] = initialCell;

        if (variant == 1) {
            //* //
            // //
            cells[1] = new Cell(initialCell.getX() + 1, initialCell.getY());
            cells[2] = new Cell(initialCell.getX(), initialCell.getY() + 1);
            cells[3] = new Cell(initialCell.getX() + 1, initialCell.getY() + 1);
        } else if (variant == 2) {
            //*
            //
            //
            //
            cells[1] = new Cell(initialCell.getX(), initialCell.getY() + 1);
            cells[2] = new Cell(initialCell.getX(), initialCell.getY() + 2);
            cells[3] = new Cell(initialCell.getX(), initialCell.getY() + 3);
        } else if (variant == 3) {
            //* // // //
            cells[1] = new Cell(initialCell.getX() + 1, initialCell.getY());
            cells[2] = new Cell(initialCell.getX() + 2, initialCell.getY());
            cells[3] = new Cell(initialCell.getX() + 3, initialCell.getY());

        } else if (variant == 4) {
                //
            //* // //
            cells[1] = new Cell(initialCell.getX() + 1, initialCell.getY());
            cells[2] = new Cell(initialCell.getX() + 1, initialCell.getY() - 1);
            cells[3] = new Cell(initialCell.getX() + 2, initialCell.getY());

        } else if (variant == 5) {
            //*
            // //
            //
            cells[1] = new Cell(initialCell.getX(), initialCell.getY() + 1);
            cells[2] = new Cell(initialCell.getX() + 1, initialCell.getY() + 1);
            cells[3] = new Cell(initialCell.getX(), initialCell.getY() + 2);
        } else if (variant == 6) {
            //* // //
                //
            cells[1] = new Cell(initialCell.getX() + 1, initialCell.getY());
            cells[2] = new Cell(initialCell.getX() + 2, initialCell.getY());
            cells[3] = new Cell(initialCell.getX() + 1, initialCell.getY() + 1);
        } else if (variant == 7) {
                //
            //* //
                //
            cells[1] = new Cell(initialCell.getX() + 1, initialCell.getY() - 1);
            cells[2] = new Cell(initialCell.getX() + 1, initialCell.getY());
            cells[3] = new Cell(initialCell.getX() + 1, initialCell.getY() + 1);
        } else if (variant == 8) {
            //*
            // //
               //
            cells[1] = new Cell(initialCell.getX(), initialCell.getY() + 1);
            cells[2] = new Cell(initialCell.getX() + 1, initialCell.getY() + 1);
            cells[3] = new Cell(initialCell.getX() + 1, initialCell.getY() + 2);
        } else if (variant == 9) {
                // //
            //* //
            cells[1] = new Cell(initialCell.getX() + 1, initialCell.getY());
            cells[2] = new Cell(initialCell.getX() + 1, initialCell.getY() - 1);
            cells[3] = new Cell(initialCell.getX() + 2, initialCell.getY() - 1);
        } else if (variant == 10) {
            //* //
                // //
            cells[1] = new Cell(initialCell.getX() + 1, initialCell.getY());
            cells[2] = new Cell(initialCell.getX() + 1, initialCell.getY() + 1);
            cells[3] = new Cell(initialCell.getX() + 2, initialCell.getY() + 1);
        } else if (variant == 11) {
                //
            //* //
            //
            cells[1] = new Cell(initialCell.getX() + 1, initialCell.getY());
            cells[2] = new Cell(initialCell.getX() + 1, initialCell.getY() - 1);
            cells[3] = new Cell(initialCell.getX(), initialCell.getY() + 1);
        } else if (variant == 12) {
            //* // //
            //
            cells[1] = new Cell(initialCell.getX() + 1, initialCell.getY());
            cells[2] = new Cell(initialCell.getX() + 2, initialCell.getY());
            cells[3] = new Cell(initialCell.getX(), initialCell.getY() + 1);
        } else if (variant == 13) {
            //* //
                //
                //
            cells[1] = new Cell(initialCell.getX() + 1, initialCell.getY());
            cells[2] = new Cell(initialCell.getX() + 1, initialCell.getY() + 1);
            cells[3] = new Cell(initialCell.getX() + 1, initialCell.getY() + 2);
        } else if (variant == 14) {
                   //
            //* // //
            cells[1] = new Cell(initialCell.getX() + 1, initialCell.getY());
            cells[2] = new Cell(initialCell.getX() + 2, initialCell.getY());
            cells[3] = new Cell(initialCell.getX() + 2, initialCell.getY() - 1);
        } else if (variant == 15) {
            //*
            //
            // //
            cells[1] = new Cell(initialCell.getX(), initialCell.getY() + 1);
            cells[2] = new Cell(initialCell.getX(), initialCell.getY() + 2);
            cells[3] = new Cell(initialCell.getX() + 1, initialCell.getY() + 2);
        } else if (variant == 16) {
            //* // //
                   //
            cells[1] = new Cell(initialCell.getX() + 1, initialCell.getY());
            cells[2] = new Cell(initialCell.getX() + 2, initialCell.getY());
            cells[3] = new Cell(initialCell.getX() + 2, initialCell.getY() + 1);
        } else if (variant == 17) {
            //* //
            //
            //
            cells[1] = new Cell(initialCell.getX() + 1, initialCell.getY());
            cells[2] = new Cell(initialCell.getX(), initialCell.getY() + 1);
            cells[3] = new Cell(initialCell.getX(), initialCell.getY() + 2);
        } else if (variant == 18) {
            //*
            // // //
            cells[1] = new Cell(initialCell.getX(), initialCell.getY());
            cells[2] = new Cell(initialCell.getX() + 1, initialCell.getY() + 1);
            cells[3] = new Cell(initialCell.getX() + 2, initialCell.getY() + 1);
        } else if (variant == 19) {
                //
                //
            //* //
            cells[1] = new Cell(initialCell.getX() + 1, initialCell.getY());
            cells[2] = new Cell(initialCell.getX() + 1, initialCell.getY() - 1);
            cells[3] = new Cell(initialCell.getX() + 1, initialCell.getY() - 2);
        }

        return new Ship(cells);
    }
}
