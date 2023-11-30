package com.company;

public class CPU {
    Process current;

    public CPU()
    {
        this.current = null;
    }

    public Process getCurrent()
    {
        return current;
    }

    public void setCurrent(Process process)
    {
        this.current = process;
        System.out.println("CPU Obtained A New Process " + " (Process ID: " + current.pcb.retrieveId() + ")");
    }

    public void toExecute()
    {
        int id = current.pcb.retrieveId();
        System.out.println("CPU is Currently Executing " + "(Process ID: " + id + ")");
        OS.setProcessID(id);
        this.current.toExecute();
    }

    public void toExecute(Process process)
    {
        process.toExecute();
    }
}
