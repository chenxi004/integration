package com.practice.ch2.tools.semaphore;

import com.xiangxue.ch2.tools.semaphore.SqlConnectImpl;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class DBPoolSemaphore {
    private static int SIZE=10;
    private Semaphore useful,useless;
    private static LinkedList<Connection> pool =new LinkedList<>();
    public DBPoolSemaphore(){
        this.useful =new Semaphore(SIZE);
        this.useless =new Semaphore(0);//不可用连接数，可以理解为空位，空位也是一种资源，也需要做流量控制
                                                //但是尝试去掉useless 似乎对连接池的使用没有影响？？？？
        for(int i=0;i<SIZE;i++){
            pool.addLast(SqlConnectImpl.fetchConnection());
        }
    }

    public Connection getConnection() throws InterruptedException {
        useful.acquire();
        Connection con;
        synchronized (pool) {//注意 synchronize 不能包括useful.acquire(); 因为会阻塞，当一个请求连接没有获取到信号量，就会阻塞，且无法释放synchronize对象锁，那么其他正使用完连接的线程释放连接时 无法获取到对象锁。
            con = pool.removeFirst();
        }
        useless.release();
        return con;
    }

    public void releaseConnection(Connection con) throws InterruptedException {
        if(con!=null){
            System.out.println("当前可使用连接："+useful.availablePermits()+"，等待连接数："+useful.getQueueLength());
            useless.acquire();
            synchronized (pool){
                pool.addLast(con);
            }
            useful.release();
        }
    }
}
