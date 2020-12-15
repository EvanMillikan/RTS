/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rts_lab.pkg2;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author 60182
 */
class MutexExample {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        SharedResource sr = new SharedResource();
        Example ex1 = new Example(sr, 1);
        Example ex2 = new Example(sr, 2);
        new Thread(ex1).start();
        new Thread(ex2).start();
    }
}
class SharedResource {
    int res = 0;
    public synchronized void increment(int id){
        res++;
        System.out.println("Thread " + id + " increment to " + res);
    }
}
class Example implements Runnable {
    SharedResource resource;
    int id;
    Example(SharedResource sharedResource, int id) {
        this.resource = sharedResource;
        this.id = id;
    }
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
           resource.increment(id);
        }   
    }
}
