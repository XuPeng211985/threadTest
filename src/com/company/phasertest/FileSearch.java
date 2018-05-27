package com.company.phasertest;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * Created by hpx on 2017/10/30.
 */
public class FileSearch implements Runnable {
    /**
     * 初始目录
     */
    private String initpath;
    private String end;
    private List<String> list;
    private Phaser phaser;

    /**
     *控制FileSearch对象的执行.它们的执行将分成3个步骤：
     * 1. 在指定的目录和子目录中查找指定扩展名的的文件
     * 2. 过滤结果.除了今天修改的文件
     * 3. 打印结果
     */
    public FileSearch(String initpath, String end,Phaser phaser) {
        this.initpath = initpath;
        this.end = end;
        this.list = new ArrayList<>();
        this.phaser = phaser;
    }
    @Override
    public void run() {
        phaser.arriveAndAwaitAdvance();//等待所有搜索对象创建
        System.out.println(Thread.currentThread().getName() +" 开始搜索...");
        File file = new File(initpath);
        if(file.isDirectory()){
            directoryProcess(file);
        }
        if(!checkResults()){
            return;
        }
        filterResults();
        if(!checkResults()){
            return;
        }
        showinfo();
        phaser.arriveAndDeregister();
        System.out.println(Thread.currentThread().getName()+" 搜索完成...");
    }
    public void fileProcess(File file){
        if(file.getName().endsWith(end)){
            list.add(file.getAbsolutePath());
        }
    }
    public void directoryProcess(File file) {
        File[] files = file.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    directoryProcess(files[i]);
                } else {
                    fileProcess(files[i]);
                }
            }
        }
    }
    public void filterResults(){
        List<String> resultList = new ArrayList<>();
        long begintime = new Date().getTime();
        for (int i = 0; i < list.size() ; i++) {
            File file = new File(list.get(i));
            long endtime = file.lastModified();//最后修改时间
            if(begintime - endtime > TimeUnit.MILLISECONDS.convert(1,TimeUnit.DAYS)) {
                resultList.add(list.get(i));
            }
        }
        list = resultList;
    }
    public void showinfo(){
        for (int i = 0; i < list.size(); i++) {
            File file = new File(list.get(i));
            System.out.println(Thread.currentThread().getName()+" 查找结果;"+ file.getAbsolutePath());
        }
        phaser.arriveAndAwaitAdvance();
        /**
         * 等待其他搜索对象结束
         */
    }
    public boolean checkResults(){
        if(list.isEmpty()){
            System.out.println(Thread.currentThread().getName()+" "+phaser.getPhase()+"搜索到0条结果..垃圾");
            System.out.println(Thread.currentThread().getName()+" "+phaser.getPhase() + "搜索结束...");
            phaser.arriveAndDeregister();
            return false;
        }else{
            System.out.println(Thread.currentThread().getName()+" "+phaser.getPhase()+"共搜索到结果： "+list.size());
            phaser.arriveAndAwaitAdvance();//阻塞  表示已经完成了该阶段的搜索 继续等待其他搜索对象完成该阶段
           return true;
        }
    }
}

