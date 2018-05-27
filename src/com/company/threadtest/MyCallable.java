package com.company.threadtest;

import java.util.concurrent.Callable;

public class MyCallable implements Callable<String> {
    private int id;

    public MyCallable(int id) {
        this.id = id;
    }
    @Override
    public String call() throws Exception {
        return "MyCallable: id = "+id;
    }
}
