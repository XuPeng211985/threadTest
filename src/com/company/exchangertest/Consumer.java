package com.company.exchangertest;
import java.util.List;
import java.util.concurrent.Exchanger;
public class Consumer implements Runnable {
    private List<String> buffer ;
    private final Exchanger<List<String>> exchanger;
    public Consumer(List<String> buffer, Exchanger<List<String>> exchanger) {
        this.buffer = buffer;
        this.exchanger = exchanger;
    }
    @Override
    public void run() {
        int cycle = 1;
        for (int i = 0; i < 5; i++) {
            System.out.println("Consumer: Cycle"+cycle);
            try {
                buffer =  exchanger.exchange(buffer);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("交互后Consumer缓冲区的产品数量为："+buffer.size());
            for (int j = 0; j < 10; j++) {
                String message = buffer.get(0);
                System.out.println("Consumer消费了产品: "+message);
                buffer.remove(0);
            }
            cycle++;
        }
    }
}
