package com.atomicLong;

public class Company1 implements Runnable{
    private Account1 account;

    public Company1(Account1 account) {
        this.account = account;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            this.account.addAccount(1000);
        }
    }
}
