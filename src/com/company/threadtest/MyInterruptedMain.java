package com.company.threadtest;

/**
 *总结几句：通常我们使用interrupt()来中断一个线程，不过只是将线程的状态
 *          位变为“中断状态”
 *          获取线程的中断状态有两种方式：通过调用Interrupted 和 isInterrupted
 *          两个方法的区别：虽然都是调用isInterrupted方法的重载方法，带有一个boolean类型的参数，
 *          Interrupted方法传入的是true 表示clearInterrupt 如果当前线程中断了，那么返回true 同时将线程状态清除为非中断 如果当前线程没有中断 直接返回false
 *          isInterrupted方法传入的是false 表示只返回中断状态，不会清除中断状态
 *
 */
public class MyInterruptedMain {
    public static void main(String[] args) {
       /* MyInterrupted mi = new MyInterrupted();
        mi.start();
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mi.interrupt();
    }*/
        System.out.println("--------------------------");
        Thread.currentThread().interrupt();
        System.out.println("线程已经中断，我清除了中断状态"+Thread.interrupted());
        System.out.println("调用该方法的线程中断了吗？"+Thread.interrupted());
        System.out.println("当前线程中断了吗？"+Thread.currentThread().isInterrupted());
        System.out.println("当前线程中断了吗？"+Thread.currentThread().isInterrupted());
        System.out.println("--------------------------");
        Thread.currentThread().interrupt();
        System.out.println("当前线程中断了吗？"+Thread.currentThread().isInterrupted());
        System.out.println("当前线程中断了吗？"+Thread.currentThread().isInterrupted());
        System.out.println("--------------------------");
        Thread.currentThread().interrupt();
        System.out.println("调用该方法的线程中断了吗？"+Thread.interrupted());
        System.out.println("调用该方法的线程中断了吗？"+Thread.interrupted());
    }
}
