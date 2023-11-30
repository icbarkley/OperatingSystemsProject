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
        Utilities.print("CPU get a new process - " + "process id: " + current.pcb.returnId());
    }

    public void toExecute() {
        int id = current.pcb.returnId();
        Utilities.print("CPU is executing " + "process id: " + id);
        OS.setIdCurrentExecutingProcess(id);
        this.current.toExecute();
    }

    public void toExecute(Process process) {
        process.toExecute();
    }
}
