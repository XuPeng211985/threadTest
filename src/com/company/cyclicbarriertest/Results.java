package com.company.cyclicbarriertest;

/**
 * Created by hpx on 2017/10/25.
 */
public class Results {
    public Results(int size) {
        data = new int[size];
    }

    private int[] data;
    public void setData(int row,int value){
        this.data[row] = value;
    }
    public int[] getData(){
        return data;
    }

}
