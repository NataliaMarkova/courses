package ua.epamcourses.natalia_markova.homework.task08.subtask05.model;

/**
 * Created by natalia_markova on 13.05.2016.
 */
public class Cell {
    private int x;
    private int y;
    private boolean isPartOfAShip;
    private boolean isHit;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isPartOfAShip() {
        return isPartOfAShip;
    }

    public void setIsPartOfAShip(boolean isPartOfAShip) {
        this.isPartOfAShip = isPartOfAShip;
    }

    public boolean isHit() {
        return isHit;
    }

    public void setIsHit(boolean isHit) {
        this.isHit = isHit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell cell = (Cell) o;

        if (x != cell.x) return false;
        return y == cell.y;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        String letters = "abcdefghij";
        return String.valueOf(letters.charAt(x)) + (y + 1);
    }

}
