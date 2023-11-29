package com.company;

public class RoundRobin implements Runnable {
    Thread t;

    public RoundRobin() {
        Utilities.printHeadLine("In RR thread");
        t = new Thread(this);
        t.start();
    }


    @Override
    public void run() {
        try{
            Utilities.printHeadLine("Start the OS");
            OS os = new OS();
            OS.setMethod("RR");

//        create a process
            PCB pcb3 = new PCB();
            pcb3.setId(3);
//            pcb3.setPriority(6);
            pcb3.setBurstTime(3);
            Process time3= new ProcessCalulation(pcb3);

            PCB pcb4 = new PCB();
            pcb4.setId(4);
//            pcb4.setPriority(1);
            pcb4.setBurstTime(2);
            Process time4 = new ProcessCalulation(pcb4);

//        add it to the os
            os.addProcess(time3);
            os.addProcess(time4);
            os.start();
        } catch (Exception e) {
            Utilities.printErr(e.getMessage());
        }
    }
}
