package com.example.test;

public class Task {

    private String desc;
    private boolean isComplete;
    private int priority;

    private static Task t=new Task();

    private Task()
    {

    }

    public static Task getInstance()
    {
        return t;
    }


    public Task(String desc, boolean isComplete, int priority) {
        this.desc = desc;
        this.isComplete = isComplete;
        this.priority = priority;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
