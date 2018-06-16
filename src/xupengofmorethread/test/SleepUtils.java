package xupengofmorethread.test;

import java.util.concurrent.TimeUnit;

public class SleepUtils {
    public static void second(long second){
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
