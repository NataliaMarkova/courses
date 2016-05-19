package ua.epamcourses.natalia_markova.project1.model;

import com.sun.istack.internal.NotNull;

import java.util.Calendar;

/**
 * Created by natalia_markova on 26.04.2016.
 */
public class Album {

    private String name;
    private int year;

    public Album(@NotNull String name) {
        this.name = name;
    }

    public Album(@NotNull String name, Performer performer, int year) {
        this.name = name;
        setYear(year);
    }

    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        if (yearIsValid(year)) {
            this.year = year;
        }
    }

    private boolean yearIsValid(int year) {
        return (year >= 1000 && year <= Calendar.getInstance().get(Calendar.YEAR));
    }

    @Override
    public String toString() {
        return name + (year == 0 ? "" : ", " + year);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Album album = (Album) o;

        if (year != album.year) return false;
        return (name != null ? name.equals(album.name) : album.name == null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + year;
        return result;
    }
}
