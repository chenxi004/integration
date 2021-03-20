package com.practice.ch2.tools.semaphore;

import java.sql.Connection;

public class AppTest {
    private static DBPoolSemaphore pool =new DBPoolSemaphore();
    private static class SemaphoreThread extends Thread{
        @Override
        public void run(){
            long start =System.currentTimeMillis();
            try {
                Connection con = pool.getConnection();
                System.out.println("Thread_"+Thread.currentThread().getId()+",获取连接时间："+(System.currentTimeMillis()-start));
                Thread.sleep(200);
                con.createStatement();
                con.commit();
                System.out.println("c查询结束 释放连接");
                pool.releaseConnection(con);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args){
        for(int i=0;i<50;i++){
            new SemaphoreThread().start();
        }
    }
}
