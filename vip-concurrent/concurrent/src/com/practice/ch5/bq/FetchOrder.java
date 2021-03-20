package com.practice.ch5.bq;

import java.util.concurrent.DelayQueue;

public class FetchOrder extends Thread{
    private DelayQueue queue;
    public FetchOrder(DelayQueue queue){
        super();
        this.queue=queue;
    }

    @Override
    public void run (){
        while(true) {
            try {
                Order order = ((QueueItem<Order>) this.queue.take()).getData();
                System.out.println("��ȡ���ڶ����������ţ�" + order.getOrderNo() + "��������" + order.getAmount());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
