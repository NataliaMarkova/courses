package ua.epamcourses.natalia_markova.homework.problem05.task08.subtask05.service;

import ua.epamcourses.natalia_markova.homework.problem05.task08.subtask05.model.Cell;
import ua.epamcourses.natalia_markova.homework.problem05.task08.subtask05.model.Ship;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by natalia_markova on 13.05.2016.
 */
public class ShipService {

    private static Cell[][] cells; // game field cells
    private static List<Cell> availableCells;
    private static Cell[][] usedCells;
    private static Ship[] ships;
    private static int index;

    private ShipService() {
    }

    public static Ship[] initializeShips(Cell[][] cells) throws ShipInitializingException {
        ShipService.cells = cells;
        ships = new Ship[10];
        usedCells = new Cell[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                usedCells[i][j] = new Cell(i, j);
            }
        }

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
        defineAvailableCells();
        while (availableCells.size() > 0) {
            Cell initialCell = getFreeCell();
            int variant = GameService.getRandomNumber(1, factory.getQtyOfVariants());
            Ship ship = factory.getShip(initialCell, variant);
            if (shipIsOk(ship)) {
                ships[index] = ship;
                markCells(ship);
                markUsedCells(ship);
                return;
            }
            availableCells.remove(initialCell);
        }
        throw new ShipInitializingException();
    }

    private static void defineAvailableCells() {
        availableCells = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (!usedCells[i][j].isPartOfAShip()) {
                    availableCells.add(usedCells[i][j]);
                }
            }
        }
    }

    private static Cell getFreeCell() {
        int index = GameService.getRandomNumber(0, availableCells.size() - 1);
        return availableCells.get(index);
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

    private static boolean cellIsUsed(Cell cell) {
        if (cell == null) {
            return false;
        }
        Cell usedCell = usedCells[cell.getX()][cell.getY()];
        return usedCell.isPartOfAShip();
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
                        if (!usedCells[i][j].isPartOfAShip()) {
                            usedCells[i][j].setIsPartOfAShip(true);
                        }
                    }
                }
            }
        }
    }

}
