package com.company;

public class CPU {
    Process currentProcess;

    public CPU() {
        this.currentProcess = null;
    }

    public Process getCurrentProcess() {
        return currentProcess;
    }

    public void setCurrentProcess(Process process) {
        this.currentProcess = process;
        Utilities.print("CPU get a new process - " + "process id: " + currentProcess.processControlBlock.getId());
    }

    public void toExecute() {
        int id = currentProcess.processControlBlock.getId();
        Utilities.print("CPU is executing " + "process id: " + id);
        OperatingSystem.setIdCurrentExecutingProcess(id);
        this.currentProcess.toExecute();
    }

    public void toExecute(Process process) {
        process.toExecute();
    }
}
