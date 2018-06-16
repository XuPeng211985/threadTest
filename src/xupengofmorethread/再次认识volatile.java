package xupengofmorethread;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.atomic.AtomicReference;

public class 再次认识volatile {
    /**
     * volatile是轻量级的synchronized,它在多处理器系统中为
     * 各线程提供了某些变量的可见性，也就是说当一个线程在
     * 修改一个被volatile修饰的变量后，其他线程会立刻感知到
     * 变量的具体变化，这样就减少了线程之间的上下文切换，不用
     * 频繁的获得变量的锁，就会感知到它的变化。除了在修改时需要获得
     * 该对象的锁以外，其他情况下都不需要对象锁。
     *
     * CPU术语：
     *   缓存（读）命中：如果下一次程序需要修改的内容所在的内存地址和当前缓存行中的地址一致
     *             那么就不需要从主存中提取数据，而是直接在高速缓存中读取数据
     *   写命中：同样的道理，如果一个线程想要修改一块内存的数据，它会先检查缓存
     *          行中是否存在没有失效的缓存行指向这块内存，如果有那么直接在缓存行
     *          写入，而不用改动主存中的数据，当然下次有其他线程修改时，这块缓存行
     *          肯定失效了，需要从主存中copy一份到缓存再进行修改。
     *
     *volatile实现可见性的方法：
     *    Java代码如下。
     *    instance = new Singleton(); // instance是volatile变量
     *    转变成汇编代码，如下。
     *    0x01a3de1d: movb $0×0,0×1104800(%esi);0x01a3de24: lock addl $0×0,(%esp);
     *   如上面的例子所示，被volatile修饰的变量在进行修改时，jvm 会对该变量加一个锁前缀
     *   这个锁前缀除了在修改值时保证排他性的同时还会为该变量加一个已经修改了的标识
     *   这个标识会被其他线程看到，其他线程想要访问这个变量时就会感知到该变量的
     *   变化，肯定了缓存行无效后，会从主存中重新读取该变量新的值。
     *   换言之，被volatile修饰的变量只要已被修改，就会立马更新到主存，并且通知
     *   其他线程关于这个变量的缓存行失效。
     *
     *   ============需要注意的是在为volatile变量上锁的时候，一般情况下锁住
     *   的是当前线程所在的缓存而不是地址总线内存，这样做也是很好理解的：
     *   锁住总线意味着其他CPU都不能访问这块内存，将会降低系统吞吐率。如果锁
     *   住的只是当前线程的缓存行，那么受限制的范围会缩小一点。
     *
     *
     *   使用优化： LinkedTransferQueue 中由于头结点和尾节点都是四字节
     *         的volatile类型的共享变量；而在很多处理器中不支持部分缓存
     *         也就是说如果缓存行是64字节的，那么你想要修改的这部分变量
     *         要么足够长填充满 整个缓冲行，要么就将该变量所在的其他属性都拿过来
     *         来填补；所以如果某个线程想要修改某个队列的头结点，必须在缓存行引入
     *         该队列的所有属性，包括它的头结点和尾节点；这是就会有一个问题，
     *         当线程修改头结点时需要锁住当前变量所在的缓存行，那么同时也就
     *         锁住了该队列的尾节点，此时其他线程想要修改该队列的尾节点就需要
     *         等待，而实际上尾节点只是被拉去做配成的，根本就不会被修改，所以也
     *         不应该被上锁；为了解决这个问题，在LinkedTransferQueue会为头结点和
     *         尾节点都做追加操作，那么头结点只要被追加为64字节和缓存行的长度
     *         一样，那么上锁的时候就不会影响尾节点的其他操作，会在一定程度上提高
     *         程序的运行效率，进而提高系统吞吐率。
     *
     *   需要注意的是，以下两种情况下不能使用追加：
     *  ·缓存行非64字节宽的处理器。
     *  ·共享变量不会被频繁地写。不频繁写就说明 在短暂的锁住尾节点的过程中
     *    基本上不会有其他线程来修改尾节点，此时就不用采用追加白白浪费系统空间了。
     *
     *
     * Lock前缀的内存语义：
     *        对缓存行上锁或者对内存总线上锁
     *        禁止指令重排
     *        将修改后的值刷新到主存中
     *
     *  CAS同时具有volatile读和volatile写的内存语义。
     *
     */
}

