package xupengofmorethread.test;

/**
 * "BlockedThread-2" #14 prio=5 os_prio=0 tid=0x00000000166d3000 nid=0x2ec0 waiting for monitor entry [0x000000001750f000]
 *    java.lang.Thread.State: BLOCKED (on object monitor)
 *         at xupengofmorethread.test.ThreadState$Blocked.run(ThreadState.java:43)
 *         - waiting to lock <0x00000000eb5c7900> (a java.lang.Class for xupengofmorethread.test.ThreadState$Blocked)
 *         at java.lang.Thread.run(Thread.java:745)
 * //两个线程竞争同一个对象的监视器，结果"BlockedThread-1"获得了锁，进入了监视器托管的区域 然后进入休眠状态
 *  //此时BlockedThread-2将会进入死等待的阻塞状态，因为他需要获得Blocked对象的锁才可以进入监视区域执行
 *  //而不行的是线程1获得锁后进入死循环，永远也不会释放对象的锁。
 * "BlockedThread-1" #13 prio=5 os_prio=0 tid=0x00000000166d1800 nid=0x3254 waiting on condition [0x000000001740e000]
 *    java.lang.Thread.State: TIMED_WAITING (sleeping)
 *         at java.lang.Thread.sleep(Native Method)
 *         at java.lang.Thread.sleep(Thread.java:340)
 *         at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
 *         at xupengofmorethread.test.SleepUtils.second(SleepUtils.java:8)
 *         at xupengofmorethread.test.ThreadState$Blocked.run(ThreadState.java:43)
 *         - locked <0x00000000eb5c7900> (a java.lang.Class for xupengofmorethread.test.ThreadState$Blocked)
 *         at java.lang.Thread.run(Thread.java:745)
 *
 * "WaitingThread" #12 prio=5 os_prio=0 tid=0x00000000166c9800 nid=0x2d4c in Object.wait() [0x000000001730f000]
 *    //该线程会在一个对象的监视器的获取权上等待
 *    java.lang.Thread.State: WAITING (on object monitor)
 *         at java.lang.Object.wait(Native Method)
 *         - waiting on <0x00000000eb5c49f0> (a java.lang.Class for xupengofmorethread.test.ThreadState$Waiting)
 *         at java.lang.Object.wait(Object.java:502)
 *         at xupengofmorethread.test.ThreadState$Waiting.run(ThreadState.java:29)
 *         - locked <0x00000000eb5c49f0> (a java.lang.Class for xupengofmorethread.test.ThreadState$Waiting)
 *         at java.lang.Thread.run(Thread.java:745)
 *
 * "TimeWaitingThread" #11 prio=5 os_prio=0 tid=0x00000000166c4000 nid=0x1108 waiting on condition [0x000000001720f000]
 *    //该线程会和线程2一样进入休眠状态  但不同的是该线程不需要获得对象锁
 *    java.lang.Thread.State: TIMED_WAITING (sleeping)
 *         at java.lang.Thread.sleep(Native Method)
 *         at java.lang.Thread.sleep(Thread.java:340)
 *         at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
 *         at xupengofmorethread.test.SleepUtils.second(SleepUtils.java:8)
 *         at xupengofmorethread.test.ThreadState$TimeWaiting.run(ThreadState.java:17)
 *         at java.lang.Thread.run(Thread.java:745)
 */
public class ThreadState {
    public static void main(String[] args) {
        new Thread(new TimeWaiting(), "TimeWaitingThread").start();
        new Thread(new Waiting(), "WaitingThread").start();
      // 使用两个Blocked线程，一个获取锁成功，另一个被阻塞
        new Thread(new Blocked(), "BlockedThread-1").start();
        new Thread(new Blocked(), "BlockedThread-2").start();
    }

    // 该线程不断地进行睡眠
    static class TimeWaiting implements Runnable {
        @Override
        public void run() {
            while (true) {
                SleepUtils.second(100);
            }
        }
    }

    // 该线程在Waiting.class实例上等待
    static class Waiting implements Runnable {
        @Override
        public void run() {
            while (true) {
                synchronized (Waiting.class) {
                    try {
                        Waiting.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    // 该线程在Blocked.class实例上加锁后，不会释放该锁
    static class Blocked implements Runnable {
        public void run() {
            synchronized (Blocked.class) {
                while (true) {
                    SleepUtils.second(100);
                }
            }
        }
    }
}
