package com.company;

public abstract class Process
{
    PCB pcb;
    public abstract void toExecute();
    public abstract void toTerminate();
    public abstract void toWait();
    public abstract void toBlocked();
    public Process getProcessById(int id) {
        if (this.pcb.retrieveId() == id) return this;
        return null;
    }
}
