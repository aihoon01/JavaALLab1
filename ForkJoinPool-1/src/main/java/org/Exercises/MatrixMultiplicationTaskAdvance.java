package org.Exercises;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;
import java.util.Arrays;

// 1. MatrixMultiplicationTask handles sub-matrix multiplication in parallel
class MatrixMultiplicationTaskAdvance extends RecursiveTask<int[][]> {
    private static final int THRESHOLD = 100; // Threshold for sub-matrix size
    private final int[][] A, B, result; // Matrices A, B, and result C
    private final int rowStartA, rowEndA; // Row range in matrix A
    private final int colStartA, colEndA; // Column range in matrix A
    private final int rowStartB, colStartB; // Starting point of B submatrix

    // Constructor to initialize matrix ranges and result
    public MatrixMultiplicationTaskAdvance(int[][] A, int[][] B, int[][] result,
                                           int rowStartA, int rowEndA, int colStartA, int colEndA,
                                           int rowStartB, int colStartB) {
        this.A = A;
        this.B = B;
        this.result = result;
        this.rowStartA = rowStartA;
        this.rowEndA = rowEndA;
        this.colStartA = colStartA;
        this.colEndA = colEndA;
        this.rowStartB = rowStartB;
        this.colStartB = colStartB;
    }

    @Override
    protected int[][] compute() {
        int rowsA = rowEndA - rowStartA;
        int colsA = colEndA - colStartA;
        int colsB = B[0].length;

        // Base case: if submatrix size is small enough, compute directly
        if (rowsA <= THRESHOLD || colsA <= THRESHOLD) {
            multiplySubmatrices();
            return result;
        }

        // Otherwise, split the submatrices and create subtasks
        int rowMidA = rowStartA + rowsA / 2;
        int colMidA = colStartA + colsA / 2;

        MatrixMultiplicationTaskAdvance task1 = new MatrixMultiplicationTaskAdvance(A, B, result,
                rowStartA, rowMidA, colStartA, colMidA, rowStartB, colStartB);

        MatrixMultiplicationTaskAdvance task2 = new MatrixMultiplicationTaskAdvance(A, B, result,
                rowMidA, rowEndA, colMidA, colEndA, rowStartB, colStartB);

        // Fork tasks
        task1.fork();
        task2.compute();

        task1.join();

        return result;
    }

    // Multiply the two submatrices
    private void multiplySubmatrices() {
        int colsB = B[0].length; // Number of columns in matrix B
        for (int i = rowStartA; i < rowEndA; i++) {
            for (int j = colStartA; j < colEndA; j++) {
                result[i][j] = 0; // Initialize element
                for (int k = 0; k < colsB; k++) {
                    result[i][j] += A[i][k] * B[k][j];
                }
            }
        }
    }

    public static void main(String[] args) {
        // Define matrices A and B
        int[][] A = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };

        int[][] B = {
                {16, 15, 14, 13},
                {12, 11, 10, 9},
                {8, 7, 6, 5},
                {4, 3, 2, 1}
        };

        int size = A.length;
        int[][] result = new int[size][size];

        // Create ForkJoinPool
        ForkJoinPool pool = new ForkJoinPool();

        // Submit the main task to the ForkJoinPool
        MatrixMultiplicationTaskAdvance task = new MatrixMultiplicationTaskAdvance(A, B, result,
                0, size, 0, size, 0, 0);

        pool.invoke(task);

        // Print the result matrix
        System.out.println("Result matrix:");
        for (int[] row : result) {
            System.out.println(Arrays.toString(row));
        }
    }
}
