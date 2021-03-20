package com.practice.ch1.vola;

import java.util.concurrent.TimeUnit;

public class VolatileUnsafe {

    private static volatile  int common_age =0;
    private static class ThreadRun implements Runnable{
        private volatile int age=0;
        @Override
        public void run (){
               try {
                   age =age++;
                    System.out.println(Thread.currentThread().getName()+"===="+age);
                    TimeUnit.MILLISECONDS.sleep(100);
                    age = age+1;
                    System.out.println(Thread.currentThread().getName()+":======"+age);
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }

        }
    }

    private static class ThreadRunCom implements Runnable{
        @Override
        public void run (){
            try {
               increase();
               System.out.println(Thread.currentThread().getName()+"===="+common_age);
            }
            catch(Exception e){
                e.printStackTrace();
            }

        }
    }
    private static void increase(){
        common_age++;
    }

    public static void main(String[] args) throws Exception{

//        ThreadRun v = new ThreadRun();
//        //ע������runnable�õ���ͳһ�� �������ǲ���ͬһ��volatile����
//        Thread thread1 = new Thread(v);
//        Thread thread2 = new Thread(v);
//        Thread thread3 = new Thread(v);
//        Thread thread4 = new Thread(v);
//        thread1.start();
//        thread2.start();
//        thread3.start();
//        thread4.start();

        //volatile ������runnable������棬������ͬ��runnableʵ�������߳� �����ı���commone_age�ǹ����
        ThreadRunCom v1 = new ThreadRunCom();
        ThreadRunCom v2 = new ThreadRunCom();
        ThreadRunCom v3 = new ThreadRunCom();
        ThreadRunCom v4 = new ThreadRunCom();
        Thread threadcom1 = new Thread(v1);
        Thread threadcom2 = new Thread(v2);
        Thread threadcom3 = new Thread(v3);
        Thread threadcom4 = new Thread(v4);
        threadcom1.start();
        threadcom2.start();
        threadcom3.start();
        threadcom4.start();
    }
}
