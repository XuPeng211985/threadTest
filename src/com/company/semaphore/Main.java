package com.company.semaphore;

/**
 * Created by hpx on 2017/10/23.
 */
public class Main {
    public static void main(String[] args) {
        Thread[] threads =  new Thread[10];
        PrintQueue printQueue = new PrintQueue();
        Job[] jobs = new Job[10];
        for(int i = 0; i < 10; i++){
            jobs[i] = new Job(printQueue);
            threads[i] = new Thread(jobs[i]);
        }
        for (int i = 0; i < 10; i++) {
            threads[i].start();
        }
    }
}
