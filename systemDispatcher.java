package com.company;

public class systemDispatcher {
    systemScheduler taskSystemScheduler;
    systemStorage systemStorage;
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
                this.systemStorage.removeProcessFromReadyQueueInLinkedListById(process.pcb.retrieveId());
                System.out.println("|Dispatcher| Process ID: " + process.pcb.retrieveId() + " Priority: " + process.pcb.retrievePriority());

                stateAlterToRun(process);
                OS.setIsExecuting(true);
                this.systemCPU.setCurrent(process);
                this.systemCPU.toExecute();

                if (!OS.isExecuting())
                    setStateAsCompleted(process);

                if (systemStorage.queueContainsNothing(systemStorage.readyQueue))
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
            System.out.println("|Dispatcher| Process ID: " + process.pcb.retrieveId() + " Overall Time to Finish: " + process.pcb.retrieveBurstTime());
            stateAlterToRun(process);
            this.systemCPU.setCurrent(process);
            this.systemCPU.toExecute();
            Utilities.printBreakLine();
            if (process.pcb.retrieveBurstTime() > 0)
            {
                osController.changeStateToReady(process);
                this.systemStorage.moveCurrentProcessToEndOfReadyQueue(process);
            }
            else
            {
                setStateAsCompleted(process);
            }

            if (systemStorage.queueContainsNothing(systemStorage.readyQueue))
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

    public void processWarehouseConnection(systemStorage connectedProcessWarehouse)
    {
        try
        {
            this.systemStorage = connectedProcessWarehouse;
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
            process.pcb.setState("New");
            System.out.println("|Dispatcher| Changed Process State (ID: " + process.pcb.retrieveId() + ") Status: " + process.pcb.retrieveState());
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
            process.pcb.setState("Run");
            System.out.println("|Dispatcher| Changed Process State (ID: " + process.pcb.retrieveId() + ") Status: " + process.pcb.retrieveState());
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
            process.pcb.setState("Ready");
            System.out.println("|Dispatcher| Changed Process State (ID: " + process.pcb.retrieveId() + ") Status: " + process.pcb.retrieveState());
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
            process.pcb.setState("Completed");
            System.out.println("|Dispatcher| Changed Process State (ID: " + process.pcb.retrieveId() + ") Status: " + process.pcb.retrieveState());
            Utilities.printBreakLine();
        }
        catch (Exception e)
        {
            Utilities.errorMsg(e.getMessage());
        }
    }


}
