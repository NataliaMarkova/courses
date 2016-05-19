package ua.epamcourses.natalia_markova.homework03.task07.service;

import ua.epamcourses.natalia_markova.homework03.task07.model.Cell;
import ua.epamcourses.natalia_markova.homework03.task07.model.Ship;
import ua.epamcourses.natalia_markova.homework03.task07.model.ShipInitializingException;

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
        ShipService.usedCells = cells;

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
            for (int variant = 1; variant <= factory.getQtyOfVariants(); variant++) {
                Cell initialCell = getFreeCell();
                if (initialCell == null) {
                    throw new ShipInitializingException();
                }
                Ship ship = factory.getShip(initialCell, variant);
                if (shipIsOk(ship)) {
                    ships[index] = ship;
                    markCells(ship);
                    markUsedCells(ship);
                }
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
            int x = (int) (Math.random() * 10) - 1;
            int y = (int) (Math.random() * 10) - 1;
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
        return usedCells[cell.getX()][cell.getY()].isPartOfAShip();
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
                    if (cellIsUsed(cell)) {
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
                    if (x >= 0 && x < 10 && y >=0 && y < 10) {
                        if (usedCells[i][j] == null) {
                            usedCells[i][j] = new Cell(i, j);
                        }
                    }
                }
            }
        }
    }

}
