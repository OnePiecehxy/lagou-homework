package com.lagou.bean;

public class MethodInfo {
    private String name;
    private long consuming;
    private long endTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getConsuming() {
        return consuming;
    }

    public void setConsuming(long consuming) {
        this.consuming = consuming;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}
