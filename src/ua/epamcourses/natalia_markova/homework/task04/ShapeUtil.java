package ua.epamcourses.natalia_markova.homework.task04;

/**
 * Created by natalia_markova on 28.04.2016.
 */
public class ShapeUtil {

    private ShapeUtil() {
    }

    public static double sumOfShapes(Shape shape1, Shape shape2) {
        return shape1.square() + shape2.square();
    }

    public static double degreesToRadians(int degrees) {
       return Math.toRadians(degrees);
    }
}
