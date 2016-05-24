package ua.epamcourses.natalia_markova.homework.task08.subtask05.model;

import ua.epamcourses.natalia_markova.homework.task08.subtask05.service.ShipService;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by natalia_markova on 13.05.2016.
 */
public class Field {

    private Cell[][] cells = new Cell[10][10];
    private Ship[] ships = new Ship[10];

    // for other player field
    private Ship[] ship4Decks= new Ship[1];
    private Ship[] ship3Decks = new Ship[2];
    private Ship[] ship2Decks = new Ship[3];
    private Ship[] ship1Deck = new Ship[4];

    private int index;


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

    public MoveResult getMoveResult(Cell cell) throws GameException {
        int x = cell.getX();
        int y = cell.getY();
        cells[x][y].setIsHit(true);
        if (!cells[x][y].isPartOfAShip()) {
            return MoveResult.MISS;
        }
        Ship ship = getShipByCell(cell);
        if (ship == null) {
            throw new GameException("No ship found by cell: " + cell + System.getProperty("line.separator") + "ships: " + Arrays.toString(ships));
        }
        if (ship.isDrowned()) {
            markCellsAsHit(ship);
            return MoveResult.DROWNED;
        } else {
            return MoveResult.SHOT;
        }
    }

    public void markCell(Cell cell, MoveResult result) throws GameException{

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
                if (i >= 0 && i < 10 && j >=0 && j < 10) {
                    ship = getShipByCell(new Cell(i, j));
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
            ship.setCell(fieldCell);
            setShip(ship);
        } else {
            ship.setCell(fieldCell);
        }
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
            ships[index] = theShip;
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

    }

    private int getShipIndex(Ship ship) throws GameException{
        for (int i = 0; i < ships.length; i++) {
            Ship sh = ships[i];
            if (sh == ship) {
                return i;
            }
        }
        throw new GameException("The ship " + ship.toString() + " + is not found in ships");
    }

    private void setShip(Ship ship) throws GameException{
        setShip(ships, ship);
    }

    private void setShip(Ship[] ships, Ship ship) throws GameException{
        for (int i = 0; i < ships.length; i++) {
            if (ships[i] == null) {
                ships[i] = ship;
                return;
            }
        }
        throw new GameException("Couldn't set a ship!");
    }

    private Ship getShipByCell(Cell cell) {
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
                    if (i >= 0 && i < 10 && j >=0 && j < 10) {
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

    public Cell getUnHitCell(Cell hitCell) throws GameException {
        if (hitCell != null) {
            int x = hitCell.getX();
            int y = hitCell.getY();
            for (int i = x - 1; i <= x + 1; i++ ) {
                for (int j = y - 1; j <= y + 1; j++ ) {
                    if (i >= 0 && i < 10 && j >=0 && j < 10) {
                        Cell cell = cells[i][j];
                        if (!cell.isHit()) {
                            return cell;
                        }
                    }
                }
            }
            throw new GameException("No possible cells found");
        } else {
            ArrayList<Cell> possibleCells = getPossibleCells();
            if (possibleCells.size() == 0) {
                throw new GameException("No possible cells found");
            }
            int index = ShipService.getRandomNumber(0, possibleCells.size() - 1);
            return possibleCells.get(index);
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
