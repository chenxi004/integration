package com.practice.ch2.future;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class UseFuture {
    private static class CallFuture implements Callable<Integer>{
        private int total= 0;
        @Override
        public Integer call(){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                //e.printStackTrace();
                Thread.currentThread().isInterrupted();
            }
            for(int i=0;i<500;i++){
                total+=i;
            }
            return total;
        }
    }


    public static void main(String[] args)throws InterruptedException, ExecutionException {
        CallFuture work = new CallFuture();
        FutureTask<Integer> task = new FutureTask<>(work);
        new Thread(task).start();
        Random r =new Random();
        if(r.nextBoolean()){//随机决定是获得结果还是终止任务
            System.out.println("get result :"+task.get());//get方法为阻塞方法
        }
        else{
            System.out.println("中断计算");
            task.cancel(true);//取消任务 中断：打个招呼

        }

    }
}
