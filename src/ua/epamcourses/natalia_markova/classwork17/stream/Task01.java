package ua.epamcourses.natalia_markova.classwork17.stream;

import java.util.Arrays;
import java.util.List;

/**
 * Created by natalia_markova on 10.06.2016.
 */
public class Task01 {

    public static void main(String[] args) {
        List<String> myList = Arrays.asList("a1", "a2", "c1", "c2");
        myList.stream()
                .filter(s -> s.startsWith("c"))
                .map(String::toUpperCase) // map принимает функцию и список, и для каждого элемента списка применяет указанную функцию
                .sorted()
                .forEach(System.out::println);
    }



}
