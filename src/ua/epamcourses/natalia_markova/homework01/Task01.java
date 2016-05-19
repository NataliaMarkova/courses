package ua.epamcourses.natalia_markova.homework01;

/**
 * Created by natalia_markova on 24.04.2016.
 */
public class Task01 {

    public static void main(String[] args) {
        System.out.println("byte: " + getBitsForByte());
        System.out.println("short: " + getBitsForShort());
        System.out.println("int: " + getBitsForInt());
        System.out.println("long: " + getBitsForLong());
    }

    private static int getBitsForByte() {
        byte b = 1;
        int count = 0;
        byte i = b;
        do {
            i = (byte) (i<<1);
            count++;
        } while (i != 0);

        return count;
    }

    private static int getBitsForShort() {
        short b = 1;
        int count = 0;
        short i = b;
        do {
            i = (short) (i<<1);
            count++;
        } while (i != 0);

        return count;
    }

    private static int getBitsForInt() {
        int b = 1;
        int count = 0;
        int i = b;
        do {
            i = (int) (i<<1);
            count++;
        } while (i != 0);

        return count;
    }

    private static int getBitsForLong() {
        long b = 1;
        int count = 0;
        long i = b;
        do {
            i = (long) (i<<1);
            count++;
        } while (i != 0);

        return count;
    }

}
