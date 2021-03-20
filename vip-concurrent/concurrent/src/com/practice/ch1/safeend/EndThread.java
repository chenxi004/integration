package com.practice.ch1.safeend;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EndThread {
    private static SimpleDateFormat formater =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private static class ThreadEnd extends Thread{
        @Override
        public void run(){
            while(!isInterrupted()){
                System.out.println(getName()+"thread is running at" +formater.format(new Date()));
            }
            System.out.println(getName()+"thread end at" +formater.format(new Date()));
        }
    }

    public static void main(String[] args) throws Exception{
        ThreadEnd threadend = new ThreadEnd();
        threadend.start();
        threadend.sleep(1000);
        threadend.interrupt();
    }
}
