package com.company;

// will take a process from scheduler and change state and assign to cpu
public class Dispatcher {
    Scheduler scheduler;
    ProcessStorage processStorage;
    CPU cpu;
    String method;
    OS osController;
    static int timeQuantum; // Equal 1 by default

    public Dispatcher(OS osController) {
        //dispatcher feed process directly to cpu and force it to execute current process
        this.cpu = new CPU();
        this.osController = osController;
        this.method = OS.getMethod();
        timeQuantum = 1;
    }

    //main functionality
    public void start() {
        if (OS.isPriorityQueueMethod()) {
            startWithPriorityQueueMethod();
        }

        if (OS.isRoundRobinMethod()) {
            startWithRoundRobinMethod();
        }
    }

    public void startWithPriorityQueueMethod() {
        try {
            if (!OS.isExecutingAProcess()) {
                Process process = this.getProcessFromScheduler();
                this.processStorage.removeProcessFromReadyQueueInLinkedListById(process.pcb.getId());
                //move this to ready state
                Utilities.print("In Dispatcher: process id: " + process.pcb.getId() +
                        "- priority: " + process.pcb.getPriority());

                //move to run state and execute the process
                changeStateToRun(process);
                OS.setIsExecutingAProcess(true);
                this.cpu.setCurrent(process);
                this.cpu.toExecute();

                // move process to completed
                if (!OS.isExecutingAProcess())
                    changeStateToCompleted(process);

                if (processStorage.isQueueEmpty(processStorage.readyQueue)) {
                    osController.setAllWorksAreDone(true);
                }
            }

        } catch (Exception e) {
            Utilities.printErr(e.getMessage());
        }
    }

    public void startWithRoundRobinMethod() {
            Process process = this.getProcessFromScheduler();
            Utilities.print("In Dispatcher: process id: " + process.pcb.getId() +
                    "- total time needed to finish: " + process.pcb.getBurstTime());
            changeStateToRun(process);
            this.cpu.setCurrent(process);
            this.cpu.toExecute();
            Utilities.printBreakLine();
            if (process.pcb.getBurstTime() > 0) {
                osController.changeProcessStateToReady(process);
                this.processStorage.moveCurrentProcessToTheEndOfReadyQueue(process);
            } else {
                changeStateToCompleted(process);
            }

            if (processStorage.isQueueEmpty(processStorage.readyQueue)) {
                osController.setAllWorksAreDone(true);
            }
    }

    // end main functionality

    // connect with other component
    public void connectToScheduler(Scheduler scheduler) {
        try {
            this.scheduler = scheduler;
        } catch (Exception e) {
            Utilities.printErr(e.getMessage());
        }
    }

    public void connectToProcessWareHouse(ProcessStorage processWareHouse) {
        try {
            this.processStorage = processWareHouse;
        } catch (Exception e) {
            Utilities.printErr(e.getMessage());
        }
    }

    public Process getProcessFromScheduler() {
        Process process = scheduler.getProcess();
        if (process == null) osController.setAllWorksAreDone(true);
        return process;
    }
    // end of connection function

    // Change state functionality
    public void changeStateToNew(Process process) {
        try {
            process.pcb.setState("New");
            Utilities.printHeadLine("In Dispatcher: Change the process with id " +
                    process.pcb.getId() + " to state " + process.pcb.getState());
        } catch (Exception e) {
            Utilities.printErr(e.getMessage());
        }
    }

    public void changeStateToRun(Process process) {
        try {
            process.pcb.setState("Run");
            Utilities.printHeadLine("In Dispatcher: Change the process with id " +
                    process.pcb.getId() + " to state " + process.pcb.getState());
        } catch (Exception e) {
            Utilities.printErr(e.getMessage());
        }
    }

    public void changeStateToReady(Process process) {
        try {
            process.pcb.setState("Ready");
            Utilities.printHeadLine("In Dispatcher: Change the process with id " +
                    process.pcb.getId() + " to state " + process.pcb.getState());
        } catch (Exception e) {
            Utilities.printErr(e.getMessage());
        }
    }

    public void changeStateToCompleted(Process process) {
        try {
            process.pcb.setState("Completed");
            Utilities.printHeadLine("In Dispatcher: Change the process with id " +
                    process.pcb.getId() + " to state " + process.pcb.getState());
            Utilities.printBreakLine();
        } catch (Exception e) {
            Utilities.printErr(e.getMessage());
        }
    }

    //end change state functions
}
