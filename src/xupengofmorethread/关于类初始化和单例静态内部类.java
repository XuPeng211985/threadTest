package xupengofmorethread;

public class 关于类初始化和单例静态内部类 {
    /**
     * memory = allocate();　　// 1：分配对象的内存空间
     * ctorInstance(memory);　 // 2：初始化对象
     * instance = memory;　　 // 3：设置instance指向刚分配的内存地址
     *
     *    类的初始化通常被安排在类加载之后和使用这个类之间完成
     * 而这个初始化过程是不安全的；会发生指令重排序并且执行
     * 过程中不加锁。这也是在构建单例类时的双重检查模式被否定的
     * 原因。
     *     更恰当的解决方案是使用静态内部类，对单例对象进行初始化
     * 因为JVM在对静态内部类的加载是在调用静态内部类时被初始化的
     * 不受外部类的影响。并且在对静态内部类初始化时会加一个静态内
     * 部类的class对象的锁防止初始化的过程被重排序，导致初始化对象不完整。
     *
     *     当然也可以将实例对象定义为 volatile 类型，这样在初始化时会上锁
     * 并且防止指令重排序。这样就不会出现其他线程会拿到没有初始化完成的
     * 对象实例了。
     *
     *    基于volatile的双重加锁机制相比于基于静态内部类的方法要繁琐一些
     * 但是双重加锁机制更加灵活，除了延迟初始化静态字段外还可以延迟初始化
     * 一些实例字段；但同时带来的问题是在延迟初始化的过程中，处理器将会
     * 被闲置用来等待初始化完成，这也是一个不小的开销。所以在日常开发中
     * 我们应该根据系统的性能以及程序执行速度等多方面来考虑具体该使用哪种
     * 方法来实现线程安全的单例模式。当然在新版本的JDK中引入了枚举类，由于
     * 枚举类本身自带线程安全的单例特性，可以根据枚举类简洁并且高效的实现一个
     * 漂亮的单例模式。
     */
}
