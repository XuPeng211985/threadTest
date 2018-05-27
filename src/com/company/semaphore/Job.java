package com.company.semaphore;
/**
 * semaphore信号量 是一个通用的同步机制，你可以使用它在任何时候保护任意一块临界区域
 *
 */
public class Job implements Runnable {
    public Job(PrintQueue printQueue) {
        this.printQueue = printQueue;
    }

    private PrintQueue printQueue ;

    @Override
    public void run() {
       printQueue.print(new Object());
    }
}
