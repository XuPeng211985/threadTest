package com.poolxp.callable;

import java.util.concurrent.Callable;

public class FactorialCalculator implements Callable<Integer> {
    private Integer number;

    public FactorialCalculator(Integer number) {
        this.number = number;
    }

    @Override
    public Integer call() throws Exception {
        int num;
        int result;
        num = this.number.intValue();
        result = 1;
        if(num == 0 || num == 1){
            result = 1;
        }else{
            for (int i = 2; i <= number; i++) {
                result = result * i;
                Thread.sleep(20);
            }
        }
        System.out.println(Thread.currentThread().getName()+" 执行结果为："+result);
        return result;
    }
}
