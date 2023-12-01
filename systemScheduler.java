package com.noahbarkos;

import java.util.*;

public class systemScheduler
{
    Queue<Integer> priorityQueue;
    Map<Integer, Integer> idAndBurstTimeMap;
    systemStorage systemProcessStorage;
    systemDispatcher systemDispatcher;
    OperatingSystem operatingSystemController;

    public systemScheduler(OperatingSystem operatingSystemController)
    {
        this.priorityQueue = new LinkedList<Integer>();
        this.idAndBurstTimeMap = new HashMap<Integer, Integer>();
        this.operatingSystemController = operatingSystemController;
    }

    public void connectToProcessStorage(systemStorage systemProcessStorage)
    {
        try
        {
            this.systemProcessStorage = systemProcessStorage;
        }
        catch (Exception e)
        {
            miscSystemProcesses.errorMsg(e.getMessage());
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
            miscSystemProcesses.errorMsg(e.getMessage());
        }
    }

    public void start()
    {
        if (OperatingSystem.isPriorityQueue())
        {
            createidAndBurstTimeMap();
            createPriorityQueue();
        }

        if (OperatingSystem.isRoundRobin())
        {
            System.out.println("{Scheduler: Picked Process from the Ready Queue}");
            return;
        }
    }

    public systemProcessManager getProcess()
    {
        if (OperatingSystem.isPriorityQueue())
        {
            systemProcessManager systemProcessManager = getPriorityQueueProcess();
            System.out.println("{Scheduler: Retrieved Process ID: " + systemProcessManager.systemProcessControlBlock.retrieveId() + "}");
            return systemProcessManager;
        }

        if (OperatingSystem.isRoundRobin())
        {
            systemProcessManager systemProcessManager = getRoundRobinProcess();
            System.out.println("{Scheduler: Retrieved Process ID: " + systemProcessManager.systemProcessControlBlock.retrieveId() + "}");
            return systemProcessManager;
        }
        return null;
    }

    public systemProcessManager getPriorityQueueProcess()
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

    public systemProcessManager getRoundRobinProcess()
    {
        systemProcessManager systemProcessManager = null;
        try
        {
            if (this.systemProcessStorage.queueContainsNothing(this.systemProcessStorage.readyQueue))
            {
                System.out.println("Ready Queue Has No Contents");
                return null;
            }

            systemProcessManager = this.systemProcessStorage.retrieveMostRecentProcessInReadyQueue();
        }
        catch (Exception e)
        {
            miscSystemProcesses.errorMsg(e.getMessage());
        }
        return systemProcessManager;
    }

    public void createidAndBurstTimeMap()
    {
        Collection readyQueue = this.systemProcessStorage.getReadyQueue();
        for (Object process : readyQueue)
        {
            addProcess((systemProcessManager) process);
        }
        System.out.println("{Scheduler: Create HashMap for PQ}");
        printHashMap();
    }


    public boolean addProcess(systemProcessManager systemProcessManager)
    {
        try
        {
            if (systemProcessManager.systemProcessControlBlock.retrieveId() > 0 || systemProcessManager.systemProcessControlBlock.retrievePriority() != 0)
            {
                idAndBurstTimeMap.put(systemProcessManager.systemProcessControlBlock.retrieveId(),
                        systemProcessManager.systemProcessControlBlock.retrievePriority());
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
            miscSystemProcesses.errorMsg(e.getMessage());
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
