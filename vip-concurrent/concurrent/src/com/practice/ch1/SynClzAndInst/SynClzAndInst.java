package com.practice.ch1.SynClzAndInst;

public class SynClzAndInst {
    private volatile static Integer age=0;
    //�����߳�
    private static class ThreadClzSyn implements Runnable{

        @Override
        public void run(){
            for(int i=0;i<1000;i++) {
                increase();
            }
        }
    }
    //�������߳�
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
    //���� ��̬���� ������class����
    private synchronized static void increase(){
        age++;
        System.out.println("clz lock is running");
    }
    //������ �����еķ��� �����Ƕ���ʵ��
    private synchronized  void increase2(){
        age++;
        System.out.println(this.toString()+" is running:"+this.age);
    }

    public static void main(String[] args)throws Exception{
        //ͬһ������ ������ �õ�ͬһ���� ���Ա�֤�̰߳�ȫ
//        SynClzAndInst instance = new SynClzAndInst();
//        Thread threadinst1 = new Thread(new ThreadInstSyn(instance));
//        threadinst1.start();
//        Thread threadinst2 = new Thread(new ThreadInstSyn(instance));
//        threadinst2.start();
        //��ͬ���� ������ �õĲ�ͬ���� ����ͳһ������ �޷���֤�̰߳�ȫ
//        SynClzAndInst instance = new SynClzAndInst();
//        Thread threadinst1 = new Thread(new ThreadInstSyn(instance));
//        threadinst1.start();
//        SynClzAndInst instance1 = new SynClzAndInst();
//        Thread threadinst2 = new Thread(new ThreadInstSyn(instance1));
//        threadinst2.start();
        //����
        Thread threadclz = new Thread(new ThreadClzSyn());
        threadclz.start();
    }
}
