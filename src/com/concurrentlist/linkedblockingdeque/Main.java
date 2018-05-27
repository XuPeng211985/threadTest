package com.concurrentlist.linkedblockingdeque;

import java.util.Date;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * put 和 take 是一对阻塞式的操作函数
 * 由于list的固定大小为3，当队列中元素添加满时put方法将阻塞 直到有空位置为止
 * 当队列为空时take方法将阻塞 直到队列中有元素为止
 *
 * 除此之外还提供了非阻塞式的操作函数：
 takeFirst()和takeLast()：这些方法分别返回列表中第一个和最后一个元素.
 它们将删除从列表中返回的元素.如果列表是空的.这些方法将阻塞直到列表中有元素.

 getFirst()和getLast()：这些方法分别返回列表中第一个和最后一个元素.
 它们不删除从列表中返回的元素.如果列表是空的, 这些方法将抛出一个NoSuchElementExcpetion异常.


 peek(),peekFirst()和peekLast()：这些方法分别返回列表中的第一个和最后一个元素.
 它们不删除从列表中返回的元素.如果列表是空的,这些方法返回一个null值.

 pool(),poolFirst()和poolLast()：这些方法分别返回列表中的第一个和最后一个元素.
 它们删除从列表中返回的元素.如果列表是空的,这些方法返回一个null值.

 add(),addFirst(),addLast()：这些方法分别在第一个位置和最后一个位置增加元素.
 如果列表是满的(你创建了一个固定容量),这些方法将抛出IllegalStateException异常.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        LinkedBlockingDeque<String> list = new LinkedBlockingDeque<>(3);
        Client client = new Client(list);
        Thread thread = new Thread(client);
        thread.start();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                try {
                    String request = list.take();
                    System.out.println("Main: Request "+request+" 已经收到 at "+new Date());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            TimeUnit.MILLISECONDS.sleep(800);
        }
    }
}
