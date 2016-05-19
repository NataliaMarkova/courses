package ua.epamcourses.natalia_markova.project1.model;

import com.sun.istack.internal.NotNull;

/**
 * Created by natalia_markova on 26.04.2016.
 */
public class Performer {
    private String name;

    public Performer(@NotNull String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Performer performer = (Performer) o;

        return !(name != null ? !name.equals(performer.name) : performer.name != null);

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
