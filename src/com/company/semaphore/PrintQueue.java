package com.company.semaphore;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Created by hpx on 2017/10/23.
 */
public class PrintQueue {
    private Semaphore semaphore;
    SimpleDateFormat sim = new SimpleDateFormat("mm:ss.SSS");
    public PrintQueue(){
        semaphore = new Semaphore(2);
    }
    public void print(Object object){
        try {
            semaphore.acquire();

            System.out.println(Thread.currentThread().getName()+" 获得信号量 at:"+sim.format(new Date()));
            long time = (long)(Math.random()*10);
            TimeUnit.SECONDS.sleep(time);
            System.out.println("该队列打印工作了 ："+time+" 秒，执行者是："+Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            System.out.println(Thread.currentThread().getName()+" 释放了信号量 at:"+sim.format(new Date()));
            semaphore.release();
        }
    }
}
