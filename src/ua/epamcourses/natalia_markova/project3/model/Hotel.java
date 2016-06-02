package ua.epamcourses.natalia_markova.project3.model;

import com.sun.istack.internal.NotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by natalia_markova on 02.06.2016.
 */
public class Hotel {
    private String name;
    private Country country;
    private String type;
    private Set<Facility> facilities = new HashSet<>();

    public Hotel(@NotNull String name, @NotNull Country country) {
        this.name = name;
        this.country = country;
    }

    public Hotel(@NotNull String name, @NotNull Country country, @NotNull String type) {
        this.name = name;
        this.country = country;
        setType(type);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if (type.matches("\\*{1,5}")) {
            this.type = type;
        }
    }

    public Set<Facility> getFacilities() {
        return facilities;
    }

    public boolean addFacility(Facility facility) {
        return facilities.add(facility);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hotel hotel = (Hotel) o;

        if (!name.equals(hotel.name)) return false;
        if (!country.equals(hotel.country)) return false;
        return !(type != null ? !type.equals(hotel.type) : hotel.type != null);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + country.hashCode();
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}

