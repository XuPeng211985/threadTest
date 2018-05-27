package com.company.countdownlatchtest;

/**
 * Created by hpx on 2017/10/23.
 */
public class Main {
    public static void main(String[] args) {
        Videoconference videoconference = new Videoconference(10);
        Thread ferenceThread = new Thread(videoconference);
        ferenceThread.start();
        for (int i = 0; i < 10; i++) {
            Participant participant = new Participant(videoconference,"Participant"+i);
            Thread t = new Thread(participant);
            t.start();
        }
    }
}
