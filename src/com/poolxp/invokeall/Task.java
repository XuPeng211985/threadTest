package com.poolxp.invokeall;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class Task implements Callable<Result> {
    private String name;
    public Task(String name) {
        this.name = name;
    }
    @Override
    public Result call() throws Exception {
        System.out.println(this.name+" Staring");
        //设置随机值，完成验证功能 最先完成验证工作的线程直接返回结果
        //其他线程将不再搜寻
        Long duration = (long)(Math.random()*10);
        System.out.println(this.name+"Waiting "+duration+"seconds for results.");
        TimeUnit.SECONDS.sleep(duration);
        int value = 0;
        for (int i = 0; i < 5; i++) {
            value += (int) (Math.random() * 100);
        }
        Result result = new Result();
        result.setName(this.name);
        result.setValue(value);
        System.out.printf("%s: Ends\n", this.name);
        return result;
    }
}
