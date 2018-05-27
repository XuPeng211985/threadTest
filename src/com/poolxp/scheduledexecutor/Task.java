package com.poolxp.scheduledexecutor;
import java.util.Date;
import java.util.concurrent.Callable;
public class Task implements Callable<String> {
   private String name;
    public Task(String name) {
        this.name = name;
    }
    @Override
    public String call() throws Exception {
        System.out.println(this.name+" Start at: "+new Date());
        return "hello world!";
    }
}
