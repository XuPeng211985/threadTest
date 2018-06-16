package xupengofmorethread;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class Concurrent包的基石 {
    /**
     * 整个concurrent包都依赖于volatile和CAS原子指令的内存原语，
     * 由于CAS同时拥有volatile读和volatile写的内存语义；也就是说
     * 在CAS指令中，读取一个变量的值的前提是有一个线程成功的修改了
     * 共享变量的值，并且刷新到了主存中，直接读取而不用锁住内存总线
     *
     * 而写一个变量时会锁住自己的缓存行对共享变量进行修改，然后判断
     * 在修改过程中是否有其他线程对内存中的共享变量进行了修改；如有
     * 修改，那么舍弃之前的修改，重新获得主存中的值再次进行修改。正因为
     * 能很快的感知到其他线程的修改，是因为就像volatile变量一样，当其他线程
     * 修改了共享变量的值，与此同时当前执行线程中共享变量所在的缓存行将会无效
     *
     * Java线程之间的通信现有下面4种方式。
     * 1）A线程写volatile变量，随后B线程读这个volatile变量。
     * 2）A线程写volatile变量，随后B线程用CAS更新这个volatile变量。
     * 3）A线程用CAS更新一个volatile变量，随后B线程用CAS更新这个volatile变量。
     * 4）A线程用CAS更新一个volatile变量，随后B线程读这个volatile变量。
     *
     * Java的CAS会使用现代处理器上提供的高效机器级别的原子指令，这些原子指令以原子
     * 方式对内存执行读-改-写操作。volatile变量的读/写和CAS可以实现线程之间的通信。
     * 把这些特性整合在一起，就形成了整个concurrent包得以实现的基石
     * AQS举例： AbstractQueuedSynchronizer作为一个阻塞同步器，维持了java并发架构
     *       中的大多数并发控制。该类中维持了一个双向阻塞队列，该队列包含了三个
     *        volatile类型的变量属性 其中内部类Node节点中又有四个volatile类型的变量。分别如下：
     * volatile int waitStatus;//当前队列中线程的状态，
     * volatile Node prev;//前驱节点
     * volatile Node next;//后继节点
     * volatile Thread thread;//节点中所携带的线程
     * private transient volatile Node head;//双向队列的头结点
     * private transient volatile Node tail;//双向队列的尾节点
     * private volatile int state; //当前共享变量的状态，已经被线程持有还是空闲等等
     *接着拿其中的一个经典方法，添加节点来说明volatile变量与CAS的协作过程，体验一下他们的奇特之处
     * private Node addWaiter(Node mode) {
     *        //给要插入的节点绑定当前线程
     *         Node node = new Node(Thread.currentThread(), mode);
     *        //取到当前队列中的尾节点，如果尾节点为空进入enq(node)方法
     *        //在该方法中将会加入一个空的头结点，并且将新插入的节点放在头结点的后继节点上，具体就不详细说了
     *         Node pred = tail;
     *         if (pred != null) {
     *             node.prev = pred;
     *             if (compareAndSetTail(pred, node)) {
     *                 pred.next = node;
     *                 return node;
     *             }
     *         }
     *         enq(node);
     *         return node;
     *     }
     */
}
