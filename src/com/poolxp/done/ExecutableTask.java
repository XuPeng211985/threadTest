package com.poolxp.done;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class ExecutableTask implements Callable<String> {
    private String name;
    public ExecutableTask(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    @Override
    public String call() throws Exception {
        Long duration = (long)(Math.random()*10);
        TimeUnit.SECONDS.sleep(duration);
        System.out.println("Waiting "+this.name+"完成任务花费了："+duration+"s");
        return "Hello world ! I'm "+this.name;
    }
}
