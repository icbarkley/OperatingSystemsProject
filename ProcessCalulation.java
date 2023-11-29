package com.company;
import java.lang.Math;

public class ProcessCalulation extends Process {
    //    ProcessControlBlock processControlBlock;
    boolean isFinished;

    public ProcessCalulation(PCB processControlBlock) {
        this.processControlBlock = processControlBlock;
        this.isFinished = false;
        Utilities.print("A new calculate process with id:" + this.processControlBlock.getId() + " is created.");
    }

    @Override
    public void toExecute() {
        if (OS.isPriorityQueueMethod()) {
            Utilities.print("Process id:" + this.processControlBlock.getId()
                    + " is being executed");


            Utilities.print("The matrix derivation is " + randomNumber());
            Utilities.print("The matrix abacus is " + randomNumber());
            this.toTerminate();
        }

        if (OS.isRoundRobinMethod()) {
            Utilities.print("Process id:" + this.processControlBlock.getId()
                    + " is being executed for " + Dispatcher.timeQuantum + " time quantum.");
            int newBurstTime = this.processControlBlock.getBurstTime() - 1;
            if  (newBurstTime <= 0) {
                Utilities.print("The matrix derivation is " + randomNumber());
                Utilities.print("The matrix abacus is " + randomNumber());
                this.toTerminate();
            }
            this.processControlBlock.setBurstTime(newBurstTime);
        }
    }

    @Override
    public void toTerminate() {
        Utilities.print("Process id:" + this.processControlBlock.getId()
                + " is terminated");
        OS.setIsExecutingAProcess(false);
    }

    @Override
    public void toWait() {

    }

    @Override
    public void toBlocked() {

    }

    public int randomNumber() {
        int max = 100;
        int min = 1;
        int range = max - min + 1;

        return (int)(Math.random() * range) + min;
    }
}