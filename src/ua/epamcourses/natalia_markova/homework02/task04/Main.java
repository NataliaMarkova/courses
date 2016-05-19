package ua.epamcourses.natalia_markova.homework02.task04;

/**
 * Created by natalia_markova on 28.04.2016.
 */
public class Main {

    public static void main(String[] args) {

        Ring ring = new Ring(3);
        Triangle triangle = new Triangle(3, 4, 90);
        Parallelogram parallelogram = new Parallelogram(3, 4, 30);
        Trapezium trapezium = new Trapezium(3, 4, 5, 30);

        System.out.println(ShapeUtil.sumOfShapes(ring, triangle));
        System.out.println(ShapeUtil.sumOfShapes(parallelogram, triangle));
        System.out.println(ShapeUtil.sumOfShapes(trapezium, triangle));

    }

}
