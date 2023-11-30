package com.company;

import java.util.Collection;

public class OS
{
    systemProcessStorage pool;
    systemScheduler systemScheduler;
    systemDispatcher systemDispatcher;
    CPU cpu;

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

    public static boolean isExecutingAProcess()
    {
        return isExecutingAProcess;
    }

    public static void setIsExecutingAProcess(boolean isExecutingAProcess)
    {
        OS.isExecutingAProcess = isExecutingAProcess;
    }

    public static boolean isExecutingAProcess;

    public static int getIdCurrentExecutingProcess()
    {
        return idCurrentExecutingProcess;
    }

    public static void setIdCurrentExecutingProcess(int idCurrentExecutingProcess)
    {
        OS.idCurrentExecutingProcess = idCurrentExecutingProcess;
    }

    public static int idCurrentExecutingProcess;

    public static void setMethod(String method)
    {
        OS.method = method;
    }

    public OS()
    {
        this.pool = new systemProcessStorage(this);
        this.systemScheduler = new systemScheduler(this);
        this.systemDispatcher = new systemDispatcher(this);
        this.cpu = new CPU();
    }

    public OS(systemProcessStorage pool, systemScheduler systemScheduler, systemDispatcher systemDispatcher, CPU cpu)
    {
        this.pool = pool;
        this.systemScheduler = systemScheduler;
        this.systemDispatcher = systemDispatcher;
        this.cpu = cpu;
    }

    public OS getOSController()
    {
        return this;
    }

    public void allWorksAreDone()
    {
        systemFinished = true;
    }

    // main functionality
    public void start()
    {
        //connect every instances together
        System.out.println("||Process Warehouse||");
        System.out.println("{Connect PWH with dispatcher}");
        this.pool.dispatcherConnection(this.systemDispatcher);

        System.out.println("||Scheduler||");
        System.out.println("{Connect scheduler with PWH}");
        this.systemScheduler.connectToProcessStorage(this.pool);
        System.out.println("{Connect scheduler with dispatcher}");
        this.systemScheduler.connectToDispatcher(this.systemDispatcher);

        // scheduler will pick a process, based on the method
        System.out.println("||Start Scheduler||");
        this.systemScheduler.start();
        System.out.println("print queue");
        this.systemScheduler.printPriorityQueue();

        System.out.println("||Dispatcher||");
        System.out.println("{Connect dispatcher with scheduler}");
        this.systemDispatcher.schedulerConnection(this.systemScheduler);
        System.out.println("{Connect dispatcher with PWH}");
        this.systemDispatcher.processWarehouseConnection(this.pool);

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

    // new process will be added to job pool then move to ready queue
    public void addProcess(Process process)
    {
        try
        {
            System.out.println("||New process has been created in the Operating System||");

            this.systemDispatcher.changeStateToNew(process);

            if (isPriorityQueueMethod())
            {
                this.pool.addProcessToJobQueue(process);
                this.pool.removeMostCurrentProcessFromJobQueue();
                this.pool.addProcessToReadyQueue(process);
            }

            if (isRoundRobinMethod())
            {
                this.pool.addProcessToJobQueue(process);
                this.pool.removeMostCurrentProcessFromJobQueue();
                this.pool.addProcessToReadyQueue(process);
            }
        }
        catch (Exception e)
        {
            Utilities.errorMsg(e.getMessage());
        }
    }



    static boolean isPriorityQueueMethod()
    {
        return  OS.method.equals("PQ");
    }

    static boolean isRoundRobinMethod()
    {
        return  OS.method.equals("RR");
    }

    static String retrieveMethod()
    {
        return OS.method;
    }


    public systemProcessStorage retrievePool()

    {
        return pool;
    }

    public void setPool(systemProcessStorage pool)
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

    public CPU retrieveCPU()
    {
        return cpu;
    }

    public void setCpu(CPU cpu)
    {
        this.cpu = cpu;
    }

    public void changeProcessStateToReady(Process process)
    {
        this.systemDispatcher.stateAlterToReady(process);
    }
}
