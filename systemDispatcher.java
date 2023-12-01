package com.noahbarkos;

public class systemDispatcher {
    systemScheduler taskSystemScheduler;
    systemStorage systemStorage;
    com.noahbarkos.systemCPU systemCPU;
    String method;
    OperatingSystem operatingSystemController;
    static int timeQuantum;

    public systemDispatcher(OperatingSystem operatingSystemController)
    {
        this.systemCPU = new systemCPU();
        this.operatingSystemController = operatingSystemController;
        this.method = OperatingSystem.retrieveMethod();
        timeQuantum = 1;
    }


    public void start()
    {
        if (OperatingSystem.isPriorityQueue())
        {
            startWithPriorityQueueMethod();
        }

        if (OperatingSystem.isRoundRobin())
        {
            startRoundRobin();
        }
    }

    public void startWithPriorityQueueMethod()
    {
        try
        {
            if (!OperatingSystem.isExecuting())
            {
                systemProcessManager systemProcessManager = this.retrieveProcess();
                this.systemStorage.removeProcessFromReadyQueueInLinkedListById(systemProcessManager.systemProcessControlBlock.retrieveId());
                System.out.println("|Dispatcher| Process ID: " + systemProcessManager.systemProcessControlBlock.retrieveId() + " Priority: " + systemProcessManager.systemProcessControlBlock.retrievePriority());

                changeStateToRun(systemProcessManager);
                OperatingSystem.setIsExecuting(true);
                this.systemCPU.alterCurrent(systemProcessManager);
                this.systemCPU.currentExecute();

                if (!OperatingSystem.isExecuting())
                    changeStateToFinished(systemProcessManager);

                if (systemStorage.queueContainsNothing(systemStorage.readyQueue))
                {
                    operatingSystemController.setSystemFinished(true);
                }
            }

        }
        catch (Exception e)
        {
            miscSystemProcesses.errorMsg(e.getMessage());
        }
    }

    public void startRoundRobin()
    {
            systemProcessManager systemProcessManager = this.retrieveProcess();
            System.out.println("|Dispatcher| Process ID: " + systemProcessManager.systemProcessControlBlock.retrieveId() + " Overall Time to Finish: " + systemProcessManager.systemProcessControlBlock.retrieveBurstTime());
            changeStateToRun(systemProcessManager);
            this.systemCPU.alterCurrent(systemProcessManager);
            this.systemCPU.currentExecute();
            System.out.println("\n");
            if (systemProcessManager.systemProcessControlBlock.retrieveBurstTime() > 0)
            {
                operatingSystemController.changeStateToReady(systemProcessManager);
                this.systemStorage.moveCurrentProcessToEndOfReadyQueue(systemProcessManager);
            }
            else
            {
                changeStateToFinished(systemProcessManager);
            }

            if (systemStorage.queueContainsNothing(systemStorage.readyQueue))
            {
                operatingSystemController.setSystemFinished(true);
            }
    }

    public void connectScheduler(systemScheduler connectedSystemScheduler)
    {
        try
        {
            this.taskSystemScheduler = connectedSystemScheduler;
        }
        catch (Exception e)
        {
            miscSystemProcesses.errorMsg(e.getMessage());
        }
    }

    public void connectStorage(systemStorage connectedStorage)
    {
        try
        {
            this.systemStorage = connectedStorage;
        }
        catch (Exception e)
        {
            miscSystemProcesses.errorMsg(e.getMessage());
        }
    }

    public systemProcessManager retrieveProcess()
    {
        systemProcessManager systemProcessManager = taskSystemScheduler.getProcess();
        if (systemProcessManager == null) operatingSystemController.setSystemFinished(true);
        return systemProcessManager;
    }

    public void changeStateToNew(systemProcessManager systemProcessManager)
    {
        try
        {
            systemProcessManager.systemProcessControlBlock.setState("New");
            System.out.println("|Dispatcher| Changed Process State (ID: " + systemProcessManager.systemProcessControlBlock.retrieveId() + ") Status: " + systemProcessManager.systemProcessControlBlock.retrieveState());
        }
        catch (Exception e)
        {
            miscSystemProcesses.errorMsg(e.getMessage());
        }
    }

    public void changeStateToRun(systemProcessManager systemProcessManager)
    {
        try
        {
            systemProcessManager.systemProcessControlBlock.setState("Run");
            System.out.println("|Dispatcher| Changed Process State (ID: " + systemProcessManager.systemProcessControlBlock.retrieveId() + ") Status: " + systemProcessManager.systemProcessControlBlock.retrieveState());
        }
        catch (Exception e)
        {
            miscSystemProcesses.errorMsg(e.getMessage());
        }
    }

    public void alterStateToReady(systemProcessManager systemProcessManager)
    {
        try
        {
            systemProcessManager.systemProcessControlBlock.setState("Ready");
            System.out.println("|Dispatcher| Changed Process State (ID: " + systemProcessManager.systemProcessControlBlock.retrieveId() + ") Status: " + systemProcessManager.systemProcessControlBlock.retrieveState());
        }
        catch (Exception e)
        {
            miscSystemProcesses.errorMsg(e.getMessage());
        }
    }

    public void changeStateToFinished(systemProcessManager systemProcessManager)
    {
        try
        {
            systemProcessManager.systemProcessControlBlock.setState("Finished");
            System.out.println("|Dispatcher| Changed Process State (ID: " + systemProcessManager.systemProcessControlBlock.retrieveId() + ") Status: " + systemProcessManager.systemProcessControlBlock.retrieveState());
            System.out.println("\n");
        }
        catch (Exception e)
        {
            miscSystemProcesses.errorMsg(e.getMessage());
        }
    }


}
