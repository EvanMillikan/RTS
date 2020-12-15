/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rts_lab.pkg3;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Bakery{
    public static void main(String[] args) {
        Timer t = new Timer();
        LinkedBlockingQueue<bun> coolingRack = new LinkedBlockingQueue<>(18);
        LinkedBlockingQueue<bun> shelf = new LinkedBlockingQueue<>(10);
        ExecutorService ex = Executors.newScheduledThreadPool(2);
        ScheduledExecutorService shed = Executors.newScheduledThreadPool(2);
        shed.scheduleAtFixedRate(new baker(coolingRack), 0, 5000, TimeUnit.MILLISECONDS);
        shed.scheduleAtFixedRate(new worker(coolingRack, shelf), 0, 1000, TimeUnit.MILLISECONDS);
                
        int customerNo = 1;
        while(true){
            try{
                Thread.sleep(1000);
            }
            catch(Exception e){}
            ex.submit(new customer(shelf, customerNo));
            customerNo++;
        }
    }
}

class bun{}

class baker extends TimerTask{
    LinkedBlockingQueue<bun> coolingRack;
    
    public baker(LinkedBlockingQueue<bun> coolingRack){
        this.coolingRack = coolingRack;
    }
    
    @Override
    public void run(){
        for (int i=1;i<13;i++){
            bun b = new bun();
            
            try{
                System.out.println("Baker: Adding bun to the cooling rack");
                coolingRack.put(b);
                Thread.sleep(10);
            }
            catch(Exception e){
                
            }
        }
    }
}

class worker extends TimerTask{
    LinkedBlockingQueue<bun> coolingRack;
    LinkedBlockingQueue<bun> shelf;
    
    public worker(LinkedBlockingQueue<bun> coolingRack, LinkedBlockingQueue<bun> shelf){
        this.coolingRack = coolingRack;
        this.shelf = shelf;
    }
    
    @Override
    public void run(){
        for (int i=1;i<5;i++){
            try{
                System.out.println("Worker: retrieve bun from the cooling rack");
                bun b = coolingRack.take();
                System.out.println("Worker: adding bun to the shelf");
                shelf.put(b);
                Thread.sleep(10);
            }
            catch(Exception e){}
        }
    }
}

class customer implements Runnable {
    LinkedBlockingQueue<bun> shelf;
    int id;
    
    public customer(LinkedBlockingQueue<bun> shelf, int id){
        this.shelf = shelf;
        this.id = id;
    }
    
    
    @Override
    public void run() {
        System.out.println("Customer " + id + ": Entered the shop. going to take a bun");
        try{
            Thread.sleep(1000);
            shelf.take();
            System.out.println("Customer " + id + ": got a bun. Leaving the shop");
        }catch(Exception e){}
    }
    
}