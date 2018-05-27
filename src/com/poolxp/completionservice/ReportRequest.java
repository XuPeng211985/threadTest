package com.poolxp.completionservice;

import java.util.concurrent.CompletionService;

public class ReportRequest implements Runnable {
    private String name;
    private CompletionService<String> service;
    public ReportRequest(String name, CompletionService<String> service) {
        this.name = name;
        this.service = service;
    }
    @Override
    public void run() {
        // ReportGenerator
        ReportGenerator reportGenerator = new ReportGenerator(name, "Report");
        // 使用CompletionService提交任务
        service.submit(reportGenerator);
    }
}
