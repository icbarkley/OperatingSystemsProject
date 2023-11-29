package com.company;

public class IBThread implements Runnable {
    Thread t;

    public IBThread() {
        Utilities.printHeadLine("In IB thread");
        t = new Thread(this);
        t.start();
    }


    @Override
    public void run() {
        try{
            Utilities.printHeadLine("Start the OS");
            OperatingSystem os = new OperatingSystem();
            OperatingSystem.setMethod("IB");

//        create a process
            ProcessControlBlock pcb3 = new ProcessControlBlock();
            pcb3.setId(3);
//            pcb3.setPriority(6);
            pcb3.setBurstTime(3);
            Process time3= new CalculateProcess(pcb3);

            ProcessControlBlock pcb4 = new ProcessControlBlock();
            pcb4.setId(4);
//            pcb4.setPriority(1);
            pcb4.setBurstTime(2);
            Process time4 = new CalculateProcess(pcb4);

//        add it to the os
            os.addNewProcess(time3);
            os.addNewProcess(time4);
            os.start();
        } catch (Exception e) {
            Utilities.printErr(e.getMessage());
        }
    }
}
