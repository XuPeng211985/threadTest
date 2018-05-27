package com.poolxp.completionservice;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ReportProcessor implements Runnable {
    private CompletionService<String> service;

    public ReportProcessor(CompletionService<String> service) {
        this.service = service;
        this.end = false;
    }

    private boolean end;

    @Override
    public void run() {
       while(!end){
           try {
               Future<String> result = service.poll(20,TimeUnit.SECONDS) ;
               if(null != result){
                   String resport = result.get();
                   System.out.println("ReportReceiver: Report Recived: "+resport);
               }
           } catch (InterruptedException e) {
               e.printStackTrace();
           } catch (ExecutionException e) {
                   e.printStackTrace();
           }
       }
        System.out.printf("ReportSender: End\n");
    }
    public void setEnd(boolean end){
        this.end = end;
    }
}
