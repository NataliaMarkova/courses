package ua.epamcourses.natalia_markova.project3.model;

import com.sun.istack.internal.NotNull;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by natalia_markova on 31.05.2016.
 */
public class TouristVoucher {
    private int id;
    private VoucherType type;
    private TransportType transportType;
    private Room room;
    private int days;
    private int nights;
    private double price;
    private Set<String> includes = new HashSet<>();

    public TouristVoucher(int id) {
        this.id = id;
    }

    public static class OrderById implements Comparator<TouristVoucher> {
        @Override
        public int compare(TouristVoucher o1, TouristVoucher o2) {
            return o1.getId() - o2.getId();
        }
    }

    public int getId() {
        return id;
    }

    public VoucherType getType() {
        return type;
    }

    public void setType(@NotNull VoucherType type) {
        this.type = type;
    }

    public TransportType getTransportType() {
        return transportType;
    }

    public void setTransportType(@NotNull TransportType transportType) {
        this.transportType = transportType;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getNights() {
        return nights;
    }

    public void setNights(int nights) {
        this.nights = nights;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Set<String> getIncludes() {
        return includes;
    }

    public void addIncludes(String include) {
        includes.add(include);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TouristVoucher that = (TouristVoucher) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "TouristVoucher{" +
                "id=" + id +
                ", type=" + type +
                ", transportType=" + transportType +
                ", room=" + room +
                ", days=" + days +
                ", nights=" + nights +
                ", price=" + price +
                ", includes=" + includes +
                '}';
    }
}
