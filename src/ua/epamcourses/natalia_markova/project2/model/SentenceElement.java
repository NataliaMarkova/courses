package ua.epamcourses.natalia_markova.project2.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by natalia_markova on 17.05.2016.
 */
public abstract class SentenceElement {
    private List<Character> characters = new ArrayList<>();

    public SentenceElement(List<Character> characters) {
        this.characters = characters;
    }

    public int length() {
        return characters.size();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Character character : characters) {
            str.append(character.toString());
        }
        return str.toString();
    }
}
