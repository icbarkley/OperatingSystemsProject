package com.company;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.LinkedList;

public class ProcessStorage<V> {
    Queue<Process> jobQueue;
    // use readyQue in roundrobin
    Queue<Process> readyQueue;

    //use readyQueueInLinkedList in priorityQ
    LinkedList<Process> readyQueueInLinkedList;

    Queue<Process> waitQueue;
    Queue<Process> blockQueue;

    Dispatcher dispatcher;
    // OS
    OS osController;

    public ProcessStorage(OS os) {
        this.jobQueue = new LinkedList<>();
        this.readyQueue = new LinkedList<>();
        this.waitQueue = new LinkedList<>();
        this.blockQueue = new LinkedList<>();
        this.readyQueueInLinkedList = new LinkedList<>();
        this.osController = os;

    }

    public void connectToDispatcher(Dispatcher dispatcher) {
        try {
            this.dispatcher = dispatcher;
        } catch (Exception e) {
            Utilities.printErr(e.getMessage());
        }
    }

//  Based on the method PQ or RR, the data structure will be different
    public Collection<Process> getReadyQueue() {
        if (OS.isPriorityQueueMethod()) { return getReadyQueueIfPriorityQueue(); }
        if (OS.isRoundRobinMethod()) { return getReadyQueueIfRoundRobin(); }
        return null;
    }
    public Queue<Process> getReadyQueueIfRoundRobin() { return this.readyQueue; }

    public LinkedList<Process> getReadyQueueIfPriorityQueue() { return this.readyQueueInLinkedList; }
//  End

    // Job queue functionalities
    public boolean addProcessToJobQueue(Process process) {
        try {
            Utilities.printSubLine("In process ware house(PWH): ");
            Utilities.print("Add most current process to job queue");
            this.jobQueue.add(process);
            return true;
        } catch (Exception e) {
            Utilities.print("Error happenned: " + e.getMessage());
            return false;
        }
    }

    public Process removeMostCurrentProcessFromJobQueue() {
        try {
            Utilities.print("Remove most current process from job queue");
            if (!isQueueEmpty(jobQueue)) {
                Process process = this.jobQueue.remove();
                return process;
            } else
                return null;
        } catch (Exception e) {
            Utilities.print("Error happenned: " + e.getMessage());
            return null;
        }
    }
    // End job queue functionality

    // Priority Queue implementation
    public void addProcessToReadyQueueInLinkedList(Process process) {
        try {

            Utilities.print("In PWH: Add most current process to ready queue linked list");
            this.readyQueueInLinkedList.add(process);
            Utilities.printBreakLine();

        } catch (Exception e) {
            Utilities.print("Error happenned: " + e.getMessage());
        }
    }

    // move from ready queue to execution
    public void removeProcessFromReadyQueueInLinkedListById(int id) {
        try {
            if (id <= 0) throw new IllegalArgumentException("Invalid id");
            Process process = searchProcessById(id);
            this.readyQueueInLinkedList.remove(process);
            Utilities.print("In PWH: remove a process with id: " + id + " out of Ready Queue.");
        } catch (Exception e) {
            Utilities.print("Error happenned: " + e.getMessage());
        }
    }

    // End Priority Queue implementation


    // main functions
    public void addProcessToReadyQueue(Process process) {
        try {
            Utilities.print("In PWH: Add most current process to ready queue");
            osController.changeProcessStateToReady(process);
            if (OS.isPriorityQueueMethod()) {
                this.addProcessToReadyQueueInLinkedList(process);
            }

            else if (OS.isRoundRobinMethod()) {
                Utilities.print("In PWH: Add most current process to ready queue in Round RObin");
                this.moveCurrentProcessToTheEndOfReadyQueue(process);
            }
            Utilities.printBreakLine();
        } catch (Exception e) {
            Utilities.print("Error happenned: " + e.getMessage());
        }
    }

    //Round robin implementation
    //get the most current process
    public Process getMostCurrentProcessFromReadyQueue() {
        if (OS.isRoundRobinMethod()) {
            if (!isQueueEmpty(readyQueue)) {
                Utilities.print("In PWH: Remove most current process from ready queue");
                Process process = this.readyQueue.poll();
                Utilities.print("The poll off process is id " + process.processControlBlock.getId());
                return process;
            }
        }
        return null;
    }
    // if still has burst time then return the process to the end of ready Queue
    public void moveCurrentProcessToTheEndOfReadyQueue(Process process) {
        try {
            if (!Utilities.isValidProcess(process)) throw new IllegalArgumentException("The process is not valid");
            Utilities.print("In PWH: Move the process id: " + process.processControlBlock.getId()+" to the end of ready queue");
            this.readyQueue.add(process);
        } catch (Exception e) {
            Utilities.printErr(e.getMessage());
        }
    }

    // End of round robin implementation

//     Utilities
//    To use in readyQueueLinkedList
    public Process searchProcessById(int id) {
        Iterator<Process> itr = this.readyQueueInLinkedList.iterator();

        while (itr.hasNext()) {
            Process currentProcess = itr.next();
            int currentId = currentProcess.processControlBlock.getId();
            if (currentId == id) return currentProcess;
        }
        return null;
    }

//    to check the existence of a process in system
    public boolean isProcessInWareHouse(Process process) {
        return (isInJobQueue(process) || isInReadyQueue(process) || isInBlockQueue(process)
                ||  isInWaitQueue(process));
    }

    public Queue<Process> getQueueOfCurrentProcess(Process process) {
        if (isInReadyQueue(process)) return readyQueue;
        if (isInWaitQueue(process)) return waitQueue;
        if (isInJobQueue(process)) return jobQueue;
        if (isInBlockQueue(process)) return blockQueue;
        return null;
    }

    public boolean isInJobQueue(Process process) {
        return jobQueue.contains(process);
    }

    public boolean isInReadyQueue(Process process) {
        return readyQueue.contains(process);
    }

    public boolean isInWaitQueue(Process process) {
        return waitQueue.contains(process);
    }


    public boolean isInBlockQueue(Process process) {
        return blockQueue.contains(process);
    }

    public boolean isQueueEmpty(Queue queue) {
        return queue.isEmpty();
    }
    
    public void printQueue() {
        Iterator<Process> itr = readyQueue.iterator();

        // hasNext() returns true if the queue has more elements
        while (itr.hasNext())
        {
            // next() returns the next element in the iteration
            System.out.println(itr.next());
        }
    }
    // End utilities
}
