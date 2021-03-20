package com.practice.ch1;

public class UseThreadLocal {
    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return 1;
        }
    };
    private static class ThreadLocalThread implements Runnable{
        int i;
        public ThreadLocalThread(int i){
            this.i=i;
        }
        @Override
        public void run()
        {
            Integer s =threadLocal.get();
            s=s+this.i;

            threadLocal.set(s);
            System.out.println(Thread.currentThread().getName()+":"
                    +threadLocal.get());
        }
    }
    public static void main(String[] args){
        Thread a1 =new Thread(new ThreadLocalThread(1));
        a1.start();
        Thread a2 =new Thread(new ThreadLocalThread(2));
        a2.start();
        Thread a3 =new Thread(new ThreadLocalThread(3));
        a3.start();
    }
}
