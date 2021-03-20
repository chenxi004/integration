package com.practice.ch5.bq;

public class Order {
    private String orderNo;
    private double amount;

    public String getOrderNo() {
        return orderNo;
    }

    public double getAmount() {
        return amount;
    }

    public Order(String orderNo, double amount) {
        super();
        this.orderNo = orderNo;
        this.amount = amount;
    }


}
