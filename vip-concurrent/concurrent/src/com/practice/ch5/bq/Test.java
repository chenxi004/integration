package com.practice.ch5.bq;

import java.util.concurrent.DelayQueue;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        DelayQueue queue =new DelayQueue();
        new PutOrder(queue).start();
        new FetchOrder(queue).start();

        //ÿ��300���룬��ӡ������
        for(int i=1;i<15;i++){
            Thread.sleep(300);
            System.out.println(i*300);
        }
    }
}
