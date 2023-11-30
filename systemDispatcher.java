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
            startRoundRobin();
        }
    }

    public void startWithPriorityQueueMethod()
    {
        try
        {
            if (!OS.isExecuting())
            {
                Process process = this.retrieveProcess();
                this.systemStorage.removeProcessFromReadyQueueInLinkedListById(process.pcb.retrieveId());
                System.out.println("|Dispatcher| Process ID: " + process.pcb.retrieveId() + " Priority: " + process.pcb.retrievePriority());

                changeStateToRun(process);
                OS.setIsExecuting(true);
                this.systemCPU.setCurrent(process);
                this.systemCPU.toExecute();

                if (!OS.isExecuting())
                    changeStateToFinished(process);

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

    public void startRoundRobin()
    {
            Process process = this.retrieveProcess();
            System.out.println("|Dispatcher| Process ID: " + process.pcb.retrieveId() + " Overall Time to Finish: " + process.pcb.retrieveBurstTime());
            changeStateToRun(process);
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
                changeStateToFinished(process);
            }

            if (systemStorage.queueContainsNothing(systemStorage.readyQueue))
            {
                osController.setSystemFinished(true);
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
            Utilities.errorMsg(e.getMessage());
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
            Utilities.errorMsg(e.getMessage());
        }
    }

    public Process retrieveProcess()
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

    public void changeStateToRun(Process process)
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

    public void changeStateToReady(Process process)
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

    public void changeStateToFinished(Process process)
    {
        try
        {
            process.pcb.setState("Finished");
            System.out.println("|Dispatcher| Changed Process State (ID: " + process.pcb.retrieveId() + ") Status: " + process.pcb.retrieveState());
            Utilities.printBreakLine();
        }
        catch (Exception e)
        {
            Utilities.errorMsg(e.getMessage());
        }
    }


}
