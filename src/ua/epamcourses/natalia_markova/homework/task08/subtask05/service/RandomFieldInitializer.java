package ua.epamcourses.natalia_markova.homework.task08.subtask05.service;

import ua.epamcourses.natalia_markova.homework.task08.subtask05.model.Cell;
import ua.epamcourses.natalia_markova.homework.task08.subtask05.model.Field;
import ua.epamcourses.natalia_markova.homework.task08.subtask05.model.Ship;
import ua.epamcourses.natalia_markova.homework.task08.subtask05.model.ShipInitializingException;

/**
 * Created by natalia_markova on 13.05.2016.
 */
public class RandomFieldInitializer implements FieldInitializer {

    @Override
    public Field initializeField() throws ShipInitializingException {
        Field field = new Field();
        Cell[][] cells = field.getCells();

        // initializing ships
        Ship[] ships = ShipService.initializeShips(cells);

        // initializing the rest cells of the field
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (cells[i][j] == null) {
                    cells[i][j] = new Cell(i, j);
                }
            }
        }
        field.setCells(cells);
        field.setShips(ships);
        return field;
    }

    @Override
    public Field initializeEmptyField() {
        Field field = new Field();
        Cell[][] cells = field.getCells();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }
        field.setCells(cells);
        return field;
    }

}
