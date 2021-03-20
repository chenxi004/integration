package com.practice.ch4.rw;

public class UseSyn implements GoodsService{
    private GoodsInfo goodsInfo =new GoodsInfo("ÄÚÖÃËø",10000,10000);

    @Override
    public synchronized GoodsInfo getNumber() {
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return goodsInfo;
    }

    @Override
    public synchronized void setNumber(int num) {
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        goodsInfo.changeNumber(num);
    }

}
