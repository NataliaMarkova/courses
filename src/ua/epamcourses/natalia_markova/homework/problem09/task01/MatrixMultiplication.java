package ua.epamcourses.natalia_markova.homework.problem09.task01;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by natalia_markova on 14.06.2016.
 */

class UnitOperation implements Callable<Integer> {
    private int a;
    private int b;
    private int[][] matrixA;
    private int[][] matrixB;

    public UnitOperation(int[][] matrixA, int[][] matrixB, int a, int b) {
        this.a = a;
        this.b = b;
        this.matrixA = matrixA;
        this.matrixB = matrixB;
    }

    @Override
    public Integer call() {
        int result = 0;
        for (int i = 0; i < matrixA[0].length; i++) {
                result = result + matrixA[a][i]*matrixB[i][b];
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

    private static int[][] multiply(int[][] matrixA, int[][] matrixB) throws InterruptedException, ExecutionException {

        int m = matrixA.length;
        int k = matrixB[0].length;

        int[][] matrixAB = new int[m][k];
        ExecutorService executor = Executors.newFixedThreadPool(m*k);
        List<Future<Integer>> futures = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < k; j++) {
                UnitOperation unitOperation = new UnitOperation(matrixA, matrixB, i, j);
                Future<Integer> future = executor.submit(unitOperation);
                futures.add(future);
            }
        }

        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        for (int index = 0; index < futures.size(); index++) {
            Future<Integer> future = futures.get(index);
            int i = index % m;
            int j = index % k;
            matrixAB[i][j] = future.get();
        }

        return matrixAB;
    }

    private static int[][] initializeMatrix(int m, int n) {
        int[][] matrix = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = getRandomNumber(0, 10);
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
