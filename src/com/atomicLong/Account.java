package com.atomicLong;
import java.util.concurrent.atomic.AtomicLong;
/**
 * 原子long
 */
public class Account {
    private AtomicLong  balance;

    public Account() {
        this.balance = new AtomicLong();
    }

    public long getBalance() {
        return balance.get();
    }

    public void setBalance(long balance) {
        this.balance.set(balance);
    }

    /**
     * 原子添加方法
     * @param amount
     */
    public void addBalance(long amount){
        this.balance.getAndAdd(amount);
    }
    public void subtractAmount(long amount){
        this.balance.getAndAdd(-amount);
    }
}
