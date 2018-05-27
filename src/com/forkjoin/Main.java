package com.forkjoin;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
public class Main {
    public static void main(String[] args) {
        ProductListGenerator generator = new ProductListGenerator();
        List<Product> products = generator.generator(40);
        Task task = new Task(products,0,products.size(),0.2);
        ForkJoinPool pool = new ForkJoinPool();
        pool.execute(task);
        do{
            System.out.println("Main: Thread Count: "+pool.getActiveThreadCount());
            System.out.printf("Main: Thread Steal: %d\n", pool.getStealCount());
            System.out.printf("Main: Paralelism: %d\n", pool.getParallelism());
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }while(!task.isDone());
        pool.shutdown();
        //检查任务是否正常完成
        if(task.isCompletedNormally()){
            System.out.println("Main: The process has completed normally");
        }
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            if(product.getPrice() != 12){
                System.out.println(product);
            }
        }
        System.out.println("Main: End of the program");
    }
}
