package com.atomicLong;
public class Main {
    public static void main(String[] args) {
        Account account = new Account();
        account.setBalance(1000);
        Company company = new Company(account);
        Thread companyThread = new Thread(company);
        Bank bank = new Bank(account);
        Thread bankThread = new Thread(bank);
        System.out.println("卡里原来有："+ account.getBalance());
         companyThread.start();
         bankThread.start();
        try {
            companyThread.join();
            bankThread.join();
            System.out.println("10次交换转账后结果为：" + account.getBalance());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
