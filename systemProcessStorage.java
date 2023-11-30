package com.company;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.LinkedList;

public class systemProcessStorage<V>
{
    Queue<Process> jobQueue;
    Queue<Process> readyQueue;
    LinkedList<Process> readyQueueInLinkedList;
    Queue<Process> waitQueue;
    Queue<Process> blockQueue;
    systemDispatcher systemDispatcher;
    OS osController;

    public systemProcessStorage(OS os)
    {
        this.jobQueue = new LinkedList<>();
        this.readyQueue = new LinkedList<>();
        this.waitQueue = new LinkedList<>();
        this.blockQueue = new LinkedList<>();
        this.readyQueueInLinkedList = new LinkedList<>();
        this.osController = os;

    }


    public Collection<Process> getReadyQueue()
    {
        if (OS.isPriorityQueueMethod())
        {
            return getReadyQueuePQ();
        }
        if (OS.isRoundRobinMethod())
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
        return this.readyQueueInLinkedList;
    }

    public boolean addProcessToJobQueue(Process process)
    {
        try
        {
            Utilities.printSubLine("In process ware house(PWH): ");
            System.out.println("Adding the most current process to job queue");

            this.jobQueue.add(process);
            return true;
        }
        catch (Exception e)
        {
            Utilities.print("Error: " + e.getMessage());
            return false;
        }
    }

    public Process removeMostCurrentProcessFromJobQueue()
    {
        try
        {
            Utilities.print("Remove most current process from job queue");
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
            Utilities.print("Error: " + e.getMessage());
            return null;
        }
    }

    public void addProcessToReadyQueueInLinkedList(Process process)
    {
        try
        {

            Utilities.print("In PWH: Add most current process to ready queue linked list");
            this.readyQueueInLinkedList.add(process);
            Utilities.printBreakLine();

        }
        catch (Exception e)
        {
            Utilities.print("Error: " + e.getMessage());
        }
    }

    public void removeProcessFromReadyQueueInLinkedListById(int id)
    {
        try
        {
            if (id <= 0) throw new IllegalArgumentException("Invalid id");
            Process process = searchProcessById(id);
            this.readyQueueInLinkedList.remove(process);
            Utilities.print("In PWH: remove a process with id: " + id + " out of Ready Queue.");
        }
        catch (Exception e)
        {
            Utilities.print("Error: " + e.getMessage());
        }
    }

    public void addProcessToReadyQueue(Process process)
    {
        try
        {
            Utilities.print("In PWH: Add most current process to ready queue");
            osController.changeProcessStateToReady(process);
            if (OS.isPriorityQueueMethod())
            {
                this.addProcessToReadyQueueInLinkedList(process);
            }

            else if (OS.isRoundRobinMethod())
            {
                Utilities.print("In PWH: Add most current process to ready queue in Round Robin");
                this.moveCurrentProcessToTheEndOfReadyQueue(process);
            }
            Utilities.printBreakLine();
        }
        catch (Exception e)
        {
            Utilities.print("Error: " + e.getMessage());
        }
    }

    public Process getMostCurrentProcessFromReadyQueue()
    {
        if (OS.isRoundRobinMethod())
        {
            if (!queueContainsNothing(readyQueue))
            {
                Utilities.print("In PWH: Remove most current process from ready queue");
                Process process = this.readyQueue.poll();
                Utilities.print("ID of poll off process:" + process.pcb.getId());
                return process;
            }
        }
        return null;
    }

    public void moveCurrentProcessToTheEndOfReadyQueue(Process process)
    {
        try
        {
            if (!Utilities.isValid(process)) throw new IllegalArgumentException("The process is not valid");
            Utilities.print("In PWH: Process ID: " + process.pcb.getId()+" has been moved to the end of ready queue");
            this.readyQueue.add(process);
        }
        catch (Exception e)
        {
            Utilities.errorMsg(e.getMessage());
        }
    }

    public Process searchProcessById(int id)
    {
        Iterator<Process> itr = this.readyQueueInLinkedList.iterator();

        while (itr.hasNext())
        {
            Process currentProcess = itr.next();
            int currentId = currentProcess.pcb.getId();
            if (currentId == id) return currentProcess;
        }
        return null;
    }

    public boolean isProcessInWareHouse(Process process)
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
