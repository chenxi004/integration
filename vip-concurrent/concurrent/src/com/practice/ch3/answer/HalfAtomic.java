package com.practice.ch3.answer;

import java.util.concurrent.atomic.AtomicInteger;

public class HalfAtomic extends AtomicInteger {
    public int GetAndIncreaments(){
        int expect= get();
        int newvalue = expect++;
        while(!compareAndSet(expect,newvalue)){
            expect= get();
            newvalue++;
        }
        return newvalue;
    }
}
