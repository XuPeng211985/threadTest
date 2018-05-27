package com.concurrentlist.linkeddeque;

import java.util.concurrent.ConcurrentLinkedDeque;

/**
 *
  getFirst()和getLast()：这些方法返回分别(respectively)返回列表中的第一个和最后一个元素.
 它们不删除列表中返回的元素.如果列表是空的,这些方法将抛出NoSuchElementExcpetion异常.

  peek(), peekFirst()和 peekLast()：这些方法分别返回列表中的第一个和最后一个元素.
 它们不删除列表中返回的元素.如果列表是空的,这些方法返回null值.

 remove(), removeFirst(), removeLast()：这些方法分别返回列表中的第一个和最后一个元素.
 它们删除从列表中返回的元素.如果列表是空的,这些方法将抛出NoSuchElementException异常.
 */
public class Main {
    public static void main(String[] args) {
        ConcurrentLinkedDeque<String> list = new ConcurrentLinkedDeque();
        Thread[] threads = new Thread[100];
        for (int i = 0; i < threads.length; i++) {
            AddTask addTask = new AddTask(list);
            threads[i] = new Thread(addTask);
            threads[i].start();
        }
        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (String ss : list
             ) {
            System.out.println(ss);
        }
        System.out.println("添加后list的长度为： "+list.size());
        System.out.println("添加完成，开始删除");
        for (int i = 0; i < threads.length; i++) {
            PollTask task = new PollTask(list);
            threads[i] = new Thread(task);
            threads[i].start();
        }
        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("删除后list的长度为："+list.size());
    }
}
