package xupengofmorethread;

/**
 * 测试上下文切换带来的额外开销对程序性能的影响
 *
 * 相当于团队合作完成一项作业，如果这个作业很简单：计算一下1+1等于几  那你找
 * 三四个人的团队来完成这项任务就白白浪费了很多时间，比如一个人出来的结果还要和其他人交接对比
 * 但是如果这个任务体系比较庞大，那么单凭一个人的力量是不够的，这样就需要组建一个多人
 * 团队，一起合作完成这项任务，至于团队之间交互传递信息所浪费的时间，与节省的时间相比
 * 几乎为0；
 *
 * 总结：本程序的主要任务是累加一个数，和累减一个数
 *       在concurrency()中定义了一个子线程，由子线程
 *       来做累加，由主线程来累减；由于两个线程操作的
 *       不是同一个变量，所以不会出现数据混乱的情况
 *       在serial()中没有定义其他子线程，类加和累减都
 *       有主线程来完成
 *       由执行结果可以看出，当一个程序的计算量比较少时
 *       完成整个任务所花的时间很短暂，甚至小于两个线程
 *       之间上下文切换所用的时间，那么采用多个线程并发
 *       将会得不偿失，但是当执行的任务比较庞大时，多线程
 *       就会发挥它的优势，计算速度会是单线程执行的两倍。
 *
 *       减少上下文切换的方法：
 *       无锁并发编程。多线程竞争锁时，会引起上下文切换，所以多线程处理数据时，可以用一
 *       些办法来避免使用锁，如将数据的ID按照Hash算法取模分段，不同的线程处理不同段的数据。
 *       CAS算法 以及协程：在单线程里实现多任务的调度，并在单线程里维持多个任务间的切换。
 */
public class ConcurrentTest {
    private static final long count = 100000000L;

    /**
     * 10000
     * concurrency :3ms,b=-10000
     * serial:1ms,b=-10000,a=50000
     *
     * Process finished with exit code 0
     *
     * 100000
     * concurrency :4ms,b=-100000
     * serial:3ms,b=-100000,a=500000
     *
     * Process finished with exit code 0
     *
     * 1000000
     * concurrency :8ms,b=-1000000
     * serial:11ms,b=-1000000,a=5000000
     *
     * 100000000
     * concurrency :94ms,b=-100000000
     * serial:139ms,b=-100000000,a=500000000
     *
     * Process finished with exit code 0
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        //并发执行体
        concurrency();
        //单线程执行体
        serial();
    }

    private static void concurrency() throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //子线程执行类加
                int a = 0;
                for (long i = 0; i < count; i++) {
                    a += 5;
                }
            }
        });
        thread.start();
        //主线程执行类减
        int b = 0;
        for (long i = 0; i < count; i++) {
            b--;
        }
        //到这一句 主线程的类减任务就执行完了，但是子线程不一定执行完
        long time = System.currentTimeMillis() - start;
        //等待子线程执行完成
        thread.join();
        System.out.println("concurrency :" + time+"ms,b="+b);
    }
    private static void serial() {
        //全部由主线程来完成  同样的两份工作
        long start = System.currentTimeMillis();
        int a = 0;
        for (long i = 0; i < count; i++) {
            a += 5;
        }
        int b = 0;
        for (long i = 0; i < count; i++) {
            b--;
        }
        long time = System.currentTimeMillis() - start;
        System.out.println("serial:" + time+"ms,b="+b+",a="+a);
    }
}
