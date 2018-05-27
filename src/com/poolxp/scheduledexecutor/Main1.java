package com.poolxp.scheduledexecutor;
import java.util.Date;
import java.util.concurrent.*;

public class Main1 {
    public static void main(String[] args) {
        /**
         * 为什么scheduleAtFixedRate不能传入一个Callable对象？
         */
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        System.out.println("Main: Starting at: "+new Date());
        Task1 task1 = new Task1("Task1");
        /**
         * 输出结果解析：
         * Main: Starting at: Wed Apr 18 10:03:58 CST 2018
         * Main: Delay: 999
         * Main: Delay: 498
         * ===> 由于第二个参数传入的是1秒（任务延迟等待时间）  所以main线程开始执行大约1000毫秒之后
         * ===>线程池开始执行第一个任务，每隔500毫秒主函数做一次Delay的输出
         * Task1 Executed at: Wed Apr 18 10:03:59 CST 2018
         * Main: Delay: 1998
         * Main: Delay: 1497
         * Main: Delay: 997
         * Main: Delay: 496
         * ===>1秒后第一个任务开始执行，每隔2秒（第三个参数，任务的执行周期）
         * ===>所以大约2000毫秒后 任务开始第二次执行
         * Task1 Executed at: Wed Apr 18 10:04:01 CST 2018
         * Main: Delay: 1996
         * Main: Delay: 1495
         * Main: Delay: 994
         * Main: Delay: 494
         * Task1 Executed at: Wed Apr 18 10:04:03 CST 2018
         * ===>for循环里面 Main执行10次Delay的输出，总共用时5秒   刚好与主线程休眠5秒相匹配
         * ===>在这5秒当中Task1 1 秒用来延迟等待 另外4秒用来执行两次任务
         * ===>调用shutdown()后，任务挂起 （和原始的Executors.newFixedThreadPool 返回的executor的shutdown不同）
         * Main: No more tasks at: Wed Apr 18 10:04:03 CST 2018
         * Main: Finished at: Wed Apr 18 10:04:08 CST 2018
         */
        ScheduledFuture<?> result = executor.scheduleAtFixedRate(task1,1,2, TimeUnit.SECONDS);
        for (int i = 0; i < 10; i++) {
            System.out.println("Main: Delay: "+result.getDelay(TimeUnit.MILLISECONDS));
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
        System.out.println("Main: No more tasks at: "+new Date());
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Main: Finished at: "+new Date());
    }
}
