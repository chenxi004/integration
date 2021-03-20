package com.practice.ch4.rw;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class UseRwLock implements GoodsService {
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private Lock readLock = lock.readLock();
    private Lock writeLock =lock.writeLock();
    private GoodsInfo goodsInfo =new GoodsInfo("¶ÁËø",0,10000);

    public GoodsInfo getNumber(){
        try{
            readLock.lock();
            Thread.sleep(5);
            return goodsInfo;
        }
        catch(InterruptedException e){
            e.printStackTrace();
            return null;
        }
        finally {
            readLock.unlock();
        }
    }
    public void setNumber(int num){
        try{
            writeLock.lock();
            Thread.sleep(5);
            goodsInfo.changeNumber(num);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        finally {
            writeLock.unlock();
        }
    }
}
