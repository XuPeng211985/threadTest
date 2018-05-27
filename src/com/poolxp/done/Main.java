package com.poolxp.done;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        //创建5个任务结果处理对象
        ResultTask[] resultTasks = new ResultTask[5];
        //创建5个任务
        for (int i = 0; i < 5; i++) {
            ExecutableTask executableTask = new ExecutableTask("Task=="+i);
            resultTasks[i] = new ResultTask(executableTask);
            //将任务提交给线程池
            executor.submit(resultTasks[i]);
        }
        //主线程休眠5秒
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //将所有任务都取消 但是有部分任务在取消之前就完成了
        for (int i = 0; i < resultTasks.length; i++) {
            // 取消所有的任务,如果在这之前有的任务已经结束了,那么这里的取消操作不会有任何的影响
            resultTasks[i].cancel(true);
        }
        for (int i = 0; i < resultTasks.length; i++) {
            if(!resultTasks[i].isCancelled()){
                try {
                    System.out.println("没有取消的任务:  "+resultTasks[i].get());
                    System.out.println("因为我在取消之前就执行完成了 哈哈");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
        executor.shutdown();
    }
}
