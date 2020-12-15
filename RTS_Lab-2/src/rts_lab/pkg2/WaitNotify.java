/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rts_lab.pkg2;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 60182
 */
public class WaitNotify {

    /**
     * @param args the command line arguments
     */
    static class Food {
    }
    static final Food food = new Food();

    static class Cook implements Runnable {

        @Override
        public void run() {
            try {
                synchronized (food) {
                    System.out.println("Cook is Cooking Food");
                    Thread.sleep(5000);
                    System.out.println("Cook Notifies the waiter that the food is prepared");
                    food.notify();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    static class Waiter implements Runnable {

        @Override
        public void run() {
            try {
                synchronized (food) {
                    System.out.println("Waiter is waiting for the Cook to notify about the Food");
                    food.wait();
                    System.out.println("Waiter got notification from the Cook about the Food");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new Waiter()).start();
        new Thread(new Cook()).start();
    }

}
