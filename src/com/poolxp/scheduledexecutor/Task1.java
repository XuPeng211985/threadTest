package com.poolxp.scheduledexecutor;

import java.util.Date;
import java.util.concurrent.Callable;

public class Task1 implements Runnable {
    private String name;

    public Task1(String name) {
        this.name = name;
    }
    @Override
    public void run() {
        System.out.println(this.name + " Executed at: "+new Date());
    }
}
