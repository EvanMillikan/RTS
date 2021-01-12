/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rts_lab.pkg2;

import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author 60182
 */
public class Stopwatch {
    public static void main(String[] args) {
        LinkedBlockingQueue<Integer> s = new LinkedBlockingQueue();
        
    }
}

class seconds extends TimerTask{
    int sec=0;
    LinkedBlockingQueue<Integer>s;
    
    public seconds(LinkedBlockingQueue<Integer>s){
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

class minutes extends TimerTask{
  int min=0;
    LinkedBlockingQueue<Integer>m;

    public minutes(LinkedBlockingQueue<Integer>m){
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

class display implements Runnable{
    LinkedBlockingQueue<Integer> s;
    LinkedBlockingQueue<Integer> m;
    int sec = 0;
    int min = 0;
    
    public display(LinkedBlockingQueue<Integer> m, LinkedBlockingQueue<Integer> s){
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