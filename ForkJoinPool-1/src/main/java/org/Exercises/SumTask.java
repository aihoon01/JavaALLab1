package org.Exercises;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;
import java.util.Arrays;

// 1. SumTask class that extends RecursiveTask<Long>
class SumTask extends RecursiveTask<Long> {
    private static final int THRESHOLD = 1000; // Threshold to switch to direct sum calculation
    private final int[] array; // The array to sum up
    private final int start; // Start index
    private final int end; // End index (exclusive)

    // Constructor to initialize the array and its range (start to end)
    public SumTask(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    // 2. Implement the compute method
    @Override
    protected Long compute() {
        // Calculate the length of the current range
        int length = end - start;

        // Base case: if the length is below the threshold, sum the elements directly
        if (length <= THRESHOLD) {
            long sum = 0;
            for (int i = start; i < end; i++) {
                sum += array[i];
            }
            return sum; // Return the direct sum
        }

        // Otherwise, split the array into two halves
        int middle = start + (length / 2);

        // Create two SumTask subtasks for each half
        SumTask subTask1 = new SumTask(array, start, middle);
        SumTask subTask2 = new SumTask(array, middle, end);

        // Fork the first subtask (parallelize it) and compute the second subtask synchronously
        subTask1.fork(); // Fork the first subtask
        long sum2 = subTask2.compute(); // Compute the second subtask synchronously

        // Join the result of the first subtask (wait for it to complete)
        long sum1 = subTask1.join(); // Wait for the first subtask to finish

        // Return the sum of both subtasks
        return sum1 + sum2;
    }

    // Main method
    public static void main(String[] args) {
        // 3. Create a large array with sample data
        int size = 10_000_000; // 10 million elements
        int[] largeArray = new int[size];

        // Fill the array with some sample data (e.g., each element is 1)
        Arrays.fill(largeArray, 1); // The sum should be equal to the size of the array

        // 4. Create a SumTask for the entire array
        SumTask sumTask = new SumTask(largeArray, 0, largeArray.length);

        // 5. Create a ForkJoinPool
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        // Submit the task to the ForkJoinPool and get the final sum
        long result = forkJoinPool.invoke(sumTask);

        // Print the final result (sum of the array)
        System.out.println("Sum of array elements: " + result); // Expected result: 10,000,000
    }
}
