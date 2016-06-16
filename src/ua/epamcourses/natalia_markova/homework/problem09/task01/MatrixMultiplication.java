package ua.epamcourses.natalia_markova.homework.problem09.task01;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by natalia_markova on 14.06.2016.
 */

class MultiplyRunnable implements Runnable {

    private int[][] matrixA;
    private int[][] matrixB;
    private int line;
    private int result[];

    public MultiplyRunnable(int[][] matrixA, int[][] matrixB, int line) {
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.line = line;
    }

    public int[] getResult() {
        return result;
    }

    public int getLine() {
        return line;
    }

    @Override
    public void run() {
        result = new int[matrixB[0].length];
        for (int j = 0; j < matrixB[0].length; j++) {
            result[j] = getValueFor(j);
        }
    }

    private int getValueFor(int index) {
        int result = 0;
        for (int i = 0; i < matrixA[0].length; i++) {
            result = result + matrixA[line][i]*matrixB[i][index];
        }
        return result;
    }
}

public class MatrixMultiplication {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int m = 3;
        int n = 5;
        int k = 4;
        int[][] matrixA = initializeMatrix(m, n);
        int[][] matrixB = initializeMatrix(n, k);

        System.out.println("Matrix A:");
        printMatrix(matrixA);
        System.out.println();

        System.out.println("Matrix B:");
        printMatrix(matrixB);
        System.out.println();

        int[][] matrixAB = multiply(matrixA, matrixB);

        System.out.println("Matrix AxB:");
        printMatrix(matrixAB);
        System.out.println();

    }

    private static int[][] multiply(int[][] matrixA, int[][] matrixB) throws InterruptedException {
        int m = matrixA.length;
        int k = matrixB[0].length;

        int[][] matrixAB = new int[m][k];
        List<MultiplyRunnable> runnables = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            MultiplyRunnable runnable = new MultiplyRunnable(matrixA, matrixB, i);
            runnables.add(runnable);
            Thread thread = new Thread(runnable);
            thread.start();
            thread.join();
        }

        for (MultiplyRunnable runnable: runnables) {
            int i = runnable.getLine();
            int[] result = runnable.getResult();
            for (int j = 0; j< result.length; j++) {
                matrixAB[i][j] = result[j];
            }
        }
        return matrixAB;
    }

    private static int[][] initializeMatrix(int m, int n) {
        int[][] matrix = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = getRandomNumber(0, 5);
            }
        }
        return matrix;
    }

    private static int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    private static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }
    }

}
