package com.company.phasertest;

import java.util.concurrent.Phaser;

/**
 * Created by hpx on 2017/11/8.
 */
public class MyPhaser extends Phaser {
    /**
     *
     * protected boolean onAdvance(int phase, int registeredParties) {
     *         return registeredParties == 0;
     *     }
     * @param phase
     * @param registeredParties
     * @return
     */

    @Override
    protected boolean onAdvance(int phase, int registeredParties) {
        switch(phase){
            case 0 :
                return studentsArrived();
            case 1:
                return finishFirstExercise();
            case 2:
                return finishSecondExercise();
            case 3:
                return finishExam();
            default:
                return true;
        }
    }

    public boolean studentsArrived(){
        System.out.println("考试将要开始，学生做好准备...");
        System.out.println("该考场应到 : "+getRegisteredParties()+"人");
        return false;
    }
    public boolean finishFirstExercise(){
        System.out.println("第一阶段考试完毕...");
        System.out.println("准备进入下一阶段...");
        return false;
    }
    public boolean finishSecondExercise(){
        System.out.println("第二阶段完毕...");
        System.out.println("准备进入下一阶段...");
        return false;
    }
    public boolean finishExam(){
        System.out.println("考试完毕，考生离场..");
        return true;
    }
}
