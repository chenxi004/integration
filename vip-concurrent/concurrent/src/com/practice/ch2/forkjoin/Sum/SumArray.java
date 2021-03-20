package com.practice.ch2.forkjoin.Sum;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class SumArray {

    private static class CallTask extends RecursiveTask<Integer> {
        private static final int THRESHOLD=MakeArray.count/10;
        private int[] arr;
        private int fromindex=0;
        private int toindex=0;
        public  CallTask(int[] arr,int fromindex,int toindex){
            this.arr=arr;
            this.fromindex = fromindex;
            this.toindex=toindex;
        }
        @Override
        public Integer compute(){
            if((toindex-fromindex)<THRESHOLD){
                int total=0;
                //直接计算
                for(int i =fromindex;i<=toindex;i++){
                    total+=arr[i];
                }
                return total;
            }
            else{
                //分解任务
                int mid = (fromindex+toindex)/2;
                CallTask left = new CallTask(arr,fromindex,mid);
                CallTask right = new CallTask(arr,mid+1,toindex);
                invokeAll(left,right);
                return left.join()+right.join();
            }

        }
    }

    public static void main(String[] args){
        ForkJoinPool pool =new ForkJoinPool();
        int[] arr = MakeArray.MakeArray();
        CallTask task = new CallTask(arr,0,arr.length-1);
        long current = System.currentTimeMillis();
        pool.invoke(task);
        task.join();
        System.out.println("cost time:"+(System.currentTimeMillis()-current)+"ms");
    }

}
