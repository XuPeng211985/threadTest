package com.company.cyclicbarriertest;

import java.util.concurrent.CyclicBarrier;

/**
 * Created by hpx on 2017/10/25.
 */
public class Main {
    public static void main(String[] args) {
        MatrixMock matrixMock = new MatrixMock(1000,1000,6);
        Results results = new Results(1000);
        Grouper grouper = new Grouper(results);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5,grouper);
        Searcher[] searchers = new Searcher[5];
        for(int i = 0; i < 5; i++){
            searchers[i] = new Searcher(matrixMock,results,i*200,(i*200+200),6,cyclicBarrier);
            Thread thread = new Thread(searchers[i]);
            thread.start();
        }
    }
}
