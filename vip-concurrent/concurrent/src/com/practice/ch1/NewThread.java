package com.practice.ch1;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class NewThread {

    private static class ThreadRun implements Runnable{
        @Override
        public void run(){
            System.out.println("execute runnable");
        }
    }

    private static class ThreadCallable implements Callable<String>{
        @Override
        public String call() throws Exception{
            System.out.println("execute callable");
            return "reture string execute callable";
        }
    }

    public static void main(String[] args)throws InterruptedException, ExecutionException {
        ThreadRun runInstance = new ThreadRun();
        Thread trunInstance = new Thread(runInstance);
        trunInstance.start();

        ThreadCallable callInstance = new ThreadCallable();
        FutureTask<String> futureTask = new FutureTask<>(callInstance);
        new Thread(futureTask).start();
        System.out.println(futureTask.get());

    }
}
