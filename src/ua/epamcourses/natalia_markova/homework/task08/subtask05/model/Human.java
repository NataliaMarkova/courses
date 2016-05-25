package ua.epamcourses.natalia_markova.homework.task08.subtask05.model;

import ua.epamcourses.natalia_markova.homework.task08.subtask05.service.GameException;
import ua.epamcourses.natalia_markova.homework.task08.subtask05.service.ShipInitializingException;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by natalia_markova on 13.05.2016.
 */
public class Human extends Player {

    public Human() {
        super();
        setName("Human");
    }

    @Override
    public Cell move() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Cell cell = null;
        String letters = "abcdefghij";
        while (cell == null) {
            System.out.println("Your move: ");
            try {
                String input = reader.readLine();
                input = input.toLowerCase();
                if (input.matches("[a-j]([1-9]|10)")) {
                    int x = letters.indexOf(input.charAt(0));
                    int y = Integer.valueOf(input.substring(1)) - 1;
                    return new Cell(x, y);
                }
            } catch (Exception e) {
                throw new GameException(e);
            }
        }
        return cell;
    }
}
