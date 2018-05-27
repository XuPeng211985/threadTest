package com.poolxp.callable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
public class Main {
    public static void main(String[] args){
        /**
         * Executor框架中的Callable接口与Runnable接口类似，都可以创建一个线程任务
         *   不同于Runnable的是，实现Callable接口的对象可以将任务提交给线程池后
         *   返回一个Future对象，该对象包含Callable接口执行方法call的执行结果
         *   并且在线程执行的过程中可以通过Future对象的isDone()方法，实时跟踪
         *   当前任务是否完成，并且通过阻塞式的get()方法获取线程任务的执行结果
         *  并且可以通过另一个版本的get(long timeout,TimeUnit unit）为线程设置阻塞
         *  时间，如果超过阻塞时间 任务还没有返回结果；那么get()方法直接返回null
         */
        //实例化一个固定数目的线程池
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        List<Future<Integer>> resultList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            Integer number = new Integer(random.nextInt(10));
            FactorialCalculator factorialCalculator = new FactorialCalculator(number);
            //将任务提交给线程池中的两个线程
            Future<Integer> future = executor.submit(factorialCalculator);
            resultList.add(future);
        }
        do{
            System.out.println("当前已经完成的任务数量："+executor.getCompletedTaskCount());
            for (int i = 0; i < resultList.size(); i++) {
                Future<Integer> future = resultList.get(i);
                System.out.println("Task: "+i+"是否完成"+future.isDone());
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
              }while(executor.getCompletedTaskCount() < resultList.size());
        System.out.println("执行结果为：");
        for (int i = 0; i < resultList.size(); i++) {
            Future<Integer> result = resultList.get(i);
          Integer number = null;
            try {
                //阻塞式get() 获得结果后返回  否则一直阻塞
                number = result.get();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            System.out.println("Task:"+i+" 返回的结果是："+number);
        }
        //平缓式关闭：继续执行当前正在执行的任务 并且等待执行已经提交还没有开始执行的任务
        //shutdownNow() 暴力关闭 尝试取消正在执行的任务 并返回所有已提交但尚未开始的任务
        //从而将这些日志写入日志文件 方便以后处理
        executor.shutdown();
    }
}
