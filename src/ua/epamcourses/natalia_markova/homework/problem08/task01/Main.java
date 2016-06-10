package ua.epamcourses.natalia_markova.homework.problem08.task01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Created by natalia_markova on 10.06.2016.
 */
public class Main {
    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String expression = "";
        while (!expression.equalsIgnoreCase("exit")) {
            System.out.println("Enter expression: ");
            expression = reader.readLine();
            try {
                System.out.println(expression + " = " + Calculator.calculate(expression));
            } catch (Exception e) {
                System.out.println("Wrong expression!");
            }
        }
        reader.close();
    }
}
