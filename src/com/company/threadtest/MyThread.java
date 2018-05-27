package com.company.threadtest;
/**
 *继承Thread类 实现多线程
 */
public class MyThread extends Thread{
    private int number  = 100;
    public void run(){
        while(true){
            if(number > 0){
                System.out.println(Thread.currentThread().getName()+"剩余票数：" + --this.number);
            }else{
                break;
            }
        }
    }
}
