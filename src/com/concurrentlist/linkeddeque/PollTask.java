package com.concurrentlist.linkeddeque;

import java.util.concurrent.ConcurrentLinkedDeque;

public class PollTask implements Runnable {
    public PollTask(ConcurrentLinkedDeque<String> linkedDeque) {
        this.linkedDeque = linkedDeque;
    }

    private ConcurrentLinkedDeque<String> linkedDeque;
//每个线程删5000次  每次头尾删两个元素
    @Override
    public void run() {
        for (int i = 0; i < 5000; i++) {
            linkedDeque.pollFirst();
            linkedDeque.pollLast();
        }
    }
}
