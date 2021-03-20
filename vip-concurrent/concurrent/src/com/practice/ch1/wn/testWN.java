package com.practice.ch1.wn;

public class testWN {
    private static Express expressInstanse =new Express(Express.CITY,0);
    private static class WaitKm extends Thread{
        @Override
        public void run (){
            expressInstanse.waitKm();
        }
    }
    private static class WaitCity extends Thread{
        @Override
        public void run (){
            expressInstanse.waitCity();
        }
    }
    public static void main(String[] args){
        for (int i=0;i<3;i++){
            WaitKm thread = new WaitKm();
            thread.start();
        }
        for (int i=0;i<3;i++){
            WaitCity thread = new WaitCity();
            thread.start();
        }

        try {
            Thread.currentThread().sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        expressInstanse.changeKm();//notifyALL
        //expressInstanse.chageCity();//notify �����źſ��ܻᱻͬ���ȴ��ڶ������ϵ������߳�����

    }
}
