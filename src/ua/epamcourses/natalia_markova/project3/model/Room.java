package ua.epamcourses.natalia_markova.project3.model;

import com.sun.istack.internal.NotNull;

/**
 * Created by natalia_markova on 02.06.2016.
 */
public class Room {
    private Hotel hotel;
    private String type;
    private NutritionType nutrition;

    public Room(@NotNull Hotel hotel, @NotNull String type) {
        this.hotel = hotel;
        this.type = type;
    }

    public Room(@NotNull Hotel hotel, @NotNull String type, @NotNull NutritionType nutrition) {
        this.hotel = hotel;
        this.type = type;
        this.nutrition = nutrition;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(@NotNull Hotel hotel) {
        this.hotel = hotel;
    }

    public NutritionType getNutrition() {
        return nutrition;
    }

    public void setNutrition(@NotNull NutritionType nutrition) {
        this.nutrition = nutrition;
    }

    public String getType() {
        return type;
    }

    public void setType(@NotNull String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        if (!hotel.equals(room.hotel)) return false;
        if (!type.equals(room.type)) return false;
        return nutrition == room.nutrition;

    }

    @Override
    public int hashCode() {
        int result = hotel.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + (nutrition != null ? nutrition.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Room{" +
                "hotel=" + hotel +
                ", type='" + type + '\'' +
                ", nutrition=" + nutrition +
                '}';
    }
}
