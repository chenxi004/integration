package com.practice.ch2.tools;

import java.util.concurrent.CountDownLatch;

public class UseCountDownLatch {
    private static CountDownLatch countDown = new CountDownLatch(6);

    public static class InitialThread extends Thread{
        @Override
        public void run (){
            try {
                Thread.currentThread().sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDown.countDown();
            System.out.println("other initialize thread:"+getName());
        }
    }

    public static class BusinessThread extends Thread{
        @Override
        public void run(){
            System.out.println("business thread do some thins");
        }
    }

    public static void main(String[] args) throws InterruptedException{
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("1st: initialize base date");
                countDown.countDown();
                System.out.println("2nd: initialize environment");
                countDown.countDown();
            }
        }).start();
        //4个初始化线程
        for(int i=0;i<4;i++) {
            new InitialThread().start();
        }


        //Thread.currentThread().sleep(2);
        //等待闭锁
        countDown.await();
        //继续执行主线程
        new BusinessThread().start();
        System.out.println("main thread do something......");
    }
}
