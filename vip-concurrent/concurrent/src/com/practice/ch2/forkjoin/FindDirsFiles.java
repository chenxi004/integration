package com.practice.ch2.forkjoin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class FindDirsFiles extends RecursiveAction {
    File path;
    public FindDirsFiles(File path){
        this.path=path;
    }
    @Override
    public void compute(){
        File[] files = path.listFiles();
        List<FindDirsFiles> dirs=new ArrayList<>();
        for (File f :files){
            if(f.isDirectory()){
                dirs.add(new FindDirsFiles(f));
            }
            else {
                if (f.getAbsolutePath().endsWith("txt")) {
                    System.out.println("txt file name:" + f.getAbsolutePath());
                }
            }
        }
        if(!dirs.isEmpty()){
            //invokeAll(dirs);
            for(FindDirsFiles subTask:invokeAll(dirs)) {//调用子任务
                subTask.join();//等待子任务执行完成
            }
        }
    }

    public static void main(String[] args){
        try {
            ForkJoinPool pool = new ForkJoinPool();//主任务 要用ForkJoinPool调用
            File path = new File("D:/下载/");
            FindDirsFiles task =new FindDirsFiles(path);
            pool.execute(task);//异步调用 不获取结果

            System.out.println("my Task is Running......");
            Thread.sleep(1);
            int other = 0;
            for(int i=0;i<100;i++){
                other = other+i;
            }
            System.out.println("Main Thread done sth.....,otherWork="+other);
            task.join();//阻塞的方法 如果不写这行 有可能子任务还没有完成 main线程就结束了
            System.out.println("Task end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
