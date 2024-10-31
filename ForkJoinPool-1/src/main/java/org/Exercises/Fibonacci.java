package org.Exercises;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Fibonacci extends RecursiveTask<Integer> {

    private final int n;

    public Fibonacci(int n) {
        this.n = n;
    }

    @Override
    protected Integer compute() {
        //Base Case
        if (n <= 1) return n;

        //Otherwise, split the task into subtasks
        Fibonacci f1Task = new Fibonacci(n - 1);
        Fibonacci f2Task = new Fibonacci(n - 2);

        //Fork the first task (Parallize it) and compute the second task synchronously
        f1Task.fork();
        int result2 = f2Task.compute();

        //Wait for the results of the first subtask to join and sum the results
        int result1 = f1Task.join();

        return result1 + result2;
    }


    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        //Fibonacci number to be computed
        int number =10;
        System.out.print("Fibonacci number for " + number + " is: " );

        //Create the Fibonacci task with the number defined earlier
        Fibonacci fibonacci = new Fibonacci(number);

        //Submit the task to the ForkJoinPool using invoke.
        int result = forkJoinPool.invoke(fibonacci);

        //Print the final Result
        System.out.println( result);
    }

}
