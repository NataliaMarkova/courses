package ua.epamcourses.natalia_markova.project2.view;

import ua.epamcourses.natalia_markova.project2.controller.TextParser;
import ua.epamcourses.natalia_markova.project2.controller.TextParserImpl;
import ua.epamcourses.natalia_markova.project2.model.Text;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FilterReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by natalia_markova on 17.05.2016.
 */
public class Main {
    public static void main(String[] args) {

        String textString = "";
        String fileName = "D:\\!Java\\pratch8.txt";
//        String fileName = "D:\\!Java\\test_parser.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName));) {
            String line = "";
            while ((line = reader.readLine()) != null) {
                textString = textString + line + System.getProperty("line.separator");
            }
        } catch (IOException e) {
            System.out.println("Couldn't read file " + fileName);
            return;
        }

        TextParser parser = new TextParserImpl();
        Text text = parser.parse(textString);
//        System.out.println(text);
//        System.out.println();
        text.orderSentencesByQtyOfWords();
        System.out.println(text);
    }
}
