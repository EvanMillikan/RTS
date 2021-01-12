/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rts_lab.pkg5;

/**
 *
 * @author 60182
 */

public class RTS_Lab5 {
    static int fib(int n){
        if (n <= 1)
            return n;
        return fib(n-1) + fib(n-2);
    }
    
    public static void main(String[] args) {
        int n = 45;
        long start = System.nanoTime();
        System.out.println(fib(n));
        long end = System.nanoTime();
        System.out.println((end-start) + "ns");
    }
    
}
