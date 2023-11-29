package com.company;
import java.lang.Math;

public class CalculateProcess extends Process {
    //    ProcessControlBlock processControlBlock;
    boolean isFinished;

    public CalculateProcess(PCB processControlBlock) {
        this.processControlBlock = processControlBlock;
        this.isFinished = false;
        Utilities.print("A new calculate process with id:" + this.processControlBlock.getId() + " is created.");
    }

    @Override
    public void toExecute() {
        if (OperatingSystem.isPriorityQueueMethod()) {
            Utilities.print("Calcualte process id:" + this.processControlBlock.getId()
                    + " is being executed");


            Utilities.print("The matrix derivation is " + generateRandNum());
            Utilities.print("The matrix abacus is " + generateRandNum());
            this.toTerminate();
        }

        if (OperatingSystem.isRoundRobinMethod()) {
            Utilities.print("Calcualte process id:" + this.processControlBlock.getId()
                    + " is being executed for " + Dispatcher.timeQuantum + " time quantum.");
            int newBurstTime = this.processControlBlock.getBurstTime() - 1;
            if  (newBurstTime <= 0) {
                Utilities.print("The matrix derivation is " + generateRandNum());
                Utilities.print("The matrix abacus is " + generateRandNum());
                this.toTerminate();
            }
            this.processControlBlock.setBurstTime(newBurstTime);
        }
    }

    @Override
    public void toTerminate() {
        Utilities.print("Calcualte process id:" + this.processControlBlock.getId()
                + " is terminated");
        OperatingSystem.setIsExecutingAProcess(false);
    }

    @Override
    public void toWait() {

    }

    @Override
    public void toBlocked() {

    }

    public int generateRandNum() {
        int max = 100;
        int min = 1;
        int range = max - min + 1;

        return (int)(Math.random() * range) + min;
    }
}