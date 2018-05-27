package com.company.countdownlatchtest;
import java.util.concurrent.CountDownLatch;
/**
 * Created by hpx on 2017/10/23.
 */
public class Videoconference  implements Runnable {
    private final CountDownLatch countDownLatch;
    public Videoconference(int number) {
        this.countDownLatch = new CountDownLatch(number);
    }
    public void arrive(String name){
        System.out.println("参会人员："+ name +" arrived !");
        countDownLatch.countDown();
        System.out.println( "当前还有 "+countDownLatch.getCount()+"人未到，Waiting...");
    }
    @Override
    public void run() {
        System.out.println( "参会总人数为  "+countDownLatch.getCount());
        try {
            countDownLatch.await();
            System.out.println("全体成员已到... 会议开始！");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
