package com.company.cyclicbarriertest;

import java.util.Random;

/**
 * Created by hpx on 2017/10/25.
 */
public class MatrixMock {
    private int[][] data;
    public MatrixMock(int size,int length,int number){
        data = new int[size][length];
        int count =  0;
        Random random = new Random();
        for (int i = 0; i < size ; i++) {
            for (int j = 0; j < length; j++) {
                data[i][j] = random.nextInt(10);
                if(data[i][j] == number){
                    count++;
                }
            }
        }
        System.out.println("该数组中 " + number + " 共出现了" + count + " 次");
    }

    public int[] getRow(int row){
        if(row >= 0 && row < data.length){
            return data[row];
        }else{
            return null;
        }
    }
}
