package ua.epamcourses.natalia_markova.homework02.task05;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by natalia_markova on 28.04.2016.
 */
public class StarSystem {
    private String name;
    private List<Star> stars;

    public StarSystem(@NotNull String name) {
        this.name = name;
        this.stars = new ArrayList<>();
    }

    public StarSystem(@NotNull String name, @NotNull List<Star> stars) {
        this.name = name;
        this.stars = stars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean addStar(Star star) {
        if (stars.contains(star)) {
            return false;
        }
        return stars.add(star);
    }

    public String getStarName(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= stars.size()) {
            throw new IndexOutOfBoundsException();
        }
        return stars.get(index).toString();
    }

    public int getNumberOfPlanets() {
        return stars.size();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("STAR SYSTEM: ").append(name).append(System.getProperty("line.separator"));
        str.append("PLANETS: ");
        String separator = "";
        for (Star star : stars) {
            str.append(separator).append(star.getName());
            if (separator.isEmpty()) {
                separator = ", ";
            }
        }
        return str.toString();
    }
}
