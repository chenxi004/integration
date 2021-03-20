package com.practice.ch2.tools;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;

public class UseCyclicBarrier {
    private static CyclicBarrier barrier = new CyclicBarrier(4,new BarrierAction());
    private static ConcurrentHashMap<Long,String> map =new ConcurrentHashMap<>();
    private static class BarrierAction implements Runnable{
        @Override
        public void run(){
            System.out.println("barrier reach done");
            StringBuilder result =new StringBuilder();
            for(Map.Entry<Long,String> m :map.entrySet()){
                result.append("¡¾"+m.getValue()+"¡¿");
            }
            System.out.println("result map:"+result.toString());
        }
    }

    private static class BarrerThread extends Thread{
        @Override
        public void run (){
            Random r = new Random();
            try {
                if (r.nextBoolean()) {
                    sleep(2000);
                }
                System.out.println("one thread is running:" + getName());
                map.put(getId(),getName());
                barrier.await();

                System.out.println("after barrier to do:" + getName());
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public static void main (String[] args){
        for(int i=0;i<4;i++){
            new BarrerThread().start();
        }
    }
}
