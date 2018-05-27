package com.company.threadtest;

/**
 * Created by hpx on 2018/3/11.
 * 继承Thread类 建立4个线程对象 分别调用它们的4个start()方法
 * 实质上是建立了4个线程分别创建了4个购票系统
 * 独立完成各自的售票操作
 */
public class MyThreadMain {
    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        MyThread myThread1 = new MyThread();
        MyThread myThread2 = new MyThread();
        MyThread myThread3 = new MyThread();
        myThread.start();
        myThread1.start();
        myThread2.start();
        myThread3.start();
       /* myThread.start();同一个Thread不能多次调用start()方法
        myThread.start();
        myThread.start();
        myThread.start();*/

    }
}
