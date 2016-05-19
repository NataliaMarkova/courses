package ua.epamcourses.natalia_markova.homework01;

/**
 * Created by natalia_markova on 24.04.2016.
 */
public class Task02 {

    public static void main(String[] args) {
        int number = 10;
        int pos = 2;
        System.out.println("to zero: " + number + " -> " + bitToZero(number, pos));
        System.out.println("to one: " + number + " -> " + bitToOne(number, pos));
    }

    private static int bitToZero(int num, int pos) {
        int mask = -1;
        for (int i = 0; i < pos; i++) {
            mask = mask ^ 1<<i;
        }
        for (int i = 1; i < pos; i++) {
            mask = bitToOne(mask, i);
        }

        return num & mask;
    }

    private static int bitToOne(int num, int pos) {
        return num | 1<<(pos - 1);
    }

}



