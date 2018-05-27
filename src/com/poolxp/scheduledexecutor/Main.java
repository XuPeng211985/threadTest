package com.poolxp.scheduledexecutor;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        //创建ScheduledExecutorService
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        System.out.println("Main: Starting at "+new Date());
        for (int i = 0; i < 5; i++) {
            Task task = new Task("Task-"+i);
            /**
             * 指定特定的时间延迟执行某个任务：
             * schedule的三个参数意义：在提交任务的同时，告诉它们需要等待多长时间才能开始执行
             * 参数1：表明提交的任务
             * 参数2：指定该任务需要等待的时间
             * 参数3：给第二个参数限定一个单位 到底是 时 还是 分 还是 秒
             */
            executor.schedule(task,i+1,TimeUnit.SECONDS);
        }
        //在此强调shutdown只是敲了警钟，将要关闭线程池
        //但是该执行多少任务，还是执行多少任务
        executor.shutdown();
        try {
            executor.awaitTermination(1,TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Core: Ends at: "+new Date());
    }
}
