package com.atomicLong;
public class Account1 {
    public Account1() {
        this.balance = 1000;
    }
    /**
     * 普通long类型
     */
    private long balance;
    public long getBalance() {
        return balance;
    }
    public void setBalance(long balance) {
        this.balance = balance;
    }
    public void addAccount(long amount){
        this.balance += amount;
    }
    public void subtractAmount(long amount){
        this.balance += - amount;
    }
}
