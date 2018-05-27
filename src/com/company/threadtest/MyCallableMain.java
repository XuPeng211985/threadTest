package com.company.threadtest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class MyCallableMain {
    public static void main(String[] args) {
        /**
         * Callable是一种具有具有类型参数的泛型 通过ExecutorService对象的
         * submit方法返回一个future对象，可以通过该对象的isDone方法来确定
         * 当前任务有没有执行完成，也可以调用该对象的get()方法获得执行后返回的结果
         * get()方法有一个重载方法可以设置超时时间，如果当前线程在规定时间内
         * 没有返回我们想要的结果，就可以中断当前线程。
         */
        ExecutorService exec = Executors.newCachedThreadPool();
        List<Future<String>>  results = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            results.add(exec.submit(new MyCallable(i)));
        }
        for (Future<String> f : results) {
            try {
                System.out.println(f.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
