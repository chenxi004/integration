package com.practice.ch1.safeend;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EndRunnable {
    private static SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    private static class ThreadRun implements Runnable{
        @Override
        public void run(){
            while(!Thread.currentThread().isInterrupted()){
                System.out.println(Thread.currentThread().getName()+"runnable running at:" +formater.format(new Date()));
            }
            System.out.println(Thread.currentThread().getName()+"end Thread at:" +formater.format(new Date()));
        }
    }
    public static void main(String[] args) throws Exception{
        ThreadRun runnable = new ThreadRun();
        Thread thread =new Thread(runnable);
        thread.start();
        Thread.sleep(1000);
        thread.interrupted();//终端之后清空中断标志位
        thread.interrupt();
    }
}
