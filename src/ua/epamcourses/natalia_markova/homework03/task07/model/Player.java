package ua.epamcourses.natalia_markova.homework03.task07.model;

import ua.epamcourses.natalia_markova.homework03.task07.service.FieldInitializer;
import ua.epamcourses.natalia_markova.homework03.task07.service.RandomFieldInitializer;

/**
 * Created by natalia_markova on 13.05.2016.
 */
public abstract class Player {

    private String name;
    private Field field;
    private Field otherField;
    private Cell lastHitCell;


    public Player() {
        FieldInitializer initializer = new RandomFieldInitializer();
        field = initializer.initializeField();
        otherField = new Field();
    }

    public Field getMyField() {
        return field;
    }

    public abstract Cell move();
    public MoveResult getMoveResult(Cell cell) {
        return field.getMoveResult(cell);
    }
    public void processResult(Cell cell, MoveResult result) throws GameException {
        otherField.markCell(cell, result);
    }
    public boolean won() {
        return otherField.hasShipsLeft();
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
}
