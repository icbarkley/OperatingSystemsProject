package com.company;
import java.lang.Math;

public class ProcessCalulation extends Process
{
    //    ProcessControlBlock processControlBlock;
    boolean isFinished;

    public ProcessCalulation(PCB processControlBlock)
    {
        this.pcb = processControlBlock;
        this.isFinished = false;
        System.out.println("A new calculation process has been created. (ID:" + this.pcb.retrieveId() + ")");
    }

    @Override
    public void toExecute()
    {
        if (OS.isPriorityQueue())
        {
            System.out.println("Process is now being executed (ID:" + this.pcb.retrieveId());
            System.out.println("Matrix derivation: " + randomNumGen());
            System.out.println("Matrix abacus: " + randomNumGen());
            this.toTerminate();
        }

        if (OS.isRoundRobin())
        {
            System.out.println("Process (ID: " + this.pcb.retrieveId() + ") is currently executed for " + systemDispatcher.timeQuantum + " time quantum.");

            int newBurstTime = this.pcb.retrieveBurstTime() - 1;
            if  (newBurstTime <= 0)
            {
                System.out.println("Matrix derivation: " + randomNumGen());
                System.out.println("Matrix abacus: " + randomNumGen());
                this.toTerminate();
            }
            this.pcb.setBurstTime(newBurstTime);
        }
    }

    @Override
    public void toTerminate()
    {
        System.out.println("Process (ID:" + this.pcb.retrieveId() + ") has been terminated");
        OS.setIsExecuting(false);
    }

    @Override
    public void toWait()
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