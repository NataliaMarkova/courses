package ua.epamcourses.natalia_markova.homework.problem02.task04;

/**
 * Created by natalia_markova on 28.04.2016.
 */
public class Trapezium extends Parallelogram {

    private int side3;

    public Trapezium() {
    }

    public Trapezium(int side1, int side2, int side3, int angle) {
        super(side1, side2, angle);
        this.side3 = side3;
    }

    public int getSide3() {
        return side3;
    }

    public void setSide3(int side3) {
        if (side3 > 0) {
            this.side3 = side3;
        }
    }

    @Override
    double square() {
        return side1 * Math.sin(ShapeUtil.degreesToRadians(angle)) * (side3 + Math.abs(side3 - side2) / 2);
    }
}
