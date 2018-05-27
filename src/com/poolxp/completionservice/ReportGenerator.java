package com.poolxp.completionservice;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class ReportGenerator implements Callable<String> {
    //创建生成报表的任务
    private String sender;
    private String title;
    public ReportGenerator(String sender, String title) {
        this.sender = sender;
        this.title = title;
    }
    @Override
    public String call() throws Exception {
        Long duration = (long)(Math.random()*10);
        System.out.println(this.sender+"__"+this.title+"ReportGenerator : Generating a report during "+duration+"s");
        TimeUnit.SECONDS.sleep(duration);
        String ret = this.sender + ": " + this.title;
        return ret;
    }
}
