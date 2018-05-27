package com.company.threadtest;

/**
 *
 * Created by hpx on 2018/3/11.
 */
public class MyInterrupted extends Thread{
    @Override
    public void run() {
        long number = 1L;
        while(true) {
            if (isPrime(number)) {
                System.out.println(number + "是一个素数！");
            }
            if(isInterrupted()){
                System.out.println("线程中断.....");
                return;
            }
            number++;
        }
    }
    private boolean isPrime(long number){
        if(number <= 2){
            return true;
        }
        for (int i = 2; i < number; i++) {
            if(number % i == 0){
                return false;
            }
        }
        return true;
    }
}
