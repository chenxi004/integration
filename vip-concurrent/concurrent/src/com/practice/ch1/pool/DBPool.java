package com.practice.ch1.pool;

import java.sql.Connection;
import java.util.LinkedList;

public class DBPool {
    public static LinkedList<Connection> pool=new LinkedList<>();
    public DBPool(int size){
        for(int i =0;i<size;i++){
            pool.addLast(SqlConnectImpl.fetchConnection());
        }
    }

    public synchronized Connection fetchConnection(long timeout) throws InterruptedException {
        if(timeout<=0) {//不超时 直接获取连接
            while (pool.isEmpty()) {//没有连接 等待
                wait();
            }
            return pool.removeFirst();
        }
        else{
            long overtime = System.currentTimeMillis() + timeout;   //超时时间点
            long remain = timeout;                                  //剩余时间
            while(pool.isEmpty()&&remain>0){
                wait(remain);
                remain = overtime-System.currentTimeMillis();       //重新计算剩余时间
            }
            if(!pool.isEmpty()){
                return pool.removeFirst();
            }
            else{
                return null;
            }
        }

    }

    public synchronized void releaseConnection(Connection con){
        if(con!=null){
            pool.addLast(con);
            notifyAll();
        }
    }
}
