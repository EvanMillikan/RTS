/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rts_lab.pkg2;

import java.util.concurrent.Semaphore;

/**
 *
 * @author Ali Ahsan Saeed
 */
public class MultiplexSemaphore {

    static Semaphore BankClerk = new Semaphore(2);

    public static void main(String[] args) {
        new Thread(new FirstPerson()).start();
        new Thread(new SecondPerson()).start();
    }

    static class FirstPerson implements Runnable {

        @Override
        public void run() {
            try {
                BankClerk.acquire();
                // run some code
                System.out.println("Bank Clerk is attending the FirstPerson");
                System.out.println("Number of Bank Clerks Available: " + BankClerk.availablePermits());
                Thread.sleep(2000);
                BankClerk.release();
                System.out.println("Bank Clerk finished serving FirstPerson");
                System.out.println("Number of Bank Clerks Available: " + BankClerk.availablePermits());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    static class SecondPerson implements Runnable {
        @Override
        public void run() {
            try {
                BankClerk.acquire();
                System.out.println("Bank Clerk Attending the SecondPerson");
                System.out.println("Number of Bank Clerks Available: " + BankClerk.availablePermits());
                Thread.sleep(4000);
                BankClerk.release();
                System.out.println("Bank Clerk finished serving SecondPerson");
                System.out.println("Number of Bank Clerks Available: " + BankClerk.availablePermits());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
