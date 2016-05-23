package ua.epamcourses.natalia_markova.homework.task08.subtask05.model;

import java.security.InvalidParameterException;

/**
 * Created by natalia_markova on 13.05.2016.
 */
public class Ship {
    Cell[] cells;

    public Ship(int size) {
        if (size < 1 || size > 4) {
            throw new IndexOutOfBoundsException();
        }
        cells = new Cell[size];
    }

    public Ship(Cell[] cells) {
        if (cells.length < 1 || cells.length > 4) {
            throw new InvalidParameterException();
        }
        this.cells = cells;
    }

    public Cell[] getCells() {
        return cells;
    }

    public void setCell(Cell cell) {
        for (int i = 0; i < cells.length; i++) {
            if (cells[i] == null) {
                cells[i] = cell;
                return;
            }
        }
    }

    public boolean isDrowned() {
        for (Cell cell : cells) {
            if (!cell.isHit()) {
                return true;
            }
        }
        return true;
    }

    public boolean hasCell(Cell cell) {
        for (Cell c : cells) {
            if (c.equals(cell)) {
                return true;
            }
        }
        return false;
    }

    public int getSize() {
        int size = 0;
        for (int i = 1; i < size; i++) {
            if (cells[i] != null) {
                size++;
            } else {
                break;
            }
        }
        return size;
    }

}
