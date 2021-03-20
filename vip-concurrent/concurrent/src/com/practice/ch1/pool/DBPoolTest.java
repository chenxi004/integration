package com.practice.ch1.pool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class DBPoolTest {
    private final static int countDown=20;
    private static int conCount=5;
    private static AtomicInteger got=new AtomicInteger(0);
    private static AtomicInteger missing=new AtomicInteger(0);
    public static CountDownLatch countDownLatch=new CountDownLatch(countDown);
    public static DBPool pool = new DBPool(10);

    private static class PoolThread extends Thread {
        @Override
        public void run(){
            for(int i=0;i<conCount;i++){
                Connection connection = null;
                try {
                    connection = pool.fetchConnection(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(connection==null){
                    missing.getAndIncrement();
                    System.out.println(getName()+"µÈ´ý³¬Ê±£¡");
                }
                else{
                    try {
                        connection.createStatement();
                        connection.commit();

                    }
                    catch (SQLException e){
                        e.printStackTrace();
                    }
                    finally {
                        pool.releaseConnection(connection);
                        got.getAndIncrement();
                    }
                }
            }
            countDownLatch.countDown();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        for(int i=0;i<countDown;i++){
            PoolThread t = new PoolThread();
            t.start();
        }
        countDownLatch.await();
        System.out.println("try to connect to db count:"+countDown*conCount);
        System.out.println("got connection count:"+got);
        System.out.println("missing connection count:"+missing);
    }
}
