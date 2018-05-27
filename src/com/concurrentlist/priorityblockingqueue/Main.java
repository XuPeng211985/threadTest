package com.concurrentlist.priorityblockingqueue;

import java.util.concurrent.PriorityBlockingQueue;

public class Main {
    public static void main(String[] args) {
        PriorityBlockingQueue<Event> list = new PriorityBlockingQueue<>();
        Thread[] taskThread = new Thread[5];
        for (int i = 0; i < taskThread.length; i++) {
            Task task = new Task(i,list);
            taskThread[i] = new Thread(task);
            taskThread[i].start();
        }
        for (int i = 0; i < taskThread.length; i++) {
            try {
                taskThread[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Main : list size "+list.size());
        for (int i = 0; i < taskThread.length * 1000; i++) {
            Event event = list.poll();
            System.out.println("Thread "+event.getThread()+", Priority "+event.getPriority());
        }
        System.out.println("结束了 长度 "+list.size());
    }
}
