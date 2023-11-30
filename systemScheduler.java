package com.company;

import java.util.*;

public class systemScheduler
{
    Queue<Integer> priorityQueue;
    Map<Integer, Integer> idAndBurstTimeMap;
    systemProcessStorage systemProcessStorage;
    systemDispatcher systemDispatcher;
    OS osController;

    public systemScheduler(OS osController)
    {
        this.priorityQueue = new LinkedList<Integer>();
        this.idAndBurstTimeMap = new HashMap<Integer, Integer>();
        this.osController = osController;
    }

    public void connectToProcessStorage(systemProcessStorage systemProcessStorage)
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
        if (OS.isPriorityQueueMethod())
        {
//            HashMap (key:id - value:priority) to keep history of all the processes that had been added either ready or terminated
//            From HashMap create the priority queue of Ids based on the process priority
            createidAndBurstTimeMap();
            createPriorityQueue();
        }

        if (OS.isRoundRobinMethod())
        {
//             No need to create additional data structure since the process is poll off from ready Queue
//            from ProcessWareHouse
            System.out.println("{Scheduler: Picked Process from the Ready Queue}");
            return;
        }
    }

//     take a process from priority Q, take it out of ready Q
    public Process getProcess()
    {
//        depend on the method, if PQ then pull the id out of PQ, search for that process, execute that process and erase that process
//        if RR, just pull the most current process, execute a while and put it back

        if (OS.isPriorityQueueMethod())
        {
            com.company.Process process = getPriorityQueueProcess();
            System.out.println("{Scheduler: Retrieved Process ID: " + process.processControlBlock.retrieveId() + "}");
            return process;
        }

        if (OS.isRoundRobinMethod())
        {
            Process process = getRoundRobinProcess();
            System.out.println("{Scheduler: Retrieved Process ID: " + process.processControlBlock.retrieveId() + "}");
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


//  Priority scheduling algorithm related functions
//  create the hashmap to later on feed to priority queue
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
            // Very crucial condition check to separate the 2 thread
            // If specify same properties, one thread can know and change data of another thread
            if (process.processControlBlock.retrieveId() > 0 || process.processControlBlock.retrievePriority() != 0)
            {
                idAndBurstTimeMap.put(process.processControlBlock.retrieveId(),
                        process.processControlBlock.retrievePriority());
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

    // Printing methods
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
