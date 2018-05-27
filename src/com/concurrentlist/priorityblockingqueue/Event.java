package com.concurrentlist.priorityblockingqueue;

public class Event implements Comparable<Event> {
    private int thread;

    public int getThread() {
        return thread;
    }

    public int getPriority() {
        return priority;
    }

    private int priority;
    public Event(int thread, int priority) {
        this.thread = thread;
        this.priority = priority;
    }
    @Override
    public int compareTo(Event o) {
        if(this.priority > o.getPriority()){
            return 1;
        }else if(this.priority < o.getPriority()){
            return -1;
        }
        return 0;
    }
}
