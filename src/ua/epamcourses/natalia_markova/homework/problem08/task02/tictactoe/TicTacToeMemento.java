package ua.epamcourses.natalia_markova.homework.problem08.task02.tictactoe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Created by natalia_markova on 12.06.2016.
 */

class TicTacToe {

    private Player player1;
    private Player player2;

    public TicTacToe(String name1, String name2) {
        Field field = new Field();
        player1 = new Player(name1, true, field);
        player2 = new Player(name2, false, field);
    }

    public void play() throws IOException {

        Player currentPlayer = player1;
        Player secondPlayer = player2;

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Result gameResult = null;

        while ((gameResult = currentPlayer.result()) == Result.CONTINUE) {

            Player temp = currentPlayer;
            currentPlayer = secondPlayer;
            secondPlayer = temp;

            currentPlayer.move();
            currentPlayer.printField();

            String result = "";
            while (!result.matches("y|n|Y|N")) {
                System.out.println("Do you want to cancel your move? y/n");
                result = reader.readLine();
            }

            if (result.equalsIgnoreCase("y")) {
                currentPlayer.cancelMove();
                currentPlayer.printField();

                temp = currentPlayer;
                currentPlayer = secondPlayer;
                secondPlayer = temp;
            }
        }

        if (gameResult == Result.TIE) {
            System.out.println("Tie!");
        } else {
            System.out.println("Player " + currentPlayer + " won!");
        }

    }

}

class Field {
    private char [][] data = new char[3][3];
    private Integer lastMove;

    public Field() {
        for (int i = 0; i <3 ; i ++) {
            for (int j = 0; j < 3; j++) {
                data[i][j] = ' ';
            }
            System.out.println();
        }
    }

    public void print() {
        for (int i = 0; i <3 ; i ++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(data[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    public boolean hasLine(char symbol) {

        boolean right = true;
        boolean left = true;

        for (int i = 0; i < 3; i++) {
            if (data[i][i] != symbol) {
                right = false;
            }
            if (data[i][2 - i] != symbol) {
                left = false;
            }
            boolean horizontal = true;
            boolean vertical = true;
            for (int j = 0; j < 3; j++) {
                if (data[i][j] != symbol) {
                    horizontal = false;
                }
                if (data[j][i] != symbol) {
                    vertical = false;
                }
                if (!horizontal && !vertical) {
                    break;
                }

            }
            if (horizontal || vertical) {
                return true;
            }
        }
        if (right || left) {
            return true;
        }
        return false;
    }

    public void processMove(int move, char symbol) {
        lastMove = move;
        update(move, symbol);
    }

    public char getSymbol(int move) {
        int j = move % 10 - 1;
        int i = (move - j) / 10 - 1;
        return data[i][j];
    }

    public void cancelLastMove() {
        if (lastMove == null) {
            return;
        }
        update(lastMove, ' ');
        lastMove = null;
    }

    public boolean hasFreeCells() {
        for (int i = 0; i <3 ; i ++) {
            for (int j = 0; j < 3; j++) {
                if (data[i][j] == ' ') {
                    return true;
                }
            }
        }
        return false;
    }

    private void update(int move, char symbol) {
        int j = move % 10 - 1;
        int i = (move - j) / 10 - 1;
        data[i][j] = symbol;
    }

}

class Player {

    private String name;
    private Field field;
    char symbol; // true == X, false == 0

    public Player(String name, boolean symbol, Field field) {
        this.name = name;
        if (symbol) {
            this.symbol = 'X';
        } else {
            this.symbol = '0';
        }
        this.field = field;
    }

    public void move() throws IOException {
        int move = -1;
        while (true) {
            move = getMove();
            char ch = field.getSymbol(move);
            if (ch == ' ') {
                break;
            }
        }
        field.processMove(move, symbol);
    }

    public void cancelMove() {
        field.cancelLastMove();
    }

    private int getMove() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String res = "";
        while (true) {
            System.out.println(name + ": ");
            res = reader.readLine();
            if (res.matches("[1-3]{2}")) {
                break;
            }
        }
        return Integer.valueOf(res);
    }

    public Result result() {

        if (field.hasLine(symbol)) {
            return Result.VICTORY;
        } else if (field.hasFreeCells()) {
            return Result.CONTINUE;
        } else {
            return Result.TIE;
        }

    }

    public void printField() {
        field.print();
    }

    @Override
    public String toString() {
        return name;
    }
}

enum Result {
    VICTORY, TIE, CONTINUE
}

public class TicTacToeMemento {

    public static void main(String[] args) throws IOException {
        TicTacToe game = new TicTacToe("Mia", "natalia");
        game.play();
    }
}
