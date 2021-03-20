package com.practice.ch1;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class OnlyMain {
    public static void main(String[] args){
        ThreadMXBean mx = ManagementFactory.getThreadMXBean();
        ThreadInfo[] ts =  mx.dumpAllThreads(true,true);
        for(ThreadInfo info: ts){
            System.out.println("["+info.getThreadId()+"] "+info.getThreadName());
        }

    }

}
