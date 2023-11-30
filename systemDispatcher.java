package com.company;

public class systemDispatcher {
    Scheduler taskScheduler;
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
        if (OS.isPriorityQueueMethod())
        {
            startWithPriorityQueueMethod();
        }

        if (OS.isRoundRobinMethod())
        {
            startWithRoundRobinMethod();
        }
    }

    public void startWithPriorityQueueMethod()
    {
        try
        {
            if (!OS.isExecutingAProcess())
            {
                Process process = this.retrieveProcessFromScheduler();
                this.systemProcessStorage.removeProcessFromReadyQueueInLinkedListById(process.pcb.returnId());
                System.out.println("|Dispatcher| Process ID: " + process.pcb.returnId() + " Priority: " + process.pcb.getPriority());

                stateAlterToRun(process);
                OS.setIsExecutingAProcess(true);
                this.systemCPU.setCurrent(process);
                this.systemCPU.toExecute();

                if (!OS.isExecutingAProcess())
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
            System.out.println("|Dispatcher| Process ID: " + process.pcb.returnId() + " Overall Time to Finish: " + process.pcb.getBurstTime());
            stateAlterToRun(process);
            this.systemCPU.setCurrent(process);
            this.systemCPU.toExecute();
            Utilities.printBreakLine();
            if (process.pcb.getBurstTime() > 0)
            {
                osController.changeProcessStateToReady(process);
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

    public void schedulerConnection(Scheduler connectedScheduler)
    {
        try
        {
            this.taskScheduler = connectedScheduler;
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
        Process process = taskScheduler.getProcess();
        if (process == null) osController.setSystemFinished(true);
        return process;
    }

    public void changeStateToNew(Process process)
    {
        try
        {
            process.pcb.setState("New");
            System.out.println("|Dispatcher| Change the process (ID: " + process.pcb.returnId() + ") to state " + process.pcb.getState());
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
            System.out.println("|Dispatcher| Change process (ID: " + process.pcb.returnId() + ") to state " + process.pcb.getState());
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
            System.out.println("|Dispatcher| Change process (ID: " + process.pcb.returnId() + ") to state " + process.pcb.getState());
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
            Utilities.printHeadLine("|Dispatcher| Change process (ID: " + process.pcb.returnId() + ") to state " + process.pcb.getState());
            Utilities.printBreakLine();
        }
        catch (Exception e)
        {
            Utilities.errorMsg(e.getMessage());
        }
    }


}
