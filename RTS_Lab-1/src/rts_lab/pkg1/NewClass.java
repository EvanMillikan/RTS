/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rts_lab.pkg1;

/**
 *
 * @author 60182
 */
public class NewClass {

    // Iterative
    static void Fibonacci(int N) {
        int num1 = 0, num2 = 1;
        int counter = 0;
        while (counter < N) {
            System.out.println(num1 + " ");
            int num3 = num2 + num1;
            num1 = num2;
            num2 = num3;
            counter = counter + 1;
        }
    }

    // Recursion
    static int fib(int n) {
        if (n <= 1) {
            return n;
        }
        return fib(n - 1) + fib(n - 2);
    }

    // Dyanmic Programming
    static int fib2(int n) {
        int f[] = new int[n + 2];
        int i;
        f[0] = 0;
        f[1] = 1;

        for (i = 2; i <= n; i++) {
            f[i] = f[i - 1] + f[i - 2];
        }

        return f[n];
    }

    public static void main(String[] args) {
        int N = 40;
        long startTime = System.currentTimeMillis();
        int test = fib2(39);
        long finishTime = System.currentTimeMillis();
        System.out.println(test);
        System.out.println("Total time:" + (finishTime - startTime) + " ms");
    }
}
