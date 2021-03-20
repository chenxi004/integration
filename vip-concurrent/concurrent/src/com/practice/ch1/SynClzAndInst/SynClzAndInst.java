package com.practice.ch1.SynClzAndInst;

public class SynClzAndInst {
    private volatile static Integer age=0;
    //类锁线程
    private static class ThreadClzSyn implements Runnable{

        @Override
        public void run(){
            for(int i=0;i<1000;i++) {
                increase();
            }
        }
    }
    //对象锁线程
    private static class ThreadInstSyn implements Runnable{
        private SynClzAndInst clz;
        public ThreadInstSyn(SynClzAndInst t){
            this.clz=t;
        }
        @Override
        public void run(){
            for(int i=0;i<10000;i++) {
                clz.increase2();
            }
        }
    }
    //类锁 静态方法 锁的是class对象
    private synchronized static void increase(){
        age++;
        System.out.println("clz lock is running");
    }
    //对象锁 对象中的方法 锁的是对象实例
    private synchronized  void increase2(){
        age++;
        System.out.println(this.toString()+" is running:"+this.age);
    }

    public static void main(String[] args)throws Exception{
        //同一个对象 对象锁 用的同一把锁 可以保证线程安全
//        SynClzAndInst instance = new SynClzAndInst();
//        Thread threadinst1 = new Thread(new ThreadInstSyn(instance));
//        threadinst1.start();
//        Thread threadinst2 = new Thread(new ThreadInstSyn(instance));
//        threadinst2.start();
        //不同对象 对象锁 用的不同的锁 操作统一个变量 无法保证线程安全
//        SynClzAndInst instance = new SynClzAndInst();
//        Thread threadinst1 = new Thread(new ThreadInstSyn(instance));
//        threadinst1.start();
//        SynClzAndInst instance1 = new SynClzAndInst();
//        Thread threadinst2 = new Thread(new ThreadInstSyn(instance1));
//        threadinst2.start();
        //类锁
        Thread threadclz = new Thread(new ThreadClzSyn());
        threadclz.start();
    }
}
