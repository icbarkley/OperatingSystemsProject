package com.company;

// will take a process from scheduler and change state and assign to cpu
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
        this.method = OS.getMethod();
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
                Process process = this.getProcessFromScheduler();
                this.systemProcessStorage.removeProcessFromReadyQueueInLinkedListById(process.pcb.getId());
                Utilities.print("In Dispatcher: process id: " + process.pcb.getId() + "- priority: " + process.pcb.getPriority());

                changeStateToRun(process);
                OS.setIsExecutingAProcess(true);
                this.systemCPU.setCurrent(process);
                this.systemCPU.toExecute();

                if (!OS.isExecutingAProcess())
                    changeStateToCompleted(process);

                if (systemProcessStorage.queueContainsNothing(systemProcessStorage.readyQueue))
                {
                    osController.setAllWorksAreDone(true);
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
            Process process = this.getProcessFromScheduler();
            Utilities.print("In Dispatcher: process id: " + process.pcb.getId() + "- total time needed to finish: " + process.pcb.getBurstTime());
            changeStateToRun(process);
            this.systemCPU.setCurrent(process);
            this.systemCPU.toExecute();
            Utilities.printBreakLine();
            if (process.pcb.getBurstTime() > 0)
            {
                osController.changeProcessStateToReady(process);
                this.systemProcessStorage.moveCurrentProcessToTheEndOfReadyQueue(process);
            }
            else
            {
                changeStateToCompleted(process);
            }

            if (systemProcessStorage.queueContainsNothing(systemProcessStorage.readyQueue))
            {
                osController.setAllWorksAreDone(true);
            }
    }

    public void connectToScheduler(Scheduler scheduler)
    {
        try
        {
            this.taskScheduler = scheduler;
        } catch (Exception e) {
            Utilities.errorMsg(e.getMessage());
        }
    }

    public void connectToProcessWareHouse(systemProcessStorage processWareHouse)
    {
        try
        {
            this.systemProcessStorage = processWareHouse;
        } catch (Exception e)
        {
            Utilities.errorMsg(e.getMessage());
        }
    }

    public Process getProcessFromScheduler()
    {
        Process process = taskScheduler.getProcess();
        if (process == null) osController.setAllWorksAreDone(true);
        return process;
    }

    public void changeStateToNew(Process process)
    {
        try
        {
            process.pcb.setState("New");
            Utilities.printHeadLine("In Dispatcher: Change the process with id " +
                    process.pcb.getId() + " to state " + process.pcb.getState());
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
            Utilities.printHeadLine("In Dispatcher: Change the process with id " +
                    process.pcb.getId() + " to state " + process.pcb.getState());
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
            Utilities.printHeadLine("In Dispatcher: Change the process with id " +
                    process.pcb.getId() + " to state " + process.pcb.getState());
        }
        catch (Exception e)
        {
            Utilities.errorMsg(e.getMessage());
        }
    }

    public void changeStateToCompleted(Process process)
    {
        try
        {
            process.pcb.setState("Completed");
            Utilities.printHeadLine("In Dispatcher: Change the process with id " +
                    process.pcb.getId() + " to state " + process.pcb.getState());
            Utilities.printBreakLine();
        }
        catch (Exception e)
        {
            Utilities.errorMsg(e.getMessage());
        }
    }


}
