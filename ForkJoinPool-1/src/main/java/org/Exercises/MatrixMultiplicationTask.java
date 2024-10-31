package org.Exercises;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;
import java.util.Arrays;

// 1. MatrixMultiplicationTask class to handle matrix multiplication using RecursiveTask<int[][]>
class MatrixMultiplicationTask extends RecursiveTask<int[][]> {
    private static final int THRESHOLD = 100; // Threshold for dividing tasks
    private final int[][] A, B; // Matrices A and B
    private final int[][] result; // Result matrix C
    private final int startRow, endRow; // Row range for this task
    private final int n; // Number of columns in matrix A / rows in matrix B (shared dimension)

    // Constructor to initialize matrices and row range
    public MatrixMultiplicationTask(int[][] A, int[][] B, int[][] result, int startRow, int endRow, int n) {
        this.A = A;
        this.B = B;
        this.result = result;
        this.startRow = startRow;
        this.endRow = endRow;
        this.n = n;
    }

    // Compute method for parallel matrix multiplication
    @Override
    protected int[][] compute() {
        int rowRange = endRow - startRow;

        // Base case: if the row range is below the threshold, compute the multiplication directly
        if (rowRange <= THRESHOLD) {
            multiplyDirectly();
            return result;
        }

        // Otherwise, split the task into two smaller subtasks
        int middleRow = startRow + (rowRange / 2);

        MatrixMultiplicationTask task1 = new MatrixMultiplicationTask(A, B, result, startRow, middleRow, n);
        MatrixMultiplicationTask task2 = new MatrixMultiplicationTask(A, B, result, middleRow, endRow, n);

        // Fork the first task and compute the second task synchronously
        task1.fork();
        task2.compute();

        // Join the result of the first task
        task1.join();

        return result;
    }

    // Directly compute the multiplication for the given row range
    private void multiplyDirectly() {
        int p = B[0].length; // Number of columns in matrix B
        for (int i = startRow; i < endRow; i++) {
            for (int j = 0; j < p; j++) {
                result[i][j] = 0; // Initialize the result element
                for (int k = 0; k < n; k++) {
                    result[i][j] += A[i][k] * B[k][j]; // Dot product of row i and column j
                }
            }
        }
    }




    // Main method to execute the parallel matrix multiplication
    public static void main(String[] args) {
        // Example matrices A and B
        int[][] A = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        int[][] B = {
                {9, 8, 7},
                {6, 5, 4},
                {3, 2, 1}
        };

        int m = A.length; // Number of rows in A
        int n = A[0].length; // Number of columns in A (and rows in B)
        int p = B[0].length; // Number of columns in B

        // Result matrix C (m x p)
        int[][] result = new int[m][p];

        // Create ForkJoinPool
        ForkJoinPool pool = new ForkJoinPool();

        // Create the main MatrixMultiplicationTask
        MatrixMultiplicationTask mainTask = new MatrixMultiplicationTask(A, B, result, 0, m, n);

        // Submit the task to the pool and get the result
        pool.invoke(mainTask);

        // Print the result matrix
        System.out.println("Result matrix:");
        for (int[] row : result) {
            System.out.println(Arrays.toString(row));
        }
    }
}

