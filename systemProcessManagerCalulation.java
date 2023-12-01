package com.noahbarkos;
import java.lang.Math;

public class systemProcessManagerCalulation extends systemProcessManager {
    boolean isFinished;

    public systemProcessManagerCalulation(systemPCB processControlBlock)
    {
        this.systemProcessControlBlock = processControlBlock;
        this.isFinished = false;
        System.out.println("A new calculation process has been created. (ID:" + this.systemProcessControlBlock.retrieveId() + ")");
    }

    @Override
    public void toExecution()
    {
        if (OperatingSystem.isPriorityQueue())
        {
            System.out.println("Process is now being executed (ID:" + this.systemProcessControlBlock.retrieveId());
            System.out.println("Matrix derivation: " + randomNumGen());
            System.out.println("Matrix abacus: " + randomNumGen());
            this.toTermination();
        }

        if (OperatingSystem.isRoundRobin())
        {
            System.out.println("Process (ID: " + this.systemProcessControlBlock.retrieveId() + ") is currently executed for " + systemDispatcher.timeQuantum + " time quantum.");

            int newBurstTime = this.systemProcessControlBlock.retrieveBurstTime() - 1;
            if  (newBurstTime <= 0)
            {
                System.out.println("Matrix derivation: " + randomNumGen());
                System.out.println("Matrix abacus: " + randomNumGen());
                this.toTermination();
            }
            this.systemProcessControlBlock.setBurstTime(newBurstTime);
        }
    }

    @Override
    public void toTermination()
    {
        System.out.println("Process (ID:" + this.systemProcessControlBlock.retrieveId() + ") has been terminated");
        OperatingSystem.setIsExecuting(false);
    }

    @Override
    public void toWaiting()
    {

    }

    @Override
    public void toBlocked()
    {

    }

    public int randomNumGen()
    {
        int max = 100;
        int min = 1;
        int range = max - min + 1;

        return (int)(Math.random() * range) + min;
    }
}