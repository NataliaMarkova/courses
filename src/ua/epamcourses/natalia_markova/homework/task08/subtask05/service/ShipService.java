package ua.epamcourses.natalia_markova.homework.task08.subtask05.service;

import ua.epamcourses.natalia_markova.homework.task08.subtask05.model.Cell;
import ua.epamcourses.natalia_markova.homework.task08.subtask05.model.Ship;
import ua.epamcourses.natalia_markova.homework.task08.subtask05.model.ShipInitializingException;

import java.util.Random;

/**
 * Created by natalia_markova on 13.05.2016.
 */
public class ShipService {

    private static Cell[][] cells; // game field cells
    private static Cell[][] triedCells;
    private static Cell[][] usedCells;
    private static Ship[] ships = new Ship[10];
    private static int index;

    private ShipService() {
    }

    public static Ship[] initializeShips(Cell[][] cells) throws ShipInitializingException {
        ShipService.cells = cells;
        ShipService.usedCells = cells.clone();

        index = 0;
        initializeShip(4);
        index++;
        for (int i = 0; i < 2; i++) {
            initializeShip(3);
            index++;
        }
        for (int i = 0; i < 3; i++) {
            initializeShip(2);
            index++;
        }
        for (int i = 0; i < 4; i++) {
            initializeShip(1);
            index++;
        }

        return ships;
    }

    private static void initializeShip(int decks) throws ShipInitializingException{
        ShipFactory factory = new ShipFactory(decks);
        triedCells = usedCells.clone();
        while (hasUntriedCells()) {
            Cell initialCell = getFreeCell();
            if (initialCell == null) {
                throw new ShipInitializingException();
            }
            int variant = getRandomNumber(1, factory.getQtyOfVariants());
            Ship ship = factory.getShip(initialCell, variant);
            if (shipIsOk(ship)) {
                ships[index] = ship;
                markCells(ship);
                markUsedCells(ship);
                return;
            }
        }
    }

    private static boolean hasUntriedCells() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (triedCells[i][j] == null) {
                    return true;
                }
            }
        }
        return false;
    }

    private static Cell getFreeCell() {
        Cell cell = null;
        while (hasUntriedCells()) {
            int x = getRandomNumber(0, 9);
            int y = getRandomNumber(0, 9);
            cell = new Cell(x, y);
            if (cellIsUsed(cell)) {
                triedCells[x][y] = cell;
            } else {
                break;
            }
        }
        return cell;
    }

    private static boolean cellIsUsed(Cell cell) {
        if (cell == null) {
            return false;
        }
        Cell usedCell = usedCells[cell.getX()][cell.getY()];
        return usedCell != null; //&& usedCell.isPartOfAShip();
    }

    private static boolean shipIsOk(Ship ship) {
        for (Cell cell : ship.getCells()) {
            int x = cell.getX();
            int y = cell.getY();
            if (x < 0 || y < 0 || x > 9 || y > 9) {
                return false;
            }
            for (int i = x - 1; i <= x + 1; i++ ) {
                for (int j = y - 1; j <= y + 1; j++ ) {
                    if (i < 0 || j < 0 || i > 9 || j > 9) {
                        continue;
                    }
                    Cell currentCell = cells[i][j];
                    if (cellIsUsed(currentCell)) {
                        return false;
                    }
                }
            }

        }
        return true;
    }

    private static void markCells(Ship ship) {
        for (Cell cell : ship.getCells()) {
            cell.setIsPartOfAShip(true);
            cells[cell.getX()][cell.getY()] = cell;
        }
    }

    private static void markUsedCells(Ship ship) {
        for (Cell cell : ship.getCells()) {
            int x = cell.getX();
            int y = cell.getY();
            for (int i = x - 1; i <= x + 1; i++ ) {
                for (int j = y - 1; j <= y + 1; j++ ) {
                    if (i >= 0 && i < 10 && j >=0 && j < 10) {
                        if (usedCells[i][j] == null) {
                            usedCells[i][j] = new Cell(i, j);
                        }
                    }
                }
            }
        }
    }

    public static int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

}
