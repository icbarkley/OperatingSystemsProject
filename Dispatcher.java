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
                this.processStorage.removeProcessFromReadyQueueInLinkedListById(process.processControlBlock.getId());
                //move this to ready state
                Utilities.print("In Dispatcher: process id: " + process.processControlBlock.getId() +
                        "- priority: " + process.processControlBlock.getPriority());

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
            Utilities.print("In Dispatcher: process id: " + process.processControlBlock.getId() +
                    "- total time needed to finish: " + process.processControlBlock.getBurstTime());
            changeStateToRun(process);
            this.cpu.setCurrent(process);
            this.cpu.toExecute();
            Utilities.printBreakLine();
            if (process.processControlBlock.getBurstTime() > 0) {
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
            process.processControlBlock.setState("New");
            Utilities.printHeadLine("In Dispatcher: Change the process with id " +
                    process.processControlBlock.getId() + " to state " + process.processControlBlock.getState());
        } catch (Exception e) {
            Utilities.printErr(e.getMessage());
        }
    }

    public void changeStateToRun(Process process) {
        try {
            process.processControlBlock.setState("Run");
            Utilities.printHeadLine("In Dispatcher: Change the process with id " +
                    process.processControlBlock.getId() + " to state " + process.processControlBlock.getState());
        } catch (Exception e) {
            Utilities.printErr(e.getMessage());
        }
    }

    public void changeStateToReady(Process process) {
        try {
            process.processControlBlock.setState("Ready");
            Utilities.printHeadLine("In Dispatcher: Change the process with id " +
                    process.processControlBlock.getId() + " to state " + process.processControlBlock.getState());
        } catch (Exception e) {
            Utilities.printErr(e.getMessage());
        }
    }

    public void changeStateToCompleted(Process process) {
        try {
            process.processControlBlock.setState("Completed");
            Utilities.printHeadLine("In Dispatcher: Change the process with id " +
                    process.processControlBlock.getId() + " to state " + process.processControlBlock.getState());
            Utilities.printBreakLine();
        } catch (Exception e) {
            Utilities.printErr(e.getMessage());
        }
    }

    //end change state functions
}
