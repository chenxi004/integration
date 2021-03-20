package com.practice.ch3;

import java.util.concurrent.atomic.AtomicInteger;

public class UseAtomicInt {
    static AtomicInteger ai = new AtomicInteger(10);
    public static void main(String[] args){

        System.out.println("before increase value:"+ai.getAndIncrement());
        System.out.println("after increase value:"+ai.incrementAndGet());
        System.out.println("final value:"+ai.get());
    }
}
