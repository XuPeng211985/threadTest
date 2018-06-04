package xupengofmorethread;
/**
 * 文章主题：内置锁相关知识，以及常见优化锁的实现以及应用场景介绍
 * 目录索引：
 *    1.synchronized引入，使用场景介绍
 *    2.synchronized修饰方法，修饰代码块的不同
 *    3.内置锁的定义，以及轻量级锁的简单介绍
 */
public class SynchronizedTest implements Runnable{
    /**
     *1.synchronized引入，使用场景介绍
     *   在介绍synchronized之前需要说明一下临界区这个概念，临界区是指
     *   一个被监管起来的一片代码片段，这个片段里面包含对共享变量的修改
     *   行为，在多线程环境下同一时刻只能有一个线程进入临界区，其他线程
     *   只能等待临界区中的线程执行完成后退出临界区才可以进入临界区。
     *   java中用synchronized来将一个代码块锁住变为临界区，当某个方法或
     *   代码块被synchronized修饰后，同一时刻只能有一个线程执行该代码块
     *   中的代码，其余线程想要进入代码块就必须阻塞等待；但是其余线程可
     *   以执行没有上锁的代码块。java中的synchronized作为每个对象的内置
     *   锁相当于一种互斥锁，这意味着最多只有一个线程能持有这种锁。被锁
     *   保护的代码块将会具有原子性和可见性。synchronized长被用于实现多线程
     *   对一个对象的同步访问，比如常见的同步容器SynchronizedHashMap SynchronizedArrayList等。
     * 2.synchronized修饰方法，修饰变量，修饰代码块的不同
     *    2.1.当一个方法被synchronized修饰，表明该方法已经被加锁，任何线程
     *        想要执行该方法都需要判断当前有没有其他线程已经独占了这个类实例
     *        对象的锁，如果其他线程已经持有对象锁进入了该方法执行，那么其他线程
     *        就必须阻塞，直到当前实例对象的锁被释放。当然，通常情况下直接锁住一个
     *        方法，会大大影响程序的执行效率，进一步会降低系统的吞吐率，因为当被锁住的
     *        方法特别大时，一个线程获得锁然后执行需要很长一段时间，而在这段时间里
     *        其他线程想要执行该方法就必须阻塞等待直到执行线程释放对象锁。最关键的是通常
     *        情况下修改共享变量的代码可能只是方法中的某几行，而不是一整个方法，所以
     *        为了优化对一个方法上锁带来的性能问题，引入了代码块；
     *    2.2.被synchronized修饰的代码块的功能和锁住一个方法的功能相同，都是为了实现
     *        多线程能够同步访问共享变量，但是代码块锁住的往往是方法中的一小部分，这样就
     *        缩小了临界区的范围，获得锁的线程在临界区的执行时间会变短，进而其他线程等待
     *        获得锁的时间就会变短，这样就会比锁住一整个方法要好的多，可以在一定程度上
     *        提高系统吞吐率。一般形式如下：
     *        synchronized(this){
     *            //临界区
     *        }
     *        synchronized修饰代码块时有如下规则：当一个线程获得了当前对象的锁，进入一个同步
     *        代码块，那么其他线程除了不能进入当前代码块以外，还不能进入所有关于该对象的其他
     *        同步代码块，这样也很好理解，因为同一时刻只能有一个线程获得该对象的锁，其他线程
     *        想要进入关于该对象的其他同步代码块也必须获得该对象锁，但是自始至终一个对象只有
     *        一个锁。this关键字的语义是指当前调用或者执行该方法的实例对象。
     * 3.内置锁的定义，以及轻量级锁的简单介绍
     *        3.1 在上面提到的synchronized相当于获得java对象锁的一种方式，为了实现同步访问，java
     *         提供了一种内置的锁机制，为每个对象都设置了一个内置锁。值的注意的是这些锁都是可重入
     *         的，意思是说当一个线程调用一个同步容器的添加方法，其他线程想要执行这个
     *         添加方法就需要阻塞，但是如果是已经获得该对象锁的线程想要在此执行添加操作，那是完全
     *         可以的，同样很好理解，当一个线程操作一个同步容器时，需要将该容器从主存中copy一份
     *         到自己的工作内存（高速缓存）中，那么对容器的任何修改对其他线程是不可见的，但是
     *         对自己是完全开放的，所以不用担心，在连续修改容器状态时造成一致性或脏读等问题。
     *         重入锁的实现方法是JVM为每一个对象的锁都定义一个计数器，当缩空闲时为0 ，有一个线程
     *         获得该对象的锁，那么该计数器加1，如果当前线程在此尝试在此获得该对象的锁，被允许的
     *         同时，锁计数器会在此加1，当然释放锁时该线程必须释放两次才能使该锁变为空闲状态。
     *        3.2 需要明确的几个细节：1.无论synchronized关键字加在方法上还是锁住一个对象，获得的
     *          都是对象锁，在锁代码块时如果传入的的参数是classname.class形式，那么此时锁住的是
     *          该类的class实例对象。
     *
     */
    private String lock;//全局对象

    public SynchronizedTest(String lock) {
        this.lock = lock;
    }

    @Override
    public synchronized void run() {
        synchronized (lock) {
            for (int i = 0; i < 3; i++) {
                System.out.println(Thread.currentThread().getName() + ": run......");
            }
        }
    }
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(new SynchronizedTest("lock")).start();
        }
        /**
         * 加入全局变量之后的输出结果：3  3 3 3 3
         * Thread-0: run......
         * Thread-0: run......
         * Thread-0: run......
         * Thread-4: run......
         * Thread-4: run......
         * Thread-4: run......
         * Thread-3: run......
         * Thread-3: run......
         * Thread-3: run......
         * Thread-2: run......
         * Thread-2: run......
         * Thread-2: run......
         * Thread-1: run......
         * Thread-1: run......
         * Thread-1: run......
         *
         * Process finished with exit code 0
         */
       /* for (int i = 0; i < 5; i++) {
            new Thread(new SynchronizedTest()).start();
        }
        */

        /**
         * 执行结果并不是一个线程run三次然后接着下一个线程run
         * 因为这里synchronized锁住的是不同的5个Thread对象，他们各自持有各自的锁
         * 互不影响，所以并不能实现这几个线程的同步，解决方案是在Test类中定义一个
         * 全局变量，让每一个线程执行run方法时都获得该全局变量的锁，这样就可以实现
         * 多个线程争用同一个锁，真正意义上实现同步。
         * Thread-1: run......
         * Thread-2: run......
         * Thread-2: run......
         * Thread-4: run......
         * Thread-4: run......
         * Thread-3: run......
         * Thread-4: run......
         * Thread-0: run......
         * Thread-0: run......
         * Thread-0: run......
         * Thread-2: run......
         * Thread-1: run......
         * Thread-3: run......
         * Thread-1: run......
         * Thread-3: run......
         *
         * Process finished with exit code 0
         */
    }
}