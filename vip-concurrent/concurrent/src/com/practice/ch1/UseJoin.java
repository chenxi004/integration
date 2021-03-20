package com.practice.ch1;

public class UseJoin {
    private static class JoinRunnabl implements Runnable{
        private Thread curThread;
        public JoinRunnabl(Thread t){
            this.curThread=t;
        }

        @Override
        public void run(){
            try {
                System.out.println("Im a joined thread,id:" +curThread.getId() + ",name:" + curThread.getName());
                this.curThread.join();
                System.out.println("this thread end,id:" + curThread.getId() + ",name:" + curThread.getName());
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        Thread t= Thread.currentThread();
        for(int i=0;i<10;i++){
            Thread newThread = new Thread(new JoinRunnabl(t));
            newThread.start();
            t=newThread;
        }
        try{
            t.sleep(2000);
            System.out.println("main terminated");
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
