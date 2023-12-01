package com.noahbarkos;

public abstract class systemProcessManager
{
    systemPCB systemProcessControlBlock;
    public abstract void toExecution();
    public abstract void toTermination();
    public abstract void toWaiting();
    public abstract void toBlocked();
    public systemProcessManager getProcessById(int id)
    {
        if (this.systemProcessControlBlock.retrieveId() == id) return this;
        return null;
    }
}
