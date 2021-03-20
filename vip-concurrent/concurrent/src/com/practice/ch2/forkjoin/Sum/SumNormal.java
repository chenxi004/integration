package com.practice.ch2.forkjoin.Sum;

public class SumNormal {
    public static void main(String[] args){
        int[] arr = MakeArray.MakeArray();
        int total =0;
        long start = System.currentTimeMillis();
        for(int i =0;i<arr.length;i++){
//            try {
//                Thread.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            total+=arr[i];

        }
        System.out.println("cost time:"+(System.currentTimeMillis()-start)+"ms");
    }

}
