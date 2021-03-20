package com.practice.ch3;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class AtomicArray {
    static int[] arr = {1,5,10};
    static AtomicIntegerArray aa= new AtomicIntegerArray(arr);
    public static void main(String[] args){
        aa.getAndSet(1,8);
        System.out.println("update value :"+aa.get(1));
        System.out.println("old value :"+arr[1]);
        aa.getAndIncrement(1);
        System.out.println("update value :"+aa.get(1));
        System.out.println("old value :"+arr[1]);

        aa.getAndAdd(1,3);
        System.out.println("update value :"+aa.get(1));
        System.out.println("old value :"+arr[1]);
    }
}
