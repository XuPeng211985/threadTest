package com.concurrentlist.priorityblockingqueue;

import java.util.concurrent.PriorityBlockingQueue;

public class Task implements Runnable {
    private PriorityBlockingQueue list;
    private int id;
    public Task(int id,PriorityBlockingQueue list) {
        this.id = id;
        this.list = list;
    }
    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            Event event = new Event(id,i);
            list.add(event);
        }
    }
}
