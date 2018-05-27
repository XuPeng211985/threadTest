package com.concurrentlist.linkeddeque;

import java.util.concurrent.ConcurrentLinkedDeque;

public class AddTask implements Runnable {
    private ConcurrentLinkedDeque<String> linkedDeque;

    public AddTask(ConcurrentLinkedDeque<String> linkedDeque) {
        this.linkedDeque = linkedDeque;
    }

    @Override
    public void run() {
        //每个线程添加10000个字符串
        String name = Thread.currentThread().getName();
        for (int i = 0; i < 10000; i++) {
            linkedDeque.add(name + ":Element=>"+i);
        }
    }
}
