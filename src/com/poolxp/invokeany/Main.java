package com.poolxp.invokeany;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        String username = "xu";
        String password = "peng";
        // 两个验证对象
        UserValidator ldapValidator = new UserValidator("LDAP");
        UserValidator dbValidator = new UserValidator("DataBase");
        TaskValidator ldapTask = new TaskValidator(ldapValidator, username, password);
        TaskValidator dbTask = new TaskValidator(dbValidator, username, password);
        List<TaskValidator> taskList = new ArrayList<>();
        taskList.add(ldapTask);
        taskList.add(dbTask);
        ExecutorService executor = Executors.newCachedThreadPool();
        String result;
        try {
            /**
             * invokeAny将接受一系列任务，然后返回第一个完成任务的执行结果
             * 虽然isDone方法也能等待一个任务完成并返回结果。但不能确保是第一个完成的
             * 该结果类型和call方法返回的结果类型相一致
             * 在该例子中，程序会有4种不同的执行结果：
             *   两个任务都返回 true 只输出第一个完成的任务结果；
             *   第一个任务返回 false 抛出异常 第二个任务返回 true，输出第二个任务的执行结果
             *   第一个任务返回 true 第二个任务返回false 抛出异常 ，输出第一个任务的执行结果
             *   两个任务都返回false 抛出异常 没有正确输出
             *
             */
            result = executor.invokeAny(taskList);
            System.out.printf("Main: Result: %s\n", result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        // Shutdown the Executor
        executor.shutdown();
        System.out.printf("Main: End of the Execution\n");
    }
}
