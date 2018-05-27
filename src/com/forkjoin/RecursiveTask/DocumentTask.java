package com.forkjoin.RecursiveTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RecursiveTask;

public class DocumentTask extends RecursiveTask <Integer>{
    private static final long serialVersionUID = 1L;
    //要处理的文档
    private String[][] document;
    //起始行号 和 结束行号
    private int start ,end;
    //要查找的单词
    private String word;

    public DocumentTask(String[][] document, int start, int end, String word) {
        this.document = document;
        this.start = start;
        this.end = end;
        this.word = word;
    }

    @Override
    protected Integer compute() {
        Integer result = null;
        //如果行号范围小于10 则执行统计结果
        //如果行号范围大于10 则将该任务分解为2个子问题
        if(end - start < 10){
            result = processLines(document,start,end,word);
        }else{
            int mid = (start + end) / 2;
            DocumentTask task1 = new DocumentTask(document,start,mid,word);
            DocumentTask task2 = new DocumentTask(document,mid,end,word);
            try {
                result = groupResults(task1.get(),task2.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    //处理小于10行的任务
    private Integer processLines(String[][] document, int start, int end, String word) {
       List<LineTask> tasks = new ArrayList<>();
        for (int i = start; i < end; i++) {
            //每一行的任务
            LineTask task = new LineTask(document[i],0,document.length,word);
            tasks.add(task);
        }
        invokeAll(tasks);
        Integer result = 0;
        for (int i = 0; i < tasks.size(); i++) {
            LineTask task = tasks.get(i);
            try {
                result  = result + task.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    private Integer groupResults(Integer number1, Integer number2){
        Integer result = null;
        result = number1 + number2;
        return result;
    }
}
