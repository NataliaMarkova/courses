package ua.epamcourses.natalia_markova.homework.task08.subtask05.model;

import ua.epamcourses.natalia_markova.homework.task08.subtask05.service.GameException;
import ua.epamcourses.natalia_markova.homework.task08.subtask05.service.GameService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by natalia_markova on 13.05.2016.
 */
public class Field {

    private Cell[][] cells = new Cell[10][10];
    private Ship[] ships = new Ship[10];

    // for other player field
    private Ship[] shipsHit = new Ship[10];
    private Ship lastHitShip;
    private Ship[] ship4Decks = new Ship[1];
    private Ship[] ship3Decks = new Ship[2];
    private Ship[] ship2Decks = new Ship[3];
    private Ship[] ship1Deck = new Ship[4];

    public Field() {
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    public Ship[] getShips() {
        return ships;
    }

    public void setShips(Ship[] ships) {
        this.ships = ships;
    }

    public MoveResult getMoveResult(Cell cell) {
        MoveResult result = null;
        int x = cell.getX();
        int y = cell.getY();
        if (cells[x][y].isHit()) {
            return MoveResult.ALREADY_SHOT;
        }
        cells[x][y].setIsHit(true);
        if (!cells[x][y].isPartOfAShip()) {
            result = MoveResult.MISS;
        } else {
            Ship ship = getShipByCell(ships, cell);
            if (ship == null) {
                throw new GameException("No ship found by cell: " + cell + System.getProperty("line.separator") + "ships: " + Arrays.toString(ships));
            }
            if (ship.isDrowned()) {
                result = MoveResult.DROWNED;
            } else {
                result = MoveResult.SHOT;
            }
        }
        processResult(cell, result);
        return result;
    }

    private void processResult(Cell cell, MoveResult result) {

        int x = cell.getX();
        int y = cell.getY();
        Cell fieldCell = cells[x][y];
        fieldCell.setIsHit(true);
        if (result == MoveResult.MISS) {
            return;
        }
        Ship ship = null;
        fieldCell.setIsPartOfAShip(true);
        for (int i = x - 1; i <= x + 1; i++ ) {
            for (int j = y - 1; j <= y + 1; j++ ) {
                if (GameService.cellIsOk(i, j)) {
                    ship = getShipByCell(shipsHit, new Cell(i, j));
                    if (ship != null) {
                        break;
                    }
                }
            }
            if (ship != null) {
                break;
            }
        }

        if (ship == null) {
            int size = getPossibleShipSize();
            if (size == 0) {
                throw new GameException("All ships are drowned");
            }
            ship = new Ship(size);
            setShip(shipsHit, ship);
        }
        ship.setCell(fieldCell);
        if (result == MoveResult.DROWNED) {
            int index = getShipIndex(ship);
            int size = ship.getSize();
            Ship theShip = new Ship(size);
            for (Cell shipCell : ship.getCells()) {
                if (shipCell == null) {
                    break;
                }
                theShip.setCell(shipCell);
            }
            shipsHit[index] = theShip;
            if (size == 1) {
                setShip(ship1Deck, theShip);
            } else if (size == 2) {
                setShip(ship2Decks, theShip);
            } else if (size == 3) {
                setShip(ship3Decks, theShip);
            } else if (size == 4) {
                setShip(ship4Decks, theShip);
            }
            markCellsAsHit(theShip);
        }
        if (result == MoveResult.SHOT) {
            lastHitShip = ship;
        } else if (result == MoveResult.DROWNED) {
            lastHitShip = null;
        }
    }

    private int getShipIndex(Ship ship) {
        for (int i = 0; i < shipsHit.length; i++) {
            Ship sh = shipsHit[i];
            if (sh == ship) {
                return i;
            }
        }
        throw new GameException("The ship " + ship.toString() + " + is not found in hit ships");
    }

    private void setShip(Ship[] ships, Ship ship) {
        for (int i = 0; i < ships.length; i++) {
            if (ships[i] == null) {
                ships[i] = ship;
                return;
            }
        }
        throw new GameException("Couldn't set a ship!");
    }

    private Ship getShipByCell(Ship[] ships, Cell cell) {
        for (Ship ship : ships) {
            if (ship!= null && ship.hasCell(cell)) {
                return ship;
            }
        }
        return null;
    }

    private void markCellsAsHit(Ship ship) {
        for (Cell cell : ship.getCells()) {
            int x = cell.getX();
            int y = cell.getY();
            for (int i = x - 1; i <= x + 1; i++) {
                for (int j = y - 1; j <= y + 1; j++) {
                    if (GameService.cellIsOk(i, j)) {
                        cells[i][j].setIsHit(true);
                    }
                }
            }
        }
    }

    private int getPossibleShipSize() {
        if (hasNullShips(ship4Decks)) {
            return 4;
        }
        if (hasNullShips(ship3Decks)) {
            return 3;
        }
        if (hasNullShips(ship2Decks)) {
            return 2;
        }
        if (hasNullShips(ship1Deck)) {
            return 1;
        }
        return 0;
    }

    public boolean hasShipsLeft() {
        return (getPossibleShipSize() == 0 ? false : true);
    }

    public Cell getUnHitCell() {
        if (lastHitShip != null) {
            List<Cell> possibleCells = new ArrayList<>();
            for (Cell hitCell : lastHitShip.getCells()) {
                if (hitCell == null) {
                    break;
                }
                int x = hitCell.getX();
                int y = hitCell.getY();

                for (int i = - 1; i <= 1; i = i + 2) {
                    if (GameService.cellIsOk(x + i, y)) {
                        Cell cell = cells[x + i][y];
                        if (!cell.isHit()) {
                            possibleCells.add(cell);
                        }
                    }
                    if (GameService.cellIsOk(x, y + i)) {
                        Cell cell = cells[x][y + i];
                        if (!cell.isHit()) {
                            possibleCells.add(cell);
                        }
                    }
                }
            }
            if (possibleCells.size() == 0) {
                throw new GameException("No possible cells found");
            }
            int index = GameService.getRandomNumber(0, possibleCells.size() -  1);
            return possibleCells.get(index);
        } else {
            ArrayList<Cell> possibleCells = getPossibleCells();
            if (possibleCells.size() == 0) {
                throw new GameException("No possible cells found");
            }
            int index = GameService.getRandomNumber(0, possibleCells.size() - 1);
            return possibleCells.get(index);
        }
    }

    public void viewCell(int x, int y, boolean viewShips) {
        Cell cell = cells[x][y];
        if (!cell.isHit() && !cell.isPartOfAShip()) {
            // empty cell
            System.out.print("\t");
        } else if (!cell.isHit() && cell.isPartOfAShip()) {
            // part of a ship
            if (viewShips) {
                System.out.print("0\t");
            } else {
                System.out.print("\t");
            }
        } else if (cell.isHit() && cell.isPartOfAShip()) {
            // part of a ship and hit
            System.out.print("X\t");
        } else {
            // empty and hit
            System.out.print("-\t");
        }
    }

    private boolean hasNullShips(Ship[] ships) {
        for (Ship ship : ships) {
            if (ship == null) {
                return true;
            }
        }
        return false;
    }

    private ArrayList<Cell> getPossibleCells() {
        ArrayList<Cell> possibleCells = new ArrayList<>();
        for (int i = 0; i < 10; i++ ) {
            for (int j = 0; j < 10; j++ ) {
                Cell cell = cells[i][j];
                if (!cell.isHit()) {
                    possibleCells.add(cell);
                }
            }
        }
        return possibleCells;
    }

}
