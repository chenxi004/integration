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
        this.useless =new Semaphore(0);//���������������������Ϊ��λ����λҲ��һ����Դ��Ҳ��Ҫ����������
                                                //���ǳ���ȥ��useless �ƺ������ӳص�ʹ��û��Ӱ�죿������
        for(int i=0;i<SIZE;i++){
            pool.addLast(SqlConnectImpl.fetchConnection());
        }
    }

    public Connection getConnection() throws InterruptedException {
        useful.acquire();
        Connection con;
        synchronized (pool) {//ע�� synchronize ���ܰ���useful.acquire(); ��Ϊ����������һ����������û�л�ȡ���ź������ͻ����������޷��ͷ�synchronize����������ô������ʹ�������ӵ��߳��ͷ�����ʱ �޷���ȡ����������
            con = pool.removeFirst();
        }
        useless.release();
        return con;
    }

    public void releaseConnection(Connection con) throws InterruptedException {
        if(con!=null){
            System.out.println("��ǰ��ʹ�����ӣ�"+useful.availablePermits()+"���ȴ���������"+useful.getQueueLength());
            useless.acquire();
            synchronized (pool){
                pool.addLast(con);
            }
            useful.release();
        }
    }
}
