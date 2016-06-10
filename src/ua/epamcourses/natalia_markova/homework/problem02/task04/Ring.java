package ua.epamcourses.natalia_markova.homework.problem02.task04;

/**
 * Created by natalia_markova on 28.04.2016.
 */
public class Ring extends Shape {

    private int radius;

    public Ring() {
    }

    public Ring(int radius) {
        setRadius(radius);
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        if (radius >= 0) {
            this.radius = radius;
        }
    }

    @Override
    double square() {
        return Math.PI * radius * radius;
    }
}
