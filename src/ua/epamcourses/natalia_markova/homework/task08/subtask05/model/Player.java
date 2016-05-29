package ua.epamcourses.natalia_markova.homework.task08.subtask05.model;

import ua.epamcourses.natalia_markova.homework.task08.subtask05.service.FieldInitializer;
import ua.epamcourses.natalia_markova.homework.task08.subtask05.service.GameException;
import ua.epamcourses.natalia_markova.homework.task08.subtask05.service.RandomFieldInitializer;
import ua.epamcourses.natalia_markova.homework.task08.subtask05.service.ShipInitializingException;

import java.util.Arrays;

/**
 * Created by natalia_markova on 13.05.2016.
 */
public abstract class Player {

    private String name;
    private Field field;
    private Field otherField;

    public Player() {

    }

    public void setField(Field field) {
        this.field = field;
    }

    public void setOtherField(Field otherField) {
        this.otherField = otherField;
    }

    public Field getField() {
        return field;
    }

    public Field getOtherField() {
        return otherField;
    }

    protected abstract Cell move();

    public MoveResult getMoveResult() {
        return otherField.getMoveResult(move());
    }

    public void viewFields() {
        viewFields(false);
    }

    public void viewFields(boolean viewBothFieldsShips) {
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
                field.viewCell(i, j, true);
            }
            System.out.print(letters.charAt(i) + "\t");
            for (int j = 0; j < 10; j++) {
                otherField.viewCell(i, j, viewBothFieldsShips);
            }
            System.out.println();
        }
        System.out.println();
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

    @Override
    public String toString() {
        return name + System.getProperty("line.separator") + Arrays.toString(field.getShips());
    }
}
