package ua.epamcourses.natalia_markova.project3.model;

/**
 * Created by natalia_markova on 01.06.2016.
 */
public class Facility {
    private String value;

    public Facility(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
