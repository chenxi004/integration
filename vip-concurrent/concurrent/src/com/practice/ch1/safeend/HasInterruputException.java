package com.practice.ch1.safeend;

import java.lang.management.ThreadInfo;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HasInterruputException {

    private static SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private static class ThreadRun implements Runnable{
        @Override
        public void run(){
            while(!Thread.currentThread().isInterrupted()){
                try{
                    System.out.println("runnable running:"+ formater.format(new Date()));
                    Thread.sleep(1000);
                }
                catch (InterruptedException e){
                    System.out.println(Thread.currentThread().getName()+"catch interrupt exception:"
                            +Thread.currentThread().isInterrupted()
                            +"@"+formater.format(new Date()));
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }
        }
    }


    public static void main (String[] args) throws Exception{
        ThreadRun runnable = new ThreadRun();
        Thread thread = new Thread(runnable);
        thread.start();
        Thread.sleep(3000);
        thread.interrupt();
    }
}
