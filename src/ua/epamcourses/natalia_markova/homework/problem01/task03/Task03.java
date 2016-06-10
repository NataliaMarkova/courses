package ua.epamcourses.natalia_markova.homework.problem01.task03;

/**
 * Created by natalia_markova on 24.04.2016.
 */
public class Task03 {

    public static final int LIMIT = 3;

    public static void main(String[] args) {

        long a = 123474545L;
        long b = 237354565L;

        System.out.println(a * b);
        System.out.println(karatsubaMultiplication(a, b));

    }

    public static long karatsubaMultiplication(long a, long b) {

        int k = Math.max(numberLength(a), numberLength(b));
        if (k%2 == 1) {
            k++;
        }
        k = k>>1;
        if (k <= LIMIT) {
            return a * b;
        } else {
            long a1 = getFirstHalf(a, k);
            long b1 = getFirstHalf(b, k);
            long a2 = getSecondHalf(a, k);
            long b2 = getSecondHalf(b, k);

            long a1b1 = karatsubaMultiplication(a1, b1);
            long a2b2 = karatsubaMultiplication(a2, b2);
            long a1_b2 = karatsubaMultiplication(a1, b2);
            long a2_b1 = karatsubaMultiplication(a2, b1);

            return shift(a1b1, 2 * k) + shift(a1_b2 + a2_b1, k) + a2b2;
        }
    }

    private static int numberLength(long number) {
        return String.valueOf(number).length();
    }

    private static long shift(long number, int pos) {
//        String res = String.valueOf(number);
//        for (int i = 0; i < pos; i++) {
//            res = res + "0";
//        }
        return number * (long) Math.pow(10, pos);
    }

    private static long getFirstHalf(long number, int k) {
        String res = fillLength(String.valueOf(number), k<<1) ;
        return Long.parseLong(res.substring(0, k));
    }

    private static long getSecondHalf(long number, int k) {
        String res = fillLength(String.valueOf(number), k<<1) ;
        return Long.parseLong(res.substring(k));
    }

    private static String fillLength(String str, int len) {
        while (str.length() < len) {
            str = "0" + str;
        }
        return str;
    }

}






