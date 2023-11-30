package com.company;

public abstract class Process
{
    PCB processControlBlock;
    public abstract void toExecute();
    public abstract void toTerminate();
    public abstract void toWait(); // advanced function if implement waitQueue
    public abstract void toBlocked(); // advanced function if implement blockQueue
    public Process getProcessById(int id) {
        if (this.processControlBlock.retrieveId() == id) return this;
        return null;
    }
}
