package com.company.phasertest;

import java.util.concurrent.Phaser;

/**
 * Created by hpx on 2017/10/30.
 */
public class Main {
    public static void main(String[] args) {
        Phaser phaser = new Phaser(3);
        FileSearch f1 = new FileSearch("E:\\C","txt",phaser);
        FileSearch f2 = new FileSearch("E:\\as","jar",phaser);
        FileSearch f3 = new FileSearch("F:\\资源————许鹏\\java","pdf",phaser);
        Thread thread_f1 = new Thread(f1);
        thread_f1.start();
        Thread thread_f2 = new Thread(f2);
        thread_f2.start();
        Thread thread_f3 = new Thread(f3);
        thread_f3.start();
        try {
            thread_f1.join();
            thread_f2.join();
            thread_f3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("Terminated: %s\n", phaser.isTerminated());
    }
}
