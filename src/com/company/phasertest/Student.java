package com.company.phasertest;

import java.util.Date;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * Created by hpx on 2017/11/8.
 */
public class Student implements Runnable {
    private Phaser phaser;
    public Student(Phaser phaser) {
        this.phaser = phaser;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"到达考场，在 "+ new Date());
        phaser.arriveAndAwaitAdvance();
        System.out.println(Thread.currentThread().getName()+"开始答第一阶段，在 "+new Date());
        doExercise1();
        System.out.println(Thread.currentThread().getName()+"第一阶段答题完毕，在 "+new Date());
        phaser.arriveAndAwaitAdvance();
        System.out.println(Thread.currentThread().getName()+"开始答第二阶段，在 "+ new Date());
        doExercise2();
        System.out.println(Thread.currentThread().getName()+"第二阶段答题完毕，在 "+ new Date());
        phaser.arriveAndAwaitAdvance();
        System.out.println(Thread.currentThread().getName()+"开始答第三阶段，在 "+ new Date());
        doExercise3();
        System.out.println(Thread.currentThread().getName()+"第三阶段答题完毕，在 "+new Date());
        phaser.arriveAndAwaitAdvance();

        System.out.println();
    }
    private void doExercise1() {
        try {
            Long duration = (long) (Math.random() * 10);
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void doExercise2() {
        try {
            Long duration = (long) (Math.random() * 10);
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void doExercise3() {
        try {
            Long duration = (long) (Math.random() * 10);
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
