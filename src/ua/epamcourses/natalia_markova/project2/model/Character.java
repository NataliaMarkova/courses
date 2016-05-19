package ua.epamcourses.natalia_markova.project2.model;

/**
 * Created by natalia_markova on 17.05.2016.
 */
public class Character {

    private char ch;

    public Character(char ch) {
        this.ch = ch;
    }

    public char getCh() {
        return ch;
    }

    @Override
    public String toString() {
        return String.valueOf(ch);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Character character = (Character) o;
        return ch == character.ch;
    }

    @Override
    public int hashCode() {
        return (int) ch;
    }

}
