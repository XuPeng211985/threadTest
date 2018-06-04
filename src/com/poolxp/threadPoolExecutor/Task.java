package com.poolxp.threadPoolExecutor;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Task implements Runnable {
    private Date initDate;//任务创建时间
    private String name;//任务名称
    public Task(String name) {
        this.initDate = new Date();
        this.name = name;
    }
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"在 "+this.initDate+" 创建任务 "+ this.name);
        System.out.println(Thread.currentThread().getName()+"在 "+new Date()+" 开始执行任务 "+ this.name);
        Long duration =(long)(Math.random()*10);
        System.out.println(Thread.currentThread().getName()+"花费了："+duration+"秒来完成任务"+this.name);
        try {
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println(Thread.currentThread().getName()+"在 "+new Date()+" 完成了任务 "+ this.name);
    }
}
