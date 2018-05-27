package com.company.threadtest;

/**
 * 实现Runnable接口创建一个线程，与继承Thread类有两点不同
 * 1.前者是定义线程对象后直接调用start方法 后者则需要创建线程
 *   对象后作为实参传给一个Thread对象的构造函数
 *   由这个构造函数new出的Thread对象调用start方法启动一个线程
 * 2.正因为有了1的区别 继承Thread类不能实现资源共享，因为一个线程对象只能
 *    启动一个线程  如果同时创建4个继承了Thread类的线程对象
 *    他们将分别执行各自的run方法 互不影响 尽管他们的run方法代码段内容一样
 */
public class MyRunnable implements Runnable {
    private int number = 100;
    @Override
    public void run() {
        while(true){
            if(number > 0){
                System.out.println(Thread.currentThread().getName() + "The ticket have :"+ number-- + "张" );
            }else{
                break;
            }
        }
    }
}
