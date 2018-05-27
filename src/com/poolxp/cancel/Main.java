package com.poolxp.cancel;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        Task task = new Task();
        System.out.println("Main: Executing the Task");
        Future<String> result = executor.submit(task);
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Main: Cancelling the Task");
        result.cancel(true);
        System.out.println("Main: isCancelled "+ result.isCancelled());
        System.out.println("Main: isDone "+ result.isDone());
        executor.shutdown();
        System.out.println("Main: The executor has finished");
    }
}
