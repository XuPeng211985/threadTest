package com.poolxp.threadPoolExecutor;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server {
    private ThreadPoolExecutor executor;
    public Server(){
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
    }
    public void executeTesk(Task task){
        System.out.println("一个新的任务到达server");
        executor.execute(task);
        System.out.println("线程池的大小为："+executor.getPoolSize());
        System.out.println("需要处理的请求个数："+executor.getActiveCount());
        System.out.println("已经处理完成的请求个数："+executor.getCompletedTaskCount());
    }

    public void endServer(){
        executor.shutdown();
    }
}
