package com.noahbarkos;

import java.util.Collection;

public class OperatingSystem
{
    systemStorage pool;
    systemScheduler systemScheduler;
    systemDispatcher systemDispatcher;
    systemCPU systemCpu;

    public boolean isSystemFinished()
    {
        return systemFinished;
    }

    public void setSystemFinished(boolean systemFinished)
    {
        this.systemFinished = systemFinished;
    }

    public boolean systemFinished;
    public static String method ;

    public static boolean isExecuting()
    {
        return isExecuting;
    }

    public static void setIsExecuting(boolean isExecuting)
    {
        OperatingSystem.isExecuting = isExecuting;
    }

    public static boolean isExecuting;

    public static int getProcessID()
    {
        return processID;
    }

    public static void setProcessID(int processID)
    {
        OperatingSystem.processID = processID;
    }

    public static int processID;

    public static void setMethod(String method)
    {
        OperatingSystem.method = method;
    }

    public OperatingSystem()
    {
        this.pool = new systemStorage(this);
        this.systemScheduler = new systemScheduler(this);
        this.systemDispatcher = new systemDispatcher(this);
        this.systemCpu = new systemCPU();
    }

    public OperatingSystem(systemStorage pool, systemScheduler systemScheduler, systemDispatcher systemDispatcher, systemCPU systemCpu)
    {
        this.pool = pool;
        this.systemScheduler = systemScheduler;
        this.systemDispatcher = systemDispatcher;
        this.systemCpu = systemCpu;
    }

    public OperatingSystem getOSController()
    {

        return this;
    }

    public void workDone()
    {

        systemFinished = true;
    }

    public void start()
    {
        System.out.println("||Storage||");
        System.out.println("{Connect storage with dispatcher}");
        this.pool.dispatcherConnection(this.systemDispatcher);

        System.out.println("||Scheduler||");
        System.out.println("{Connect scheduler with storage}");
        this.systemScheduler.connectToProcessStorage(this.pool);
        System.out.println("{Connect scheduler with dispatcher}");
        this.systemScheduler.connectToDispatcher(this.systemDispatcher);

        System.out.println("||Start Scheduler||");
        this.systemScheduler.start();
        System.out.println("print queue");
        this.systemScheduler.printPriorityQueue();

        System.out.println("||Dispatcher||");
        System.out.println("{Connect dispatcher with scheduler}");
        this.systemDispatcher.connectScheduler(this.systemScheduler);
        System.out.println("{Connect dispatcher with storage}");
        this.systemDispatcher.connectStorage(this.pool);

        Collection readyQueue = this.pool.getReadyQueue();

        while (!readyQueue.isEmpty())
        {
            this.systemDispatcher.start();
        }
        if (systemFinished)
            System.out.println("||All tasks are complete||");
        else
            System.out.println("||Still working...||");
    }

    public void addProcess(systemProcessManager systemProcessManager)
    {
        try
        {
            System.out.println("||New process has been created in the Operating System||");

            this.systemDispatcher.changeStateToNew(systemProcessManager);

            if (isPriorityQueue())
            {
                this.pool.addProcessToJobQueue(systemProcessManager);
                this.pool.removeMostCurrentProcessFromJobQueue();
                this.pool.addProcessToReadyQueue(systemProcessManager);
            }

            if (isRoundRobin())
            {
                this.pool.addProcessToJobQueue(systemProcessManager);
                this.pool.removeMostCurrentProcessFromJobQueue();
                this.pool.addProcessToReadyQueue(systemProcessManager);
            }
        }
        catch (Exception e)
        {
            miscSystemProcesses.errorMsg(e.getMessage());
        }
    }



    static boolean isPriorityQueue()
    {

        return  OperatingSystem.method.equals("PQ");
    }

    static boolean isRoundRobin()
    {
        return  OperatingSystem.method.equals("RR");
    }

    static String retrieveMethod()
    {
        return OperatingSystem.method;
    }


    public systemStorage retrievePool()

    {
        return pool;
    }

    public void setPool(systemStorage pool)
    {
        this.pool = pool;
    }

    public systemScheduler retrieveScheduler()
    {
        return systemScheduler;
    }

    public void setScheduler(systemScheduler systemScheduler)
    {
        this.systemScheduler = systemScheduler;
    }

    public systemDispatcher retrieveDispatcher()
    {
        return systemDispatcher;
    }

    public void setDispatcher(systemDispatcher systemDispatcher)
    {
        this.systemDispatcher = systemDispatcher;
    }

    public systemCPU retrieveCPU()
    {
        return systemCpu;
    }

    public void setCpu(systemCPU systemCpu)
    {
        this.systemCpu = systemCpu;
    }

    public void changeStateToReady(systemProcessManager systemProcessManager)
    {
        this.systemDispatcher.alterStateToReady(systemProcessManager);
    }
}
