package xupengofmorethread;
/**
 * 文章主题：阻塞同步器 （相当于等锁缓冲区）AQS
 * 1.数据结构
 * 2.入队列
 * 3.出队列
 * 4.注意的细节
 *
 * AbstractQueuedSynchronizer  内部维护了一个双向队列，
 * 队列中的而每个节点都包含一个int类型的status表示当前线程是否应该阻塞；
 * 比如ReentrantLock是它的一个子类，当调用lock()方法时就相当于默认调用了
 * 父类的acquire，那么：
 * 线程1调用lock()执行acquire()方法，发现当前节点的status为0 那么获取锁成功
 * 将status设置为1，此时线程2再次调用lock()执行acquire()，发现当前节点的status为1
 * 没有获得该锁的条件，所以直接返回false 获取锁失败
 *
 * AbstractQueuedSynchronizer 中的获取执行资格的实现：
 *  public final void acquire(long arg) {
 *    //tryAcquire(arg)由子类实现，如果返回true说明当前线程获得了执行资格
 *    //当然就不用进入阻塞队列等待，反之如果返回false说明执行资格获取失败
 *    //需要加入阻塞队列
 *         if (!tryAcquire(arg) &&
 *         //添加到阻塞队列后再次调用尝试获得执行资格，如果
 *         //任然没有条件获得执行资格，那么该线程阻塞中断
 *             acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
 *             selfInterrupt();
 *     }
 *
 * 接下来再看一下向阻塞队列中添加节点的实现：
 *     private Node addWaiter(Node mode) {
 *     //绑定当前线程为一个新的节点
 *         Node node = new Node(Thread.currentThread(), mode);
 *         // Try the fast path of enq; backup to full enq on failure
 *         //获取阻塞队列中的尾节点
 *         Node pred = tail;
 *         if (pred != null) {
 *         //如果尾节点不为空，说明当前队列中已经有其他线程
 *             node.prev = pred;
 *             //让当前节点的前驱指向尾节点，并且采用CAS算法将该节点
 *             //设置为该队列的尾节点
 *             if (compareAndSetTail(pred, node)) {
 *                 pred.next = node;
 *                 //旧尾节点的下一个节点指向当前插入的节点
 *                 return node;
 *             }
 *         }
 *         //如果当前尾节点为空，那么说明队列中还没有执行的线程
 *         enq(node);
 *         return node;
 *     }
 *如果当前阻塞队列中没有任何节点，那么利用CAS算法将一个无状态的
 *一个新节点设置为当前队列的头，并且将尾指针指向头结点接着会将当前请求入队列的节点
 *采用CAS算法设置为当前队列的尾节点：
 *     private Node enq(final Node node) {
 *         for (;;) {
 *             Node t = tail;
 *             if (t == null) { // Must initialize
 *                 if (compareAndSetHead(new Node()))
 *                     tail = head;
 *             } else {
 *                 node.prev = t;
 *                 if (compareAndSetTail(t, node)) {
 *                     t.next = node;
 *                     return t;
 *                 }
 *             }
 *         }
 *     }
 *    创建好队列后，再看一下如何从队列中取出一个节点：
 *    当进入队列后会再次请求执行权利，相当于自旋（jvm的默认形式）
 *    因为该队列是为高并发场景设计，肯定假设当前存在频繁切换锁
 *    持有者，通过自旋可以减少系统阻塞一个线程并重新唤起它的额外
 *    开销
 *        final boolean acquireQueued(final Node node, int arg) {
 *         boolean failed = true;
 *         try {
 *             boolean interrupted = false;
 *             for (;;) {
 *                 final Node p = node.predecessor();
 *                 //获得当前节点的前驱节点，并且让当前节点尝试获取执行权利
 *                 if (p == head && tryAcquire(arg)) {
 *                 //如果获取成功，让当前节点称为队列的头节点
 *                     setHead(node);
 *                     //垃圾回收之前的头结点
 *                     p.next = null; // help GC
 *                     failed = false;
 *                     return interrupted;
 *                 }
 *                 if (shouldParkAfterFailedAcquire(p, node) &&
 *                     parkAndCheckInterrupt())
 *                     interrupted = true;
 *             }
 *         } finally {
 *             if (failed)
 *                 cancelAcquire(node);
 *         }
 *     }
 *     总之队列的头结点总表示当前已经获得 执行权利的线程
 *     它的下一个节点是下一次有权利请求获得执行权利的线程
 *
 *     队列中的阻塞线程被唤醒时，都要进行中断检测，如果线程
 *     已经中断了，那么抛出异常，程序退出；并不是被唤醒的线程
 *     就一定会获得锁，必须再次调用tryAcquire尝试获得锁，这里
 *     体现了jvm内置锁的非公平性。
 *
 *     当队列中的一个线程要出队列时，当前线程的waitStatus必须是小于0
 *     此时，将下个节点所持有的线程唤醒（调用unpark()）
 */
public class AQSTest {
    public static void main(String[] args) {
    }
}
