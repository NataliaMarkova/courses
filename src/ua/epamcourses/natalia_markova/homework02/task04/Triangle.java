package ua.epamcourses.natalia_markova.homework02.task04;

/**
 * Created by natalia_markova on 28.04.2016.
 */
public class Triangle extends Shape {
    private int side1;
    private int side2;
    private int angle;

    public Triangle() {
    }

    public Triangle(int side1, int side2, int angle) {
        setSide1(side1);
        setSide2(side2);
        setAngle(angle);
    }

    public int getSide1() {
        return side1;
    }

    public void setSide1(int side1) {
        if (side1 > 0) {
            this.side1 = side1;
        }
    }

    public int getSide2() {
        return side2;
    }

    public void setSide2(int side2) {
        if (side2 > 0) {
            this.side2 = side2;
        }
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        if (angle > 0 && angle < 180) {
            this.angle = angle;
        }
    }

    @Override
    double square() {
        return side1 * side2 * Math.sin(ShapeUtil.degreesToRadians(angle)) / 2;
    }
}
