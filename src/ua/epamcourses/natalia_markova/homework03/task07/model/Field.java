package ua.epamcourses.natalia_markova.homework03.task07.model;

import ua.epamcourses.natalia_markova.homework03.task07.service.ShipService;

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

    public MoveResult getMoveResult(Cell cell) {
        int x = cell.getX();
        int y = cell.getY();
        cells[x][y].setIsHit(true);
        if (!cells[x][y].isPartOfAShip()) {
            return MoveResult.MISS;
        }
        Ship ship = getShipByCell(cell);
        if (ship != null) {
            if (ship.hasCell(cell)) {
                if (ship.isDrowned()) {
                    markCellsAsHit(ship);
                    return MoveResult.DROWNED;
                } else {
                    return MoveResult.SHOT;
                }
            }
        }
        return null;
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
                if (x >= 0 && x < 10 && y >=0 && y < 10) {
                    ship = getShipByCell(new Cell(x, y));
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
            ship = new Ship(getPossibleShipSize());
            ship.setCell(fieldCell);
            setShip(ship);
        }
        if (result == MoveResult.DROWNED) {
            int index = Arrays.binarySearch(ships, ship);
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
                setShip(ship1Deck, ship);
            } else if (size == 2) {
                setShip(ship2Decks, ship);
            } else if (size == 3) {
                setShip(ship3Decks, ship);
            } else if (size == 4) {
                setShip(ship4Decks, ship);
            }
            markCellsAsHit(ship);
        }

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
        throw new GameException();
    }

    private Ship getShipByCell(Cell cell) {
        for (Ship ship : ships) {
            if (ship.hasCell(cell)) {
                return ship;
            }
        }
        return null;
    }

    private void markCellsAsHit(Ship ship) {
        for (Cell cell : ship.getCells()) {
            int x = cell.getX();
            int y = cell.getY();
            for (int i = x - 1; i <= x + 1; i++ ) {
                for (int j = y - 1; j <= y + 1; j++ ) {
                    if (x >= 0 && x < 10 && y >=0 && y < 10) {
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

    private boolean hasNullShips(Ship[] ships) {
        for (Ship ship : ship3Decks) {
            if (ship == null) {
                return true;
            }
        }
        return false;
    }
}
