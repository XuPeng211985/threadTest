package com.forkjoin;
import java.util.List;
import java.util.concurrent.RecursiveAction;
/**
 * 任务一般可继承RecursiveTask和RecursiveAction
 * 前者重写方法时有返回值 后者则没有返回值
 */
public class Task extends RecursiveAction {
    //序列化 以防执行更新操作时能准确找到初始化的对象 然后修改后的序列化字节码能准确的
    //转化为对象
    private static final long serialVersionUID = 1L;

    public Task(List<Product> products, int first, int last, double increment) {
        this.products = products;
        this.first = first;
        this.last = last;
        this.increment = increment;
    }
    private List<Product>  products;
    @Override
    public String toString() {
        return "Task{" +
                "first=" + first +
                ", last=" + last +
                '}';
    }
    private int first;
    private int last;
    private double increment;
    @Override
    protected void compute() {
        if(last - first < 10){
            updatePrices();
        }else{
            int middle = (last + first) / 2;
            System.out.println("Task: Panding tasks: "+getQueuedTaskCount());
            Task t1 = new Task(products,first,middle+1,increment);
            Task t2 = new Task(products,middle+1,last,increment);
            System.out.println("T1: "+t1);
            System.out.println("T2: "+t2);
            invokeAll(t1,t2);
        }
    }
    private void updatePrices(){
        System.out.println("first: "+this.first + "  last: "+this.last);
        System.out.println();
        for (int i = first; i < last; i++) {
            Product product = products.get(i);
            product.setPrice(product.getPrice() * (1+increment));
        }
    }
}
