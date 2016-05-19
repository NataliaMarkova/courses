package ua.epamcourses.natalia_markova.classwork05;

/**
 * Created by natalia_markova on 13.05.2016.
 */

class AbstractFactory {

    BaseFactory getFactory(String color) {
        switch (color) {
            case "back":
                return new BlackCarFactory();
            case "white":
                return new WhiteCarFactory();
        }
        return null;
    }
}

abstract class BaseFactory {
    public abstract Car getCar();
}

class BlackCarFactory extends BaseFactory {

    @Override
    public Car getCar() {
        return new BlackCar();
    }
}

class WhiteCarFactory extends BaseFactory {

    @Override
    public Car getCar() {
        return new WhiteCar();
    }
}

abstract class Car {
    public abstract void move();
}

class BlackCar extends Car {

    @Override
    public void move() {

    }
}

class WhiteCar extends Car {

    @Override
    public void move() {

    }
}

// pattern Factory
class Factory {
    public Car getCar(String color) {
        switch (color) {
            case "white" : return new WhiteCar();
            case "black" : return new BlackCar();
        }
        return null;
    }
}

public class AbsractFactoryPattern {
    public static void main(String[] args) {
        AbstractFactory abstractFactory = new AbstractFactory();
        BaseFactory baseFactory = abstractFactory.getFactory("black");
        Car car = baseFactory.getCar();
        car.move();
    }
}




