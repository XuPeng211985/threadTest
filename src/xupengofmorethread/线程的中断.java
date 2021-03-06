package xupengofmorethread;

public class 线程的中断 {
    /**
     * interrupt就不说了，聊聊已经过期的：
     *      暂停suspend()、恢复resume() 和 停止stop()
     *  为什么不建议使用的原因是：使用suspend()暂停一个线程时不会释放
     *    自己所持有的资源，比如对象锁；进入休眠后如果没有及时恢复。其他
     *    线程想要获得已经被持有的对象锁时，就会进入死等待；产生死锁。
     *    同样，stop()方法在终结一个线程时不会保证线程的资源正常释放，通
     *    常是没有给予线程完成资源释放工作的机会，因此会导致程序可能工作
     *    在不确定状态下。
     */
}
