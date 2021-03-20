package com.practice.ch1.wn;

public class Express {
    public final static String CITY="ShangHai";
    private int km=0;
    private String city="";
    public Express(String city, int km){
        this.km = km;
        this.city=city;
    }

    public synchronized void waitKm(){//调用wait 的范式：第一步是要获取对象锁
        while(this.km<200){//条件不成立 进入等待状态
            try {
                wait();//wait 是对象object 的方法
                System.out.println("wait km notified："+Thread.currentThread().getName());
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        //条件成立 执行业务代码
        System.out.println("current km："+this.km);
    }
    public synchronized void waitCity() {
        while (this.CITY.equals(this.city)) {
            try {
                wait();
                System.out.println("wait city is notified：" + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //条件成立 执行业务代码
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
