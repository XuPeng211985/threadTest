package com.atomicLong;

public class Bank1 implements Runnable {
    private Account1 account;
    public Bank1(Account1 account) {
        this.account = account;
    }
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            this.account.subtractAmount(1000);
        }
    }
}
