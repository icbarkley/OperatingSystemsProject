package com.company;

import java.util.*;

public class systemScheduler
{
    Queue<Integer> priorityQueue;
    Map<Integer, Integer> idAndBurstTimeMap;
    systemStorage systemProcessStorage;
    systemDispatcher systemDispatcher;
    OS osController;

    public systemScheduler(OS osController)
    {
        this.priorityQueue = new LinkedList<Integer>();
        this.idAndBurstTimeMap = new HashMap<Integer, Integer>();
        this.osController = osController;
    }

    public void connectToProcessStorage(systemStorage systemProcessStorage)
    {
        try
        {
            this.systemProcessStorage = systemProcessStorage;
        }
        catch (Exception e)
        {
            Utilities.errorMsg(e.getMessage());
        }
    }

    public void connectToDispatcher(systemDispatcher systemDispatcher)
    {
        try
        {
            this.systemDispatcher = systemDispatcher;
        }
        catch (Exception e)
        {
            Utilities.errorMsg(e.getMessage());
        }
    }

    public void start()
    {
        if (OS.isPriorityQueue())
        {
            createidAndBurstTimeMap();
            createPriorityQueue();
        }

        if (OS.isRoundRobin())
        {
            System.out.println("{Scheduler: Picked Process from the Ready Queue}");
            return;
        }
    }

    public Process getProcess()
    {
        if (OS.isPriorityQueue())
        {
            com.company.Process process = getPriorityQueueProcess();
            System.out.println("{Scheduler: Retrieved Process ID: " + process.pcb.retrieveId() + "}");
            return process;
        }

        if (OS.isRoundRobin())
        {
            Process process = getRoundRobinProcess();
            System.out.println("{Scheduler: Retrieved Process ID: " + process.pcb.retrieveId() + "}");
            return process;
        }
        return null;
    }

    public Process getPriorityQueueProcess()
    {
        if (priorityQueue.size() > 0)
        {
            int id = priorityQueue.remove();
            return this.systemProcessStorage.searchWithProcessId(id);
        }
        else
        {
            System.out.println("Priority Queue Has No Contents");
            return null;
        }
    }

    public Process getRoundRobinProcess()
    {
        Process process = null;
        try
        {
            if (this.systemProcessStorage.queueContainsNothing(this.systemProcessStorage.readyQueue))
            {
                System.out.println("Ready Queue Has No Contents");
                return null;
            }

            process = this.systemProcessStorage.retrieveMostRecentProcessInReadyQueue();
        }
        catch (Exception e)
        {
            Utilities.errorMsg(e.getMessage());
        }
        return process;
    }

    public void createidAndBurstTimeMap()
    {
        Collection readyQueue = this.systemProcessStorage.getReadyQueue();
        for (Object process : readyQueue)
        {
            addProcess((Process) process);
        }
        System.out.println("{Scheduler: Create HashMap for PQ}");
        printHashMap();
    }


    public boolean addProcess(Process process)
    {
        try
        {
            if (process.pcb.retrieveId() > 0 || process.pcb.retrievePriority() != 0)
            {
                idAndBurstTimeMap.put(process.pcb.retrieveId(),
                        process.pcb.retrievePriority());
                return true;
            }
            else
            {
                System.out.println("Can't add to map");
                return false;
            }
        }
        catch (Exception e)
        {
            Utilities.errorMsg(e.getMessage());
            return false;
        }
    }

    public void createPriorityQueue()
    {
        LinkedHashMap<Integer, Integer> sortedMap = new LinkedHashMap<>();

        idAndBurstTimeMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));

        for (Map.Entry<Integer, Integer> en : sortedMap.entrySet())
        {
            priorityQueue.offer(en.getKey());
        }
    }

    public void printPriorityQueue()
    {
         for (int id : priorityQueue)
         {
             System.out.println("Process ID: " + id);
        }
    }

    public void printHashMap()
    {
        Iterator itr = idAndBurstTimeMap.entrySet().iterator();

        while (itr.hasNext())
        {
            Map.Entry element = (Map.Entry) itr.next();
            System.out.println("ID: " + element.getKey() + " Priority: " + element.getValue());
        }
    }
}
