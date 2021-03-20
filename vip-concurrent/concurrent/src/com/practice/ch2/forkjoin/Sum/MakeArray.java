package com.practice.ch2.forkjoin.Sum;

import java.util.Random;

public class MakeArray {
    public static final int count = 100000;
    public static int[] MakeArray(){
        int[] array = new int[count];
        Random r = new Random();
        for (int i=0;i<count;i++){
            array[i]=r.nextInt(count*3);
        }
        return array;
    }
}
