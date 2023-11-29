package com.company;

public class NLThread implements Runnable{
    Thread t;
    public NLThread() {
        Utilities.printHeadLine("In thread NL");
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        try {
            Utilities.printHeadLine("Start the OS");
            OperatingSystem os = new OperatingSystem();
            OperatingSystem.setMethod("NL");

//        create a process
            ProcessControlBlock pcb1 = new ProcessControlBlock();
            pcb1.setId(1);
            pcb1.setPriority(6);
//            pcb1.setBurstTime(5);
            Process time = new CalculateProcess(pcb1);

            ProcessControlBlock pcb2 = new ProcessControlBlock();
            pcb2.setId(2);
            pcb2.setPriority(1);
//            pcb2.setBurstTime(3);
            Process time2 = new CalculateProcess(pcb2);


//        add it to the os
            os.addNewProcess(time);
            os.addNewProcess(time2);
            os.start();
        } catch (Exception e) {
            Utilities.printErr(e.getMessage());
        }
    }
}