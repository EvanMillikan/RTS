/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rts_lab.pkg5;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 60182
 */
public class Excercise2 {
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(20);
        Train t = new Train(latch);
        new Thread(t).start();
        
        for (int i=1;i<21;i++){
            new Thread(new Passengers(t, latch)).start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Excercise2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

class Train implements Runnable {
    LinkedBlockingQueue<Passengers> capacity = new LinkedBlockingQueue();
    int i = 1;
    CountDownLatch latch;
    public Train(CountDownLatch latch){
        this.latch = latch;
    }
    
    public void enterCarriage(Passengers pas) {
        capacity.add(pas);
    }
    
    @Override
    public synchronized void run() {
        System.out.println("Train Has arrived");
        try {
            latch.await();
        } catch (InterruptedException ex) {
            Logger.getLogger(Train.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Train is full it is now departing");
    }

}

class Passengers implements Runnable {
    Train train;
    CountDownLatch latch;
    public Passengers(Train train, CountDownLatch latch){
        this.train = train;
        this.latch = latch;
    }
    
    @Override
    public void run() {
        System.out.println("Passengers has arrived");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Passengers.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Passengers Entered the carriage");
        train.enterCarriage(this);
        latch.countDown();
    }

}
