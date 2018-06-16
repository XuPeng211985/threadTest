package xupengofmorethread.test;

/**
 * 当一个线程被设置为守护线程之后，该线程会随着程序中的最后一个
 * 非守护线程的终止而终止，所以在该例子中自定义守护线程中的
 * finally域中的代码永远不会执行，因为当主线程运行完开启自定义
 * 守护线程之后就会挂起，而此时程序中所有的非守护线程都运行结束了
 * JVM会直接退出，而不管守护线程中的代码是否执行完成
 */
public class Daemon {
    public static void main(String[] args) {
        Thread thread = new Thread(new DaemonRunner(), "DaemonRunner");
        System.out.println(Thread.currentThread().getId());
        thread.setDaemon(true);
        thread.start();
    }
    static class DaemonRunner implements Runnable {
        @Override
        public void run() {
            try {
                SleepUtils.second(3);
            } finally {
                System.out.println("DaemonThread finally run.");
            }
        }
    }
}
