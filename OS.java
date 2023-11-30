package com.company;

import java.util.Collection;

public class OS {
    ProcessStorage pool;
    Scheduler scheduler;
    systemDispatcher systemDispatcher;
    CPU cpu;

    public boolean isAllWorksAreDone() {
        return allWorksAreDone;
    }

    public void setAllWorksAreDone(boolean allWorksAreDone) {
        this.allWorksAreDone = allWorksAreDone;
    }

    public boolean allWorksAreDone;
    public static String method ;

    public static boolean isExecutingAProcess() {
        return isExecutingAProcess;
    }

    public static void setIsExecutingAProcess(boolean isExecutingAProcess) {
        OS.isExecutingAProcess = isExecutingAProcess;
    }

    public static boolean isExecutingAProcess;

    public static int getIdCurrentExecutingProcess() {
        return idCurrentExecutingProcess;
    }

    public static void setIdCurrentExecutingProcess(int idCurrentExecutingProcess) {
        OS.idCurrentExecutingProcess = idCurrentExecutingProcess;
    }

    public static int idCurrentExecutingProcess;

    public static void setMethod(String method) {
        OS.method = method;
    }

    public OS() {
        this.pool = new ProcessStorage(this);
        this.scheduler = new Scheduler(this);
        this.systemDispatcher = new systemDispatcher(this);
        this.cpu = new CPU();
    }

    public OS(ProcessStorage pool, Scheduler scheduler, systemDispatcher systemDispatcher, CPU cpu) {
        this.pool = pool;
        this.scheduler = scheduler;
        this.systemDispatcher = systemDispatcher;
        this.cpu = cpu;
    }

    public OS getOSController() {
        return this;
    }

    public void allWorksAreDone() {
        allWorksAreDone = true;
    }

    // main functionality
    public void start() {
        //connect every instances together
        Utilities.printHeadLine("PWH: ");
        Utilities.printSubLine("Connect PWH with dispatcher");
        this.pool.connectToDispatcher(this.systemDispatcher);

        Utilities.printHeadLine("Scheduler: ");
        Utilities.printSubLine("Connect scheduler with PWH");
        this.scheduler.connectToProcessStorage(this.pool);
        Utilities.printSubLine("Connect scheduler with dispatcher");
        this.scheduler.connectToDispatcher(this.systemDispatcher);

        // scheduler will pick a process, based on the method
        Utilities.printHeadLine("start scheduler");
        this.scheduler.start();
        Utilities.print("print queue");
        this.scheduler.printPriorityQueue();

        Utilities.printHeadLine("Dispatcher: ");
        Utilities.printSubLine("Connect dispatcher with scheduler");
        this.systemDispatcher.connectToScheduler(this.scheduler);
        Utilities.printSubLine("Connect dispatcher with PWH");
        this.systemDispatcher.connectToProcessWareHouse(this.pool);

        Collection readyQueue = this.pool.getReadyQueue();

        while (!readyQueue.isEmpty()) {
            this.systemDispatcher.start();
        }
        if (allWorksAreDone) Utilities.printHeadLine("All work are done");
        else Utilities.printHeadLine("Not done");
    }

    // new process will be added to job pool then move to ready queue
    public void addProcess(Process process) {
        try {
            Utilities.printHeadLine("A new process is created in the OS");
            //Os talk to the process ware house
            this.systemDispatcher.changeStateToNew(process);
            if (isPriorityQueueMethod()) {
                this.pool.addProcessToJobQueue(process);
                this.pool.removeMostCurrentProcessFromJobQueue();
                this.pool.addProcessToReadyQueue(process);
            }

            if (isRoundRobinMethod()) {
                this.pool.addProcessToJobQueue(process);
                this.pool.removeMostCurrentProcessFromJobQueue();
                this.pool.addProcessToReadyQueue(process);
            }
        }catch (Exception e) {
            Utilities.errorMsg(e.getMessage());
        }
    }
    // end of main functionalities

    // Utilities
    static boolean isPriorityQueueMethod() {return  OS.method.equals("PQ");}

    static boolean isRoundRobinMethod() {return  OS.method.equals("RR");}

    static String getMethod() {return OS.method;}


    public ProcessStorage getPool() {
        return pool;
    }

    public void setPool(ProcessStorage pool) {
        this.pool = pool;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public systemDispatcher getDispatcher() {
        return systemDispatcher;
    }

    public void setDispatcher(systemDispatcher systemDispatcher) {
        this.systemDispatcher = systemDispatcher;
    }

    public CPU getCpu() {
        return cpu;
    }

    public void setCpu(CPU cpu) {
        this.cpu = cpu;
    }

    //Actions
    public void changeProcessStateToReady(Process process) {
        this.systemDispatcher.changeStateToReady(process);
    }
}
