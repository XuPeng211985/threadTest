package com.company.cyclicbarriertest;

/**
 * Created by hpx on 2017/10/25.
 */
public class Grouper implements Runnable {
    private Results results;

    public Grouper(Results results) {
        this.results = results;
    }

    @Override
    public void run() {
        int finalResult = 0;
        int[] data = results.getData();
        for(int number : data){
            finalResult = finalResult + number;
        }
        System.out.println("最终搜索结果为：" +finalResult);
    }
}
