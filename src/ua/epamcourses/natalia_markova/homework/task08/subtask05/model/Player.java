package ua.epamcourses.natalia_markova.homework.task08.subtask05.model;

import ua.epamcourses.natalia_markova.homework.task08.subtask05.service.FieldInitializer;
import ua.epamcourses.natalia_markova.homework.task08.subtask05.service.RandomFieldInitializer;

import java.util.Arrays;

/**
 * Created by natalia_markova on 13.05.2016.
 */
public abstract class Player {

    private String name;
    private Field field;
    private Field otherField;
    private Cell lastHitCell;


    public Player() throws ShipInitializingException {
        FieldInitializer initializer = new RandomFieldInitializer();
        field = initializer.initializeField();
        otherField = initializer.initializeEmptyField();
    }

    public Field getField() {
        return field;
    }

    public Field getOtherField() {
        return otherField;
    }

    public abstract Cell move() throws GameException;

    public MoveResult getMoveResult(Cell cell) throws GameException {
        return field.getMoveResult(cell);
    }

    public void processResult(Cell cell, MoveResult result) throws GameException {
        otherField.markCell(cell, result);
        if (result == MoveResult.SHOT) {
            lastHitCell = cell;
        } else if (result == MoveResult.DROWNED) {
            lastHitCell = null;
        }
    }

    public void viewFields() {
        // first line
        for (int i = 0; i < 10; i++) {
            System.out.print((i + 1) + "\t");
        }
        System.out.print("\t");
        for (int i = 0; i < 10; i++) {
            System.out.print((i + 1) + "\t");
        }
        System.out.println();

        // fields
        String letters = "abcdefghij";
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                viewCell(field.getCells()[i][j]);
            }
            System.out.print(letters.charAt(i) + "\t");
            for (int j = 0; j < 10; j++) {
                viewCell(otherField.getCells()[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    private void viewCell(Cell cell) {
        if (!cell.isHit() && !cell.isPartOfAShip()) {
            // empty cell
            System.out.print("\t");
        } else if (!cell.isHit() && cell.isPartOfAShip()) {
            // part of a ship
            System.out.print("0\t");
        } else if (cell.isHit() && cell.isPartOfAShip()) {
            // part of a ship and hit
            System.out.print("X\t");
        } else {
            // empty and hit
            System.out.print("-\t");
        }
    }

    public boolean won() {
        return !otherField.hasShipsLeft();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Cell getLastHitCell() {
        return lastHitCell;
    }

    public void setLastHitCell(Cell lastHitCell) {
        this.lastHitCell = lastHitCell;
    }

    @Override
    public String toString() {
        return name + System.getProperty("line.separator") + Arrays.toString(field.getShips());
    }
}
