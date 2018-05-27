package com.poolxp.done;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * 继承FutureTask 重写done方法，在任务被取消之后，可以对其进行一些收尾工作
 */
public class ResultTask  extends FutureTask<String> {
    private String name;
    public ResultTask(Callable<String> callable) {
        super(callable);
       //取得任务的名称
        this.name =((ExecutableTask)callable).getName();
    }
//可以在任务被取消后做一些后续处理工作
    //例如日志打印 那些任务在取消之前就完成了 那些任务还没有完成就被取消了
    @Override
    protected void done() {
        if (isCancelled()) {
            //中途被取消
            System.out.printf("%s: Has been cancelled\n", name);
        } else {
            //正常完成
            System.out.printf("%s: Has finished\n", name);
        }
    }
}
