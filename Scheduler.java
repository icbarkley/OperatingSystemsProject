package com.company;

import java.util.*;

public class Scheduler {
    Queue<Integer> priorityQueue;
    Map<Integer, Integer> idAndBurstTimeMap;
    ProcessStorage processStorage;
    systemDispatcher systemDispatcher;
    OS osController;

    public Scheduler(OS osController) {
        this.priorityQueue = new LinkedList<Integer>();
        this.idAndBurstTimeMap = new HashMap<Integer, Integer>();
        this.osController = osController;
    }


    // main function
    public void connectToProcessStorage(ProcessStorage processStorage) {
        try {
            this.processStorage = processStorage;
        } catch (Exception e) {
            Utilities.printErr(e.getMessage());
        }
    }

    public void connectToDispatcher(systemDispatcher systemDispatcher) {
        try {
            this.systemDispatcher = systemDispatcher;
        } catch (Exception e) {
            Utilities.printErr(e.getMessage());
        }
    }
    //end

    public void start() {
        if (OS.isPriorityQueueMethod()) {
//            HashMap (key:id - value:priority) to keep history of all the processes that had been added either ready or terminated
//            From HashMap create the priority queue of Ids based on the process priority
            createidAndBurstTimeMap();
            createPriorityQueue();
        }

        if (OS.isRoundRobinMethod()) {
//             No need to create additional data structure since the process is poll off from ready Queue
//            from ProcessWareHouse
            Utilities.printSubLine("In Scheduler: The process is picked from the Ready Queue");
            return;
        }
    }

//     take a process from priority Q, take it out of ready Q
    public Process getProcess() {
//        depend on the method, if PQ then pull the id out of PQ, search for that process, execute that process and erase that process
//        if RR, just pull the most current process, execute a while and put it back

        if (OS.isPriorityQueueMethod()) {
            com.company.Process process = getPriorityQueueProcess();
            Utilities.printSubLine("In scheduler: The retrieved process id: " + process.pcb.getId());
            return process;
        }

        if (OS.isRoundRobinMethod()) {
            Process process = getRoundRobinProcess();
            Utilities.printSubLine("In scheduler: The retrieved process id: " + process.pcb.getId());
            return process;
        }
        return null;
    }

    public Process getPriorityQueueProcess() {
        if (priorityQueue.size() > 0) {
            int id = priorityQueue.remove();
            return this.processStorage.searchProcessById(id);
        } else {
            Utilities.print("Priority Queue is empty");
            return null;
        }
    }

    public Process getRoundRobinProcess() {
        Process process = null;
        try {
            if (this.processStorage.isQueueEmpty(this.processStorage.readyQueue)) {
                Utilities.print("The ready queue is empty");
                return null;
            }
            process = this.processStorage.getMostCurrentProcessFromReadyQueue();
        } catch (Exception e) {
            Utilities.printErr(e.getMessage());
        }
        return process;
    }
    // end

//  Priority scheduling algorithm related functions
//  create the hashmap to later on feed to priority queue
    public void createidAndBurstTimeMap() {
        Collection readyQueue = this.processStorage.getReadyQueue();
        for (Object process : readyQueue) {
            addProcess((Process) process);
        }
        Utilities.printSubLine("In Scheduler: Create HashMap for PQ");
        printHashMap();
    }


    public boolean addProcess(Process process) {
        try {
            // Very crucial condition check to separate the 2 thread
            // If specify same properties, one thread can know and change data of another thread
            if (process.pcb.getId() > 0 || process.pcb.getPriority() != 0) {
                idAndBurstTimeMap.put(process.pcb.getId(),
                        process.pcb.getPriority());
                return true;
            } else {
                Utilities.printErr("Cannot add to map");
                return false;
            }
        } catch (Exception e) {
            Utilities.printErr(e.getMessage());
            return false;
        }
    }

    public void createPriorityQueue() {
        LinkedHashMap<Integer, Integer> sortedMap = new LinkedHashMap<>();

        idAndBurstTimeMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));

        for (Map.Entry<Integer, Integer> en : sortedMap.entrySet()) {
            priorityQueue.offer(en.getKey());
        }
    }

    // Printing methods
    public void printPriorityQueue() {
         for (int id : priorityQueue) {
            Utilities.print("The process id is: " + id);
        }
    }

    public void printHashMap() {
        Iterator itr = idAndBurstTimeMap.entrySet().iterator();

        while (itr.hasNext()) {
            Map.Entry element = (Map.Entry) itr.next();
            Utilities.print("id: " + element.getKey() + " Priority: " + element.getValue());
        }
    }
}
