package com.atomicLong;

/**
 * 原始的long 各种情况 各种有
 */
public class Main1 {
    public static void main(String[] args) {
        Account1 account = new Account1();
        Company1 company1 = new Company1(account);
        Bank1 bank1 = new Bank1(account);
        Thread companyThread = new Thread(company1);
        Thread bankThread = new Thread(bank1);
        System.out.println("转账前卡里有："+account.getBalance());
        companyThread.start();
        bankThread.start();
        try {
            companyThread.join();
            bankThread.join();
            System.out.println("转账之后卡里的钱为："+account.getBalance());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
