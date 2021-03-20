package com.practice.ch5.bq;

import java.util.concurrent.DelayQueue;

public class PutOrder extends  Thread{
    private DelayQueue queue;
    public PutOrder(DelayQueue queue) {
        super();
        this.queue= queue;
    }
    @Override
    public void run(){
        Order order =new Order("gyh00001",10000);
        QueueItem<Order> item =new QueueItem<>(3000,order);
        queue.offer(item);
        System.out.println("���������뵽�����У��ȴ�"+3000+"���뵽��");

        Order order1 =new Order("gyh00002",20000);
        QueueItem<Order> item1 =new QueueItem<>(6000,order1);
        queue.offer(item1);
        System.out.println("���������뵽�����У��ȴ�"+6000+"���뵽��");
    }
}
