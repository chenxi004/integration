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
            for(FindDirsFiles subTask:invokeAll(dirs)) {//����������
                subTask.join();//�ȴ�������ִ�����
            }
        }
    }

    public static void main(String[] args){
        try {
            ForkJoinPool pool = new ForkJoinPool();//������ Ҫ��ForkJoinPool����
            File path = new File("D:/����/");
            FindDirsFiles task =new FindDirsFiles(path);
            pool.execute(task);//�첽���� ����ȡ���

            System.out.println("my Task is Running......");
            Thread.sleep(1);
            int other = 0;
            for(int i=0;i<100;i++){
                other = other+i;
            }
            System.out.println("Main Thread done sth.....,otherWork="+other);
            task.join();//�����ķ��� �����д���� �п���������û����� main�߳̾ͽ�����
            System.out.println("Task end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
