package ua.epamcourses.natalia_markova.classwork05;

/**
 * Created by natalia_markova on 13.05.2016.
 */

class Foreman {
    Builder builder;

    public Foreman(Builder builder) {
        this.builder = builder;
    }
    public void build() {
        builder.buildBasement();
        builder.buildWalls();
        builder.buildRoof();
    }
}

abstract class Builder {
    House house;
    public abstract void buildBasement();
    public abstract void buildWalls();
    public abstract void buildRoof();
    public House getHouse() {
        return house;
    }
}

class CottageBuilder extends Builder {

    public CottageBuilder() {
        house = new Cottage();
    }

    @Override
    public void buildBasement() {
        house.setBasement();
    }

    @Override
    public void buildWalls() {
        house.setWalls();
    }

    @Override
    public void buildRoof() {
        house.setRoof();
    }

    @Override
    public House getHouse() {
        return house;
    }
}

abstract class House {
    public abstract void setBasement();
    public abstract void setWalls();
    public abstract void setRoof();
}

class Cottage extends House {

    @Override
    public void setBasement() {
        System.out.println("Basement");
    }

    @Override
    public void setWalls() {
        System.out.println("Walls");
    }

    @Override
    public void setRoof() {
        System.out.println("Roof");
    }
}

public class BuilderPattern {
    public static void main(String[] args) {
        Builder builder = new CottageBuilder();
        Foreman foreman = new Foreman(builder);
        foreman.build();
        House house = builder.getHouse();
    }
}
