/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rts_lab.pkg4;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author 60182
 */
public class RTS_Lab4 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ExecutorService es = Executors.newCachedThreadPool();
        int canID = 1;
        while(true){
            try{
                System.out.println("CAN PRODUCTION STARTED");
                Can c = new Can(canID);
                Future<Can> filledCan = es.submit(new Filling(c));
                Future<Can> sealedCan = es.submit(new Sealing(filledCan.get()));
                Future<Can> labelledCan = es.submit(new Labelling(sealedCan.get()));
                labelledCan.get(); //Need to call get so that the process is blocked until labelledCan result is acquired
                System.out.println("PROCESSING COMPLETE....");
            }
            catch(Exception e){}
        }
    }
}

class Can{
    int id;
    boolean fill = false;
    boolean seal = false;
    boolean label = false;
    
    public Can(int id){
        this.id = id;
    }
}


class Filling implements Callable<Can>{
    Can c;
    
    public Filling(Can c){
        this.c = c;
    }
    
    @Override
    public Can call() throws Exception {
        System.out.println("NEW CAN IS PASSED TO FILLING SECTION....PROCESSING");
        Thread.sleep(500);
        c.fill = true;
        System.out.println("PROCESSING COMPLETE....CAN HAS BEEN FILLED");
        return c;
    }
    
}

class Sealing implements Callable<Can>{
    Can c;
    public Sealing(Can c){
        this.c = c;
    }
    
    @Override
    public Can call() throws Exception {
        System.out.println("CAN PASSED TO SEALING SECTION....PROCESSING");
        Thread.sleep(500);
        c.seal = true;
        System.out.println("PROCESSING COMPLETE...CAN HAS BEEN SEALED");
        return c;
    }
}

class Labelling implements Callable<Can>{
    Can c;
    public Labelling(Can c){
        this.c = c;
    }

    @Override
    public Can call() throws Exception {
        System.out.println("CAN PASSED TO LABELLING SECTION....PROCESSING");
        Thread.sleep(500);
        c.label = true;
        System.out.println("PROCESSING COMPLETE....CAN HAS BEEN LABELLED");
        return c;
    }
}