package ua.epamcourses.natalia_markova.homework.task08.subtask05.view;

import ua.epamcourses.natalia_markova.homework.task08.subtask05.model.*;
import ua.epamcourses.natalia_markova.homework.task08.subtask05.service.GameException;
import ua.epamcourses.natalia_markova.homework.task08.subtask05.service.ShipInitializingException;

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

            player1.viewFields();

            Cell cell = currentPlayer.move();
            result = secondPlayer.getMoveResult(cell);
            currentPlayer.processResult(cell, result);
            System.out.println(result);

            isOver = currentPlayer.won();
        }

        player1.viewFields();
        if (currentPlayer != player1) {
            player2.viewFields();
        }
        return currentPlayer;
    }

    public static void main(String[] args) {
        Game game = new Game();
        Player player = game.play();
        if (player != null) {
            System.out.println("Player " + player.getName() + " won!");
        }
    }
}
