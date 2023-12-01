package com.noahbarkos;

public class systemCPU {
    systemProcessManager current;

    public systemCPU()
    {
        this.current = null;
    }

    public systemProcessManager retrieveCurrent()
    {
        return current;
    }

    public void alterCurrent(systemProcessManager systemProcessManager)
    {
        this.current = systemProcessManager;
        System.out.println("CPU Obtained A New Process " + " (Process ID: " + current.systemProcessControlBlock.retrieveId() + ")");
    }

    public void currentExecute()
    {
        int id = current.systemProcessControlBlock.retrieveId();
        System.out.println("CPU is Currently Executing " + "(Process ID: " + id + ")");
        OperatingSystem.setProcessID(id);
        this.current.toExecution();
    }

    public void currentExecute(systemProcessManager systemProcessManager)
    {
        systemProcessManager.toExecution();
    }
}
