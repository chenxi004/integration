package com.practice.ch6.MyThreadPool;

import javax.xml.namespace.QName;
import java.util.Random;

public class TestMyThreadPool {
    public static void main(String[] args){
        MyThreadPool pool =new MyThreadPool(3,100);
        pool.execute(new MyTask("testA"));
        pool.execute(new MyTask("testB"));
        pool.execute(new MyTask("testC"));
        pool.execute(new MyTask("testD"));
        pool.execute(new MyTask("testE"));
    }

    public static class MyTask implements Runnable{
        private String name;
        public MyTask(String name){
            this.name = name;
        }
        private Random r =new Random();
        @Override
        public void run(){
            try {
                Thread.sleep(r.nextInt(1000)+2000);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getId()+" sleep InterruptedException:"
                        +Thread.currentThread().isInterrupted());
            }
            System.out.println("任务 " + name + " 完成");
        }
    }
}
