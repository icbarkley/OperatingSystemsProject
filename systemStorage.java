package com.noahbarkos;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.LinkedList;

public class systemStorage<V>
{
    Queue<systemProcessManager> jobQueue;
    Queue<systemProcessManager> readyQueue;
    LinkedList<systemProcessManager> readyLinkedListQueue;
    Queue<systemProcessManager> waitQueue;
    Queue<systemProcessManager> blockQueue;
    systemDispatcher systemDispatcher;
    OperatingSystem operatingSystemController;

    public systemStorage(OperatingSystem operatingSystem)
    {
        this.jobQueue = new LinkedList<>();
        this.readyQueue = new LinkedList<>();
        this.waitQueue = new LinkedList<>();
        this.blockQueue = new LinkedList<>();
        this.readyLinkedListQueue = new LinkedList<>();
        this.operatingSystemController = operatingSystem;

    }


    public Collection<systemProcessManager> getReadyQueue()
    {
        if (OperatingSystem.isPriorityQueue())
        {
            return getReadyQueuePQ();
        }
        if (OperatingSystem.isRoundRobin())
        {
            return getReadyQueueRR();
        }
        return null;
    }

    public void dispatcherConnection(systemDispatcher systemDispatcher)
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

    public Queue<systemProcessManager> getReadyQueueRR()
    {
        return this.readyQueue;
    }

    public LinkedList<systemProcessManager> getReadyQueuePQ()
    {
        return this.readyLinkedListQueue;
    }

    public boolean addProcessToJobQueue(systemProcessManager systemProcessManager)
    {
        try
        {
            System.out.println("|Storage|");
            System.out.println("Adding the most recent process to job queue");

            this.jobQueue.add(systemProcessManager);
            return true;
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    public systemProcessManager removeMostCurrentProcessFromJobQueue()
    {
        try
        {
            System.out.println("Removing the most recent process from job queue");
            if (!queueContainsNothing(jobQueue))
            {
                systemProcessManager systemProcessManager = this.jobQueue.remove();
                return systemProcessManager;
            }
            else
                return null;
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public void addProcessToReadyQueueInLinkedList(systemProcessManager systemProcessManager)
    {
        try
        {
            System.out.println("|Storage|");
            System.out.println("Adding most recent process to ready queue linked list");
            this.readyLinkedListQueue.add(systemProcessManager);
            System.out.println("\n");

        }
        catch (Exception e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void removeProcessFromReadyQueueInLinkedListById(int id)
    {
        try
        {
            if (id <= 0) throw new IllegalArgumentException("Invalid id");
            systemProcessManager systemProcessManager = searchWithProcessId(id);
            this.readyLinkedListQueue.remove(systemProcessManager);
            System.out.println("|Storage| Removing process (ID:" + id + ") out of the ready queue");
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void addProcessToReadyQueue(systemProcessManager systemProcessManager)
    {
        try
        {
            System.out.println("|Storage| Adding the most recent process to the ready queue");
            operatingSystemController.changeStateToReady(systemProcessManager);
            if (OperatingSystem.isPriorityQueue())
            {
                this.addProcessToReadyQueueInLinkedList(systemProcessManager);
            }

            else if (OperatingSystem.isRoundRobin())
            {
                System.out.println("|Storage| Adding the most recent process to the ready queue in Round Robin");
                this.moveCurrentProcessToEndOfReadyQueue(systemProcessManager);
            }
            System.out.println("\n");
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public systemProcessManager retrieveMostRecentProcessInReadyQueue()
    {
        if (OperatingSystem.isRoundRobin())
        {
            if (!queueContainsNothing(readyQueue))
            {
                System.out.println("|Storage| Remove most recent process from the ready queue");
                systemProcessManager pwhSystemProcessManager = this.readyQueue.poll();
                System.out.println("Poll off process ID:" + pwhSystemProcessManager.systemProcessControlBlock.retrieveId());
                return pwhSystemProcessManager;
            }
        }
        return null;
    }

    public void moveCurrentProcessToEndOfReadyQueue(systemProcessManager systemProcessManager)
    {
        try
        {
            if (!miscSystemProcesses.isValid(systemProcessManager)) throw new IllegalArgumentException("This process is not valid");
            System.out.println("|Storage| Process ID: " + systemProcessManager.systemProcessControlBlock.retrieveId()+" has been moved to the end of the ready queue");
            this.readyQueue.add(systemProcessManager);
        }
        catch (Exception e)
        {
            miscSystemProcesses.errorMsg(e.getMessage());
        }
    }

    public systemProcessManager searchWithProcessId(int id)
    {
        Iterator<systemProcessManager> itr = this.readyLinkedListQueue.iterator();

        while (itr.hasNext())
        {
            systemProcessManager realtimeSystemProcessManager = itr.next();
            int currentId = realtimeSystemProcessManager.systemProcessControlBlock.retrieveId();
            if (currentId == id) return realtimeSystemProcessManager;
        }
        return null;
    }

    public boolean withinStorage(systemProcessManager systemProcessManager)
    {
        return (withinJobQueue(systemProcessManager) || withinReadyQueue(systemProcessManager) || withinBlockQueue(systemProcessManager) || withinWaitQueue(systemProcessManager));
    }

    public Queue<systemProcessManager> retrieveQueueOfCurrentProcess(systemProcessManager systemProcessManager)
    {
        if (withinReadyQueue(systemProcessManager))
            return readyQueue;

        if (withinWaitQueue(systemProcessManager))
            return waitQueue;

        if (withinJobQueue(systemProcessManager))
            return jobQueue;

        if (withinBlockQueue(systemProcessManager))
            return blockQueue;

        return null;
    }

    public boolean withinJobQueue(systemProcessManager systemProcessManager)
    {
        return jobQueue.contains(systemProcessManager);
    }

    public boolean withinReadyQueue(systemProcessManager systemProcessManager)
    {
        return readyQueue.contains(systemProcessManager);
    }

    public boolean withinWaitQueue(systemProcessManager systemProcessManager)
    {
        return waitQueue.contains(systemProcessManager);
    }

    public boolean withinBlockQueue(systemProcessManager systemProcessManager)
    {
        return blockQueue.contains(systemProcessManager);
    }

    public boolean queueContainsNothing(Queue queue)
    {
        return queue.isEmpty();
    }
    
    public void showQueue() {
        Iterator<systemProcessManager> itr = readyQueue.iterator();

        while (itr.hasNext())
        {
            System.out.println(itr.next());
        }
    }
}
