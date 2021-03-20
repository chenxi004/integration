package com.practice.ch3;

import java.util.concurrent.atomic.AtomicStampedReference;

public class UseAtomicStampedReference {
    private static int STAMP=0;
    static UserInfo userInfo =new UserInfo("qiaochenxi",28);
    static AtomicStampedReference<UserInfo> asr = new AtomicStampedReference<>(userInfo,STAMP);
    static int oldStamp = asr.getStamp();
    public static void main(String[] args) throws InterruptedException {
        Thread t1=new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(Thread.currentThread().getName()
                                + "，old stamp:"+oldStamp+"，old  name:"+asr.getReference().getName());
                        UserInfo updateUserInfo =new UserInfo("brian",30);
                        boolean result = asr.compareAndSet(userInfo,updateUserInfo,oldStamp,++oldStamp);
                        System.out.println(Thread.currentThread().getName()
                                + "，new stamp:"+asr.getStamp()+"，new name:"+asr.getReference().getName());
                        System.out.println(Thread.currentThread().getName()
                                + "，update result: "+result);
                    }
                }
        );
        Thread t2=new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(Thread.currentThread().getName()
                                + "，old stamp:"+oldStamp+"，old name:"+asr.getReference().getName());
                        UserInfo updateUserInfo =new UserInfo("micheal",31);
                        boolean result = asr.compareAndSet(userInfo,updateUserInfo,oldStamp,++oldStamp);
                        System.out.println(Thread.currentThread().getName()
                                + "，new stamp:"+asr.getStamp()+"，new name:"+asr.getReference().getName());
                        System.out.println(Thread.currentThread().getName()
                                + "，update result: "+result);
                    }
                }
        );

        t1.start();
        t1.join();
        t2.start();
        t2.join();
    }




    //定义一个实体类
    static class UserInfo {
        private String name;
        private int age;

        public UserInfo(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }
}
