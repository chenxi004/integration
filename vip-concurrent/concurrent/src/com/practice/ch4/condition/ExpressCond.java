package com.practice.ch4.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
public class ExpressCond {
    private final static String CITY ="SHANGHAI";
    private static String city="";
    private static int km=100;
    ReentrantLock lock=new ReentrantLock();
    Condition kmCondition = lock.newCondition();
    Condition cityCondition =lock.newCondition();

    public void ChangeKm(int km){
        try{
            lock.lock();
            this.km=km;
            kmCondition.signalAll();
        }
        finally {
            lock.unlock();
        }
    }

    public void ChangeCity(String city){
        try{
            lock.lock();
            this.city=city;
            cityCondition.signal();
        }
        finally {
            lock.unlock();
        }
    }

    public void waitKm(){
        try {
            lock.lock();
            while(km<=100){
                System.out.println("km is await");
                kmCondition.await();
            }
            System.out.println("km is signal,current km is:"+km);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void waitCity(){
        try{
            lock.lock();
            while(CITY.endsWith(city)){
                System.out.println("city is await");
                cityCondition.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
