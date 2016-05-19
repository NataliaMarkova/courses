package ua.epamcourses.natalia_markova.homework03.task07.view;

import ua.epamcourses.natalia_markova.homework03.task07.model.*;

/**
 * Created by natalia_markova on 13.05.2016.
 */
public class Game {

    Player player1;
    Player player2;
    boolean isOver;

    public Game() {
        player1 = new Human();
        player2 = new Computer();
    }

    public Player play () {
        Player currentPlayer = player1;
        Player secondPlayer = player2;

        MoveResult result = null;

        while (!isOver) {

            if (result == MoveResult.MISS) {
                Player p = currentPlayer;
                currentPlayer = secondPlayer;
                secondPlayer = p;
            }

            viewFields();

            Cell cell = null;
            try {
                cell = currentPlayer.move();
            } catch (GameException e) {
                System.out.println("An error occurred");
                return null;
            }
            result = secondPlayer.getMoveResult(cell);
            try {
                currentPlayer.processResult(cell, result);
                System.out.println(result);
            } catch (GameException e) {
                System.out.println("An error occurred");
                if (!e.getMessage().isEmpty()) {
                    System.out.println(e.getMessage());
                }
                return null;
            }

            isOver = currentPlayer.won();

        }
       return currentPlayer;
    }

    private void viewFields() {
        player1.viewFields();
    }

    public static void main(String[] args) {
        Game game = new Game();
        Player player = game.play();
        if (player != null) {
            System.out.println("Player " + player.getName() + " won!");
        }
    }
}
