package ua.epamcourses.natalia_markova.homework.task08.subtask05.view;

import ua.epamcourses.natalia_markova.homework.task08.subtask05.model.*;

/**
 * Created by natalia_markova on 13.05.2016.
 */
public class Game {

    Player player1;
    Player player2;
    boolean isOver;

    public Game() throws ShipInitializingException{
        player1 = new Human();
        player2 = new Computer();
    }

    public Player play () throws GameException {
        Player currentPlayer = player1;
        Player secondPlayer = player2;

        MoveResult result = null;
        while (!isOver) {

            if (result == MoveResult.MISS) {
                Player p = currentPlayer;
                currentPlayer = secondPlayer;
                secondPlayer = p;
            }

            player1.viewFields();

            Cell cell = null;
            try {
                cell = currentPlayer.move();
            } catch (GameException e) {
                throw new GameException("An error occurred while making a move", e);
            }
            result = secondPlayer.getMoveResult(cell);
            try {
                currentPlayer.processResult(cell, result);
            } catch (GameException e) {
                throw new GameException("An error occurred while processing the result", e);
            }finally {
                System.out.println(result);
            }

            isOver = currentPlayer.won();
        }

        player1.viewFields();
        if (currentPlayer != player1) {
            player2.viewFields();
        }
        return currentPlayer;
    }

    public static void main(String[] args) {
        Game game = null;
        try {
            game = new Game();
        } catch (ShipInitializingException e) {
            System.out.println("Error while initializing ships!");
            e.printStackTrace();
            System.exit(0);
        }
        Player player = null;
        try {
            player = game.play();
        } catch (GameException e) {
            e.printStackTrace();
            System.exit(0);
        }
        if (player != null) {
            System.out.println("Player " + player.getName() + " won!");
        }
    }
}
