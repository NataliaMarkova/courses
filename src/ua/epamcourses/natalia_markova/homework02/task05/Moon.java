package ua.epamcourses.natalia_markova.homework02.task05;

import com.sun.istack.internal.NotNull;

/**
 * Created by natalia_markova on 28.04.2016.
 */
public class Moon {
    private String name;

    public Moon(@NotNull String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Moon moon = (Moon) o;

        return !(name != null ? !name.equals(moon.name) : moon.name != null);

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return name;
    }
}
