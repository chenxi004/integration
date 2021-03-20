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
        if(timeout<=0) {//����ʱ ֱ�ӻ�ȡ����
            while (pool.isEmpty()) {//û������ �ȴ�
                wait();
            }
            return pool.removeFirst();
        }
        else{
            long overtime = System.currentTimeMillis() + timeout;   //��ʱʱ���
            long remain = timeout;                                  //ʣ��ʱ��
            while(pool.isEmpty()&&remain>0){
                wait(remain);
                remain = overtime-System.currentTimeMillis();       //���¼���ʣ��ʱ��
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
