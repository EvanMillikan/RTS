/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rts_lab.pkg1;

import java.util.*;

/**
 *
 * @author 60182
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    static int sec = 0;
    static int min = 0;

    static class Seconds extends TimerTask {

        public void run() {
            sec++;
            if (sec == 60) {
                sec = 0;
            }
        }
    }

    static class Minutes extends TimerTask {

        public void run() {
            min++;
        }
    }

    static class Display extends TimerTask {

        public void run() {
            System.out.println(min + ":" + sec);
        }
    }

    public static void main(String[] args) {        
        Timer t = new Timer();
        
        
        t.scheduleAtFixedRate(new Minutes(), 60000, 60000);
        t.scheduleAtFixedRate(new Seconds(), 1000, 1000);
        t.scheduleAtFixedRate(new Display() , 0, 1000);
        
        

    }

}
