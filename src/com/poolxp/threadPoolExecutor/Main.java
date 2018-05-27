package com.poolxp.threadPoolExecutor;

/**
 * 关于线程池：通常情况下，执行大量任务时我们需要创建很多的线程
 *       如果任务量特别大，大量线程被创建，可惜某一时刻只能有一个线程
 *       获得CPU执行任务，其他线程将等待获得CPU。这种情况下会大大
 *       影响应用程序的吞吐量 ，极端情况下还可能会使系统饱和
 *       并且在创建线程之后，还要实现对所有线程对象的管理（创建 结束 获取结果 三者之间的关联）
 * 为了解决以上这些问题引入了线程池：
 *        有了线程池之后，开发人员只需要创建一定数量的线程任务交给它，线程池会实现自动创建一定
 *        数量的线程去执行这些线程任务，以及这些线程的调度和分配任务都由线程池来完成
 *        线程池中会根据线程的状态和执行的任务进行合理分配，某些线程执行完自己的任务，
 *        则放在等待区，如果某一时间又需要执行这个线程，则把它调入到执行等待区
 */
public class Main {
    public static void main(String[] args) {
        Server server = new Server();
        for (int i = 0; i < 100; i++) {
            Task task = new Task("Task "+i);
            server.executeTesk(task);
        }
        server.endServer();
    }
}
