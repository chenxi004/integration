package com.practice.ch6.MyThreadPool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;

public class MyThreadPool {
    private static int CORE_POOL_SIZE=5;
    private static int QUEUE_SIZE=10;
    private ArrayBlockingQueue<Runnable> worker_queue;
    private WorkerThread[] threads;

    //初始化线程池
    public MyThreadPool(int corePoolSize, int queueSize) {
        if(corePoolSize<=0){corePoolSize=CORE_POOL_SIZE;}
        if(queueSize<=0){queueSize=QUEUE_SIZE;}
        this.worker_queue = new ArrayBlockingQueue<Runnable>(corePoolSize);
        this.threads = new WorkerThread[queueSize];
        for (int i=0;i<corePoolSize;i++){
            this.threads[i]=new WorkerThread();
            this.threads[i].start();
        }
    }

    public class WorkerThread extends Thread{
        @Override
        public void run(){
            Runnable r;
            try {
                while (!isInterrupted()){
                    r = worker_queue.take();
                    if(r!=null){
                        System.out.println(getId()+" ready exec :"+r);
                        r.run();
                    }
                    // r = null;//help gc;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void stopWorder(){
            interrupt();
        }
    }
    public void execute(Runnable worker){
        try {
            worker_queue.put(worker);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//    public T submit(Callable<T> worder){
//        try {
//            worker_queue.put(worker);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

    public void destroy(){
        // 工作线程停止工作，且置为null
        System.out.println("ready close pool.....");
        for (WorkerThread t:threads) {
            t.stopWorder();
            t = null;//help gc
        }
        worker_queue.clear();//清空队列
    }



}
