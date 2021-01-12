/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rts_lab.pkg5;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 60182
 */
public class Exercise1 {
    public static void main(String[] args) {
        LinkedBlockingQueue<Integer> sec = new LinkedBlockingQueue();
        LinkedBlockingQueue<Integer> min = new LinkedBlockingQueue();
        
        Timer t = new Timer();
        new Thread(new Display(min, sec)).start();
        t.scheduleAtFixedRate(new Seconds(sec), 0, 1000);
        t.scheduleAtFixedRate(new Minutes(min), 60000, 60000);
    }
}

class Seconds extends TimerTask{
    int sec=0;
    LinkedBlockingQueue<Integer>s;
    
    public Seconds(LinkedBlockingQueue<Integer>s){
        this.s = s;
    }

    @Override
    public void run() {
        sec++;
        try{
            s.put(sec);
        }
        catch(Exception e){}
        
        if(sec == 59){
            sec = 0;
        }
    }
}

class Minutes extends TimerTask{
  int min=0;
    LinkedBlockingQueue<Integer>m;

    public Minutes(LinkedBlockingQueue<Integer>m){
        this.m = m;
    }

    @Override
    public void run() {
        min++;
        try{
            m.put(min);
        }catch(Exception e){}
    }
}

class Display implements Runnable{
    LinkedBlockingQueue<Integer> s;
    LinkedBlockingQueue<Integer> m;
    int sec = 0;
    int min = 0;
    
    public Display(LinkedBlockingQueue<Integer> m, LinkedBlockingQueue<Integer> s){
        this.s = s;
        this.m = m;
    }
    
    @Override
    public void run() {
        while(true){
            try{
                sec = s.take();
                
                if(m.peek() != null){
                    min = m.take();
                }
                System.out.println(m + ":" + s);
            }
            catch(Exception e){}
        }
    }
    
}