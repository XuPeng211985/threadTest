package xupengofmorethread.test;
import java.util.concurrent.TimeUnit;
/**
 * 在本程序中分别采用interrupt 和 设置标志位的方法
 * 进行对线程的终止。这种终止线程的方法显得比较优雅
 * 让线程可以有时间去执行一些后处理工作。
 */
public class ShutDown {
    public static void main(String[] args) throws Exception {
        Runner one = new Runner();
        Thread countThread = new Thread(one, "CountThread");
        countThread.start();
// 睡眠1秒，main线程对CountThread进行中断，使CountThread能够感知中断而结束
        TimeUnit.SECONDS.sleep(1);
        countThread.interrupt();
        Runner two = new Runner();
        countThread = new Thread(two, "CountThread");
        countThread.start();
// 睡眠1秒，main线程对Runner two进行取消，使CountThread能够感知on为false而结束
        TimeUnit.SECONDS.sleep(1);
        two.cancel();
    }
    private static class Runner implements Runnable {
        private long i;
        //聊一聊volatile 这个on作为标记位，禁止发生重排序
        //防止发生，on没有被置为 false,线程就退出了
        //或者在on初始化前，线程想要run结果没有执行累加 出现脏数据
        private volatile boolean on = true;
        @Override
        public void run() {
            while (on && !Thread.currentThread().isInterrupted()){
                i++;
            }
            System.out.println("Count i = " + i);
        }
        public void cancel() {
            on = false;
        }
    }
}
