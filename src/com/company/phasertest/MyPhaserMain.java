package com.company.phasertest;

/**
 * Created by hpx on 2017/11/8.
 */
public class MyPhaserMain {
    public static void main(String[] args) {
        MyPhaser myPhaser = new MyPhaser();
        Student[] students = new Student[5];
        for (int i = 0; i < 5; i++) {
            students[i] = new Student(myPhaser);
            myPhaser.register();
        }
        Thread threads[] = new Thread[students.length];
        for (int i = 0; i < students.length; i++) {
            threads[i] = new Thread(students[i], "Student " + i);
            threads[i].start();
        }
        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 检查Phaser是否处于终止状态
        System.out.printf("Main: The phaser has finished: %s.\n",myPhaser.isTerminated());
    }
}
