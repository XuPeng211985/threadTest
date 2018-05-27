package com.company.countdownlatchtest;
import java.util.concurrent.TimeUnit;
/**
 * Created by hpx on 2017/10/23.
 */
public class Participant implements Runnable {
    Videoconference videoconference = new Videoconference(10);
    private String name;
    public Participant(Videoconference videoconference,String name) {
        this.videoconference = videoconference;
        this.name = name;
    }
    @Override
    public void run() {
     long time = (long)(Math.random()*10);
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            videoconference.arrive(name);
        }
    }
}
