package com.company;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.LinkedList;

public class systemStorage<V>
{
    Queue<Process> jobQueue;
    Queue<Process> readyQueue;
    LinkedList<Process> readyLinkedListQueue;
    Queue<Process> waitQueue;
    Queue<Process> blockQueue;
    systemDispatcher systemDispatcher;
    OS osController;

    public systemStorage(OS os)
    {
        this.jobQueue = new LinkedList<>();
        this.readyQueue = new LinkedList<>();
        this.waitQueue = new LinkedList<>();
        this.blockQueue = new LinkedList<>();
        this.readyLinkedListQueue = new LinkedList<>();
        this.osController = os;

    }


    public Collection<Process> getReadyQueue()
    {
        if (OS.isPriorityQueue())
        {
            return getReadyQueuePQ();
        }
        if (OS.isRoundRobin())
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
            Utilities.errorMsg(e.getMessage());
        }
    }

    public Queue<Process> getReadyQueueRR()
    {
        return this.readyQueue;
    }

    public LinkedList<Process> getReadyQueuePQ()
    {
        return this.readyLinkedListQueue;
    }

    public boolean addProcessToJobQueue(Process process)
    {
        try
        {
            System.out.println("|Storage|");
            System.out.println("Adding the most recent process to job queue");

            this.jobQueue.add(process);
            return true;
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    public Process removeMostCurrentProcessFromJobQueue()
    {
        try
        {
            System.out.println("Removing the most recent process from job queue");
            if (!queueContainsNothing(jobQueue))
            {
                Process process = this.jobQueue.remove();
                return process;
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

    public void addProcessToReadyQueueInLinkedList(Process process)
    {
        try
        {
            System.out.println("|Storage|");
            System.out.println("Adding most recent process to ready queue linked list");
            this.readyLinkedListQueue.add(process);
            Utilities.printBreakLine();

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
            Process process = searchWithProcessId(id);
            this.readyLinkedListQueue.remove(process);
            System.out.println("|Storage| Removing process (ID:" + id + ") out of the ready queue");
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void addProcessToReadyQueue(Process process)
    {
        try
        {
            System.out.println("|Storage| Adding the most recent process to the ready queue");
            osController.changeStateToReady(process);
            if (OS.isPriorityQueue())
            {
                this.addProcessToReadyQueueInLinkedList(process);
            }

            else if (OS.isRoundRobin())
            {
                System.out.println("|Storage| Adding the most recent process to the ready queue in Round Robin");
                this.moveCurrentProcessToEndOfReadyQueue(process);
            }
            Utilities.printBreakLine();
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public Process retrieveMostRecentProcessInReadyQueue()
    {
        if (OS.isRoundRobin())
        {
            if (!queueContainsNothing(readyQueue))
            {
                System.out.println("|Storage| Remove most recent process from the ready queue");
                Process pwhProcess = this.readyQueue.poll();
                System.out.println("Poll off process ID:" + pwhProcess.pcb.retrieveId());
                return pwhProcess;
            }
        }
        return null;
    }

    public void moveCurrentProcessToEndOfReadyQueue(Process process)
    {
        try
        {
            if (!Utilities.isValid(process)) throw new IllegalArgumentException("This process is not valid");
            System.out.println("|Storage| Process ID: " + process.pcb.retrieveId()+" has been moved to the end of the ready queue");
            this.readyQueue.add(process);
        }
        catch (Exception e)
        {
            Utilities.errorMsg(e.getMessage());
        }
    }

    public Process searchWithProcessId(int id)
    {
        Iterator<Process> itr = this.readyLinkedListQueue.iterator();

        while (itr.hasNext())
        {
            Process realtimeProcess = itr.next();
            int currentId = realtimeProcess.pcb.retrieveId();
            if (currentId == id) return realtimeProcess;
        }
        return null;
    }

    public boolean isInStorage(Process process)
    {
        return (withinJobQueue(process) || withinReadyQueue(process) || withinBlockQueue(process) || withinWaitQueue(process));
    }

    public Queue<Process> getQueueOfCurrentProcess(Process process)
    {
        if (withinReadyQueue(process)) return readyQueue;
        if (withinWaitQueue(process)) return waitQueue;
        if (withinJobQueue(process)) return jobQueue;
        if (withinBlockQueue(process)) return blockQueue;
        return null;
    }

    public boolean withinJobQueue(Process process)
    {
        return jobQueue.contains(process);
    }

    public boolean withinReadyQueue(Process process)
    {
        return readyQueue.contains(process);
    }

    public boolean withinWaitQueue(Process process)
    {
        return waitQueue.contains(process);
    }

    public boolean withinBlockQueue(Process process)
    {
        return blockQueue.contains(process);
    }

    public boolean queueContainsNothing(Queue queue)
    {
        return queue.isEmpty();
    }
    
    public void displayQueue() {
        Iterator<Process> itr = readyQueue.iterator();

        while (itr.hasNext())
        {
            System.out.println(itr.next());
        }
    }
}
