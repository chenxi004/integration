package com.practice.ch1.wn;

public class Express {
    public final static String CITY="ShangHai";
    private int km=0;
    private String city="";
    public Express(String city, int km){
        this.km = km;
        this.city=city;
    }

    public synchronized void waitKm(){//����wait �ķ�ʽ����һ����Ҫ��ȡ������
        while(this.km<200){//���������� ����ȴ�״̬
            try {
                wait();//wait �Ƕ���object �ķ���
                System.out.println("wait km notified��"+Thread.currentThread().getName());
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        //�������� ִ��ҵ�����
        System.out.println("current km��"+this.km);
    }
    public synchronized void waitCity() {
        while (this.CITY.equals(this.city)) {
            try {
                wait();
                System.out.println("wait city is notified��" + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //�������� ִ��ҵ�����
        System.out.println("current city is: "+this.city);
    }

    public synchronized void changeKm(){
        this.km+=200;
        notifyAll();
    }

    public synchronized void chageCity(){
        this.city="BeiJing";
        notify();
    }
}
