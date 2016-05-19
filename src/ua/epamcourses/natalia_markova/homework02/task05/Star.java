package ua.epamcourses.natalia_markova.homework02.task05;

import com.sun.istack.internal.NotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by natalia_markova on 28.04.2016.
 */
public class Star {
    private String name;
    private Set<Moon> moons;

    public Star(@NotNull String name) {
        this.name = name;
        moons = new HashSet<>();
    }

    public Star(@NotNull String name, @NotNull Set<Moon> moons) {
        this.name = name;
        this.moons = moons;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Moon> getMoons() {
        return moons;
    }

    public void setMoons(Set<Moon> moons) {
        this.moons = moons;
    }

    public boolean addMoon(Moon moon) {
        return moons.add(moon);
    }

    public boolean removeMoon(Moon moon) {
        return moons.remove(moon);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Star star = (Star) o;

        return !(name != null ? !name.equals(star.name) : star.name != null);

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(name);
        if (moons.size() != 0) {
            String separator = "";
            str.append(", moons: ");
            for (Moon moon: moons) {
                str.append(separator).append(moon);
                if (separator.isEmpty()) {
                    separator = ", ";
                }
            }
        }
        return str.toString();
    }
}
