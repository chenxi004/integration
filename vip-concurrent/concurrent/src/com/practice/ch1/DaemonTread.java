package com.practice.ch1;

public class DaemonTread {

    private static class DaemonThread extends Thread{
        @Override
        public void run (){
            while(true) {
                System.out.println("I am a daemon thread");
            }
        }
    }

    public static void main(String[] args) throws Exception{
        DaemonThread daemonThread = new DaemonThread();
        daemonThread.setDaemon(true);
        daemonThread.start();
        Thread.sleep(2000);

    }
}
