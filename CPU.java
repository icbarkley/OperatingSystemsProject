package com.company;

public class CPU {
    Process current;

    public CPU() {
        this.current = null;
    }

    public Process getCurrent() {
        return current;
    }

    public void setCurrent(Process process) {
        this.current = process;
        Utilities.print("CPU get a new process - " + "process id: " + current.processControlBlock.getId());
    }

    public void toExecute() {
        int id = current.processControlBlock.getId();
        Utilities.print("CPU is executing " + "process id: " + id);
        OperatingSystem.setIdCurrentExecutingProcess(id);
        this.current.toExecute();
    }

    public void toExecute(Process process) {
        process.toExecute();
    }
}
