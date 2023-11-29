package com.company;

import java.util.Collection;

public class OS {
    ProcessWareHouse pool;
    Scheduler scheduler;
    Dispatcher dispatcher;
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
        this.pool = new ProcessWareHouse(this);
        this.scheduler = new Scheduler(this);
        this.dispatcher = new Dispatcher(this);
        this.cpu = new CPU();
    }

    public OS(ProcessWareHouse pool, Scheduler scheduler, Dispatcher dispatcher, CPU cpu) {
        this.pool = pool;
        this.scheduler = scheduler;
        this.dispatcher = dispatcher;
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
        this.pool.connectToDispatcher(this.dispatcher);

        Utilities.printHeadLine("Scheduler: ");
        Utilities.printSubLine("Connect scheduler with PWH");
        this.scheduler.connectToProcessWareHouse(this.pool);
        Utilities.printSubLine("Connect scheduler with dispatcher");
        this.scheduler.connectToDispatcher(this.dispatcher);

        // scheduler will pick a process, based on the method
        Utilities.printHeadLine("start scheduler");
        this.scheduler.start();
        Utilities.print("print queue");
        this.scheduler.printPriorityQueue();

        Utilities.printHeadLine("Dispatcher: ");
        Utilities.printSubLine("Connect dispatcher with scheduler");
        this.dispatcher.connectToScheduler(this.scheduler);
        Utilities.printSubLine("Connect dispatcher with PWH");
        this.dispatcher.connectToProcessWareHouse(this.pool);

        Collection readyQueue = this.pool.getReadyQueue();

        while (!readyQueue.isEmpty()) {
            this.dispatcher.start();
        }
        if (allWorksAreDone) Utilities.printHeadLine("All work are done");
        else Utilities.printHeadLine("Not done");
    }

    // new process will be added to job pool then move to ready queue
    public void addProcess(Process process) {
        try {
            Utilities.printHeadLine("A new process is created in the OS");
            //Os talk to the process ware house
            this.dispatcher.changeStateToNew(process);
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
            Utilities.printErr(e.getMessage());
        }
    }
    // end of main functionalities

    // Utilities
    static boolean isPriorityQueueMethod() {return  OS.method.equals("PQ");}

    static boolean isRoundRobinMethod() {return  OS.method.equals("RR");}

    static String getMethod() {return OS.method;}


    public ProcessWareHouse getPool() {
        return pool;
    }

    public void setPool(ProcessWareHouse pool) {
        this.pool = pool;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public CPU getCpu() {
        return cpu;
    }

    public void setCpu(CPU cpu) {
        this.cpu = cpu;
    }

    //Actions
    public void changeProcessStateToReady(Process process) {
        this.dispatcher.changeStateToReady(process);
    }
}
