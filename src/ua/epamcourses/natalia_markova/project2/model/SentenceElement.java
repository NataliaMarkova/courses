package ua.epamcourses.natalia_markova.project2.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by natalia_markova on 17.05.2016.
 */
public class SentenceElement {
    private SentenceElementType type;
    private List<Character> characters = new ArrayList<>();

    public SentenceElement(List<Character> characters, SentenceElementType type) {
        this.characters = characters;
        this.type = type;
    }

    public int length() {
        return characters.size();
    }

    public SentenceElementType getType() {
        return type;
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
