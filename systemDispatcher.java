package com.company;

public class systemDispatcher {
    systemScheduler taskSystemScheduler;
    systemProcessStorage systemProcessStorage;
    CPU systemCPU;
    String method;
    OS osController;
    static int timeQuantum;

    public systemDispatcher(OS osController)
    {
        this.systemCPU = new CPU();
        this.osController = osController;
        this.method = OS.retrieveMethod();
        timeQuantum = 1;
    }


    public void start()
    {
        if (OS.isPriorityQueue())
        {
            startWithPriorityQueueMethod();
        }

        if (OS.isRoundRobin())
        {
            startWithRoundRobinMethod();
        }
    }

    public void startWithPriorityQueueMethod()
    {
        try
        {
            if (!OS.isExecuting())
            {
                Process process = this.retrieveProcessFromScheduler();
                this.systemProcessStorage.removeProcessFromReadyQueueInLinkedListById(process.processControlBlock.retrieveId());
                System.out.println("|Dispatcher| Process ID: " + process.processControlBlock.retrieveId() + " Priority: " + process.processControlBlock.retrievePriority());

                stateAlterToRun(process);
                OS.setIsExecuting(true);
                this.systemCPU.setCurrent(process);
                this.systemCPU.toExecute();

                if (!OS.isExecuting())
                    setStateAsCompleted(process);

                if (systemProcessStorage.queueContainsNothing(systemProcessStorage.readyQueue))
                {
                    osController.setSystemFinished(true);
                }
            }

        }
        catch (Exception e)
        {
            Utilities.errorMsg(e.getMessage());
        }
    }

    public void startWithRoundRobinMethod()
    {
            Process process = this.retrieveProcessFromScheduler();
            System.out.println("|Dispatcher| Process ID: " + process.processControlBlock.retrieveId() + " Overall Time to Finish: " + process.processControlBlock.retrieveBurstTime());
            stateAlterToRun(process);
            this.systemCPU.setCurrent(process);
            this.systemCPU.toExecute();
            Utilities.printBreakLine();
            if (process.processControlBlock.retrieveBurstTime() > 0)
            {
                osController.changeStateToReady(process);
                this.systemProcessStorage.moveCurrentProcessToEndOfReadyQueue(process);
            }
            else
            {
                setStateAsCompleted(process);
            }

            if (systemProcessStorage.queueContainsNothing(systemProcessStorage.readyQueue))
            {
                osController.setSystemFinished(true);
            }
    }

    public void schedulerConnection(systemScheduler connectedSystemScheduler)
    {
        try
        {
            this.taskSystemScheduler = connectedSystemScheduler;
        }
        catch (Exception e)
        {
            Utilities.errorMsg(e.getMessage());
        }
    }

    public void processWarehouseConnection(systemProcessStorage connectedProcessWarehouse)
    {
        try
        {
            this.systemProcessStorage = connectedProcessWarehouse;
        }
        catch (Exception e)
        {
            Utilities.errorMsg(e.getMessage());
        }
    }

    public Process retrieveProcessFromScheduler()
    {
        Process process = taskSystemScheduler.getProcess();
        if (process == null) osController.setSystemFinished(true);
        return process;
    }

    public void changeStateToNew(Process process)
    {
        try
        {
            process.processControlBlock.setState("New");
            System.out.println("|Dispatcher| Changed Process State (ID: " + process.processControlBlock.retrieveId() + ") Status: " + process.processControlBlock.retrieveState());
        }
        catch (Exception e)
        {
            Utilities.errorMsg(e.getMessage());
        }
    }

    public void stateAlterToRun(Process process)
    {
        try
        {
            process.processControlBlock.setState("Run");
            System.out.println("|Dispatcher| Changed Process State (ID: " + process.processControlBlock.retrieveId() + ") Status: " + process.processControlBlock.retrieveState());
        }
        catch (Exception e)
        {
            Utilities.errorMsg(e.getMessage());
        }
    }

    public void stateAlterToReady(Process process)
    {
        try
        {
            process.processControlBlock.setState("Ready");
            System.out.println("|Dispatcher| Changed Process State (ID: " + process.processControlBlock.retrieveId() + ") Status: " + process.processControlBlock.retrieveState());
        }
        catch (Exception e)
        {
            Utilities.errorMsg(e.getMessage());
        }
    }

    public void setStateAsCompleted(Process process)
    {
        try
        {
            process.processControlBlock.setState("Completed");
            System.out.println("|Dispatcher| Changed Process State (ID: " + process.processControlBlock.retrieveId() + ") Status: " + process.processControlBlock.retrieveState());
            Utilities.printBreakLine();
        }
        catch (Exception e)
        {
            Utilities.errorMsg(e.getMessage());
        }
    }


}
