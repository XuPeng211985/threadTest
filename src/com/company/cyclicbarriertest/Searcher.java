package com.company.cyclicbarriertest;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
/**
 * Created by hpx on 2017/10/25.
 */
public class Searcher implements Runnable {
    private  MatrixMock matrixMock;
    private  Results results;
    private  int firstrow;

    private  int lastrow;
    private  int number;

    public Searcher(MatrixMock matrixMock,Results results, int firstrow, int lastrow, int number, CyclicBarrier cyclicBarrier) {
        this.matrixMock = matrixMock;
        this.results = results;
        this.firstrow = firstrow;
        this.lastrow = lastrow;
        this.number = number;
        this.cyclicBarrier = cyclicBarrier;
    }

    private  final CyclicBarrier cyclicBarrier ;

    @Override
    public void run() {
       int count;
        System.out.println(Thread.currentThread().getName() +"从"+ firstrow +"到"+lastrow +"行搜索");
        for (int i = firstrow; i < lastrow ; i++) {
            int[] row = matrixMock.getRow(i);
            count = 0;//每行计算结束  更新count
            for (int j = 0; j < row.length ; j++) {
                if(row[j] == number){
                    count++;
                }
            }
            results.setData(i,count);
        }
        System.out.println(Thread.currentThread().getName()+"搜索完成...");
        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
