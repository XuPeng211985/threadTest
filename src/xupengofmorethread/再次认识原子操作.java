package xupengofmorethread;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class 再次认识原子操作 {
    /**
     * 原子操作是指一个不可分割操作或是多个操作组成的一个整体不可分割
     * 就相当于在高并发秒杀系统中的执行秒杀操作在数据库中分为两步，首先
     * 查找所要秒杀的商品是它的库存数减1 然后将秒杀明细添加到明细表中
     * 减库存 和 添加明细这两个动作就必须是原子的，因为如果减库存了而没有添加
     * 明细，那么在最后整理账单是就会出现偏差，卖掉的商品数和秒杀明细单中的‘
     * 统计结果不一样；同样如果是添加了明细而没有减库存也会出现同样的冲突。

     * 在java中通过处理器的CMPXCHG交换指令和自旋的CAS操作实现某一个变量的原子操作
     * 基本思路是当一个线程想要获得共享变量的锁时，会不断循环CAS直到成功修改
     * 变量的值并且刷新到主存中。
     *
     * CAS存在的三个问题：
     *    1 ABA问题，由于CAS在循环判断的时候只关注在修改变量后
     *      内存中的变量值是否和修改前一致，而不能发现变量会被
     *      某个线程跳跃性修改，就是说在修改过程中将内存中的变量值
     *      修改后又改变为原来的值，而当前执行CAS操作的线程会以为当
     *      前变量的值没有发生变化，放行CAS操作返回true 而实际上变量
     *      在修改当前线程做修改的过程中，其他线程修改了该变量两次。
     *      解决ABA问题的可行方法是在进行CAS操作的变量中添加一个属性
     *      标识版本，这样每次CAS时在比较内存值和版本号是否相同，如果
     *      同时满足那么才能说明当前线程没有被修改。
     *
     *     2 循环时间长开销大。自旋CAS如果长时间不成功，会给CPU带来非常大的执行开销
     *
     */
    private AtomicInteger atomicI = new AtomicInteger(0);
    private int i = 0;

    public static void main(String[] args) {
        final 再次认识原子操作 cas = new 再次认识原子操作();
        List<Thread> ts = new ArrayList<>(100);
        long start = System.currentTimeMillis();
        //开启100个执行线程 对同一个对象中的变量进行累加操作
        for (int j = 0; j < 100; j++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10000; i++) {
                        //执行非原子的i++
                        cas.count();
                        //执行具有原子性的i++
                        cas.safeCount();
                    }
                }
            });
            ts.add(t);
        }
        for (Thread t : ts) {
            t.start();
        }
// 等待所有线程执行完成
        for (Thread t : ts) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(cas.i);
        System.out.println(cas.atomicI.get());
        System.out.println(System.currentTimeMillis() - start);
    }

    /**
     * 使用CAS实现线程安全计数器
     */
    private void safeCount() {
        for (; ; ) {
            int i = atomicI.get();//拿到当前内存中的i的值到自己的高速缓存中
            boolean suc = atomicI.compareAndSet(i, ++i);//执行加1操作，并且判断此时内存中i的值和刚开始取出来的值是否一致
                                                       //如果一致，将修改后的值刷新到内存 并返回true
                                                       //如果不一致，则返回false重新取值 并重新加1
            if (suc) {
                break;
            }
        }
    }

    /**
     * 非线程安全计数器
     */
    private void count() {
        i++;
    }
}
