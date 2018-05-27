package com.company.exchangertest;

import java.util.List;
import java.util.concurrent.Exchanger;

public class Producter implements Runnable {
    private List<String> buffer;

    /**
     * 定义一个Exchanger对象 泛型类型为缓冲区的类型
     */
    private final Exchanger<List<String>> exchanger;

    public Producter(List<String> buffer,Exchanger<List<String>> exchanger) {
        this.buffer = buffer;
        this.exchanger = exchanger;
    }
    @Override
    public void run() {
       int cycle = 1;
        /**
         * 进行5次循环交互
         */
        for (int i = 0; i < 5; i++) {
            System.out.println("Producer: Cycle: "+cycle);
            for (int j = 0; j < 10; j++) {
                String message = "Event "+((i*10)+j);
                System.out.println("Producer生产了: "+message);
                buffer.add(message);
            }
            /**
             * 通过Exchanger将生产好的产品缓冲区与消费者交互
             */
            try {
                buffer = exchanger.exchange(buffer);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("交互后生产者的缓冲区的产品个数："+buffer.size());
            cycle++;
        }
    }
}
