package com.practice.ch4.condition;

public class TestCond {
    private static ExpressCond express =new ExpressCond();
    private static class ExpressKmThread extends Thread{
        @Override
        public void run (){
            express.waitKm();
        }
    }

    private static class ExpressCityThread extends Thread{
        @Override
        public void run (){
            express.waitCity();
        }
    }

    private static class ExpressSignalThread extends Thread{
        @Override
        public void run(){
            express.ChangeKm(101);
        }
    }

    public static void main(String[] args){
        for(int i=0;i<3;i++) {
            new ExpressKmThread().start();
        }
        for(int i=0;i<3;i++) {
            new ExpressCityThread().start();
        }
        new ExpressSignalThread().start();
    }

}
