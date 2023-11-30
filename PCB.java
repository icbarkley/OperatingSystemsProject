package com.company;

public class PCB implements iPCB {
    private int id;
    private String state; // dispatcher manage this
    private int priority; // used in priority queue method
    private String ioInfo; //Demonstrated the potential to upgrade, Will not be used in this system
    private int burstTime; // used in round robin method

    PCB() {
        this.id = 0;
        this.state = "";
        this.priority = 0;
        this.ioInfo = "";
        this.burstTime = 0;
    }

    public int returnId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getIoInfo() {
        return ioInfo;
    }

    public void setIoInfo(String ioInfo) {
        this.ioInfo = ioInfo;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    @Override
    public void printLog() {
        System.out.println("Process id: " + this.returnId() + "\nProcess Priority" + this.getPriority());
    }
}
