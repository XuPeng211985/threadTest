package com.company.exchangertest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

public class Main {
    public static void main(String[] args) {
        /**部分输出结果：
         * Producer: Cycle: 4
         * 交互后Consumer缓冲区的产品数量为：10
         * Consumer消费了产品: Event 20
         * Producer生产了: Event 30
         * Consumer消费了产品: Event 21
         * Producer生产了: Event 31
         * Consumer消费了产品: Event 22
         * Producer生产了: Event 32
         * Exchanger相当于一个交换媒介，当生产者每在缓冲区中生产10个产品后
         * 通过Exchanger把该缓冲区中的产品交给消费者，消费者接收到该缓冲区中
         * 的产品后开始消费，但是生产者和消费者之间也就只有Exchanger这一个连接点
         * 它们在执行生产和消费过程中并没有互相影响，也就是说消费者在消费第二次交互的
         * 产品时，生产者可能正在生产第三次准备交互的产品
         */
        List<String> buffer1 = new ArrayList<>();
        List<String> buffer2 = new ArrayList<>();
        Exchanger<List<String>> exchanger = new Exchanger<>();
        Producter producter = new Producter(buffer1,exchanger);
        Consumer consumer = new Consumer(buffer2,exchanger);
        Thread pThread = new Thread(producter);
        Thread cThread = new Thread(consumer);
        pThread.start();
        cThread.start();
    }
}
