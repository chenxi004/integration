package com.practice.ch4.rw;

import java.util.Random;

public class BusiApp {
    static UseRwLock rw = new UseRwLock();
    //static UseSyn rw = new UseSyn();

    private static class ReadThread extends Thread{
        @Override
        public void run() {
            Long start =System.currentTimeMillis();

            for(int i =0;i<100;i++){
                rw.getNumber();
            }
            System.out.println( Thread.currentThread().getName()+ "-读取商品耗时:"+(System.currentTimeMillis()-start));
        }
    }
    private static class WriteThread extends Thread{
        @Override
        public void run() {
            Long start =System.currentTimeMillis();
            Random r =new Random();
            for(int i =0;i<10;i++){
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                rw.setNumber(r.nextInt());
            }
            System.out.println( Thread.currentThread().getName()+ "-更新商品耗时:"+(System.currentTimeMillis()-start));
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for(int i=0;i<3;i++){
            WriteThread wt= new WriteThread();
            for(int j=0;j<10;j++){
                new ReadThread().start();
            }
            Thread.sleep(100);
            wt.start();
        }

    }
}
