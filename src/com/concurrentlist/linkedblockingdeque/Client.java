package com.concurrentlist.linkedblockingdeque;

import java.util.Date;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class Client implements Runnable {
    private LinkedBlockingDeque<String>  list;

    public Client(LinkedBlockingDeque<String> list) {
        this.list = list;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                StringBuffer request = new StringBuffer();
                request.append(i);
                request.append(":");
                request.append(j);
                String s = request.toString();
                try {
                    list.put(s);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Client: "+s+" at "+new Date());
            }
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Client: end");
    }
}
