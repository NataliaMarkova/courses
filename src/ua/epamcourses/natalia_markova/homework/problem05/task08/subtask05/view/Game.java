package ua.epamcourses.natalia_markova.homework.problem05.task08.subtask05.view;

import ua.epamcourses.natalia_markova.homework.problem05.task08.subtask05.model.*;
import ua.epamcourses.natalia_markova.homework.problem05.task08.subtask05.service.FieldInitializer;
import ua.epamcourses.natalia_markova.homework.problem05.task08.subtask05.service.RandomFieldInitializer;

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

        FieldInitializer initializer = new RandomFieldInitializer();
        Field field1 = initializer.initializeField();
        Field field2 = initializer.initializeField();

        player1.setField(field1);
        player2.setField(field2);

        player1.setOtherField(field2);
        player2.setOtherField(field1);
    }

    public Player start() {
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
            result = currentPlayer.getMoveResult();
            System.out.println(result);

            isOver = currentPlayer.won();
        }

        player1.viewFields(true);
        return currentPlayer;
    }

    public static void main(String[] args) {
        Game game = new Game();
        Player player = game.start();
        if (player != null) {
            System.out.println("Player " + player.getName() + " won!");
        }
    }
}
