package xupengofmorethread;

public class 内存模型简单了解 {
    /**
     * JSR-133对JDK 5之前的旧内存模型的修补主要有两个。
     * ·增强volatile的内存语义。旧内存模型允许volatile变量与普通变量重排序。JSR-133严格
     * 限制volatile变量与普通变量的重排序，使volatile的写-读和锁的释放-获取具有相同的内存语
     * 义。
     * ·增强final的内存语义。在旧内存模型中，多次读取同一个final变量的值可能会不相同。为
     * 此，JSR-133为final增加了两个重排序规则。在保证final引用不会从构造函数内逸出的情况下，
     * final具有了初始化安全性。
     */
}
