package com.company;

public class RoundRobin implements Runnable
{
    Thread sysThread;

    public RoundRobin()
    {
        System.out.println("||In RR thread||");
        sysThread = new Thread(this);
        sysThread.start();
    }


    @Override
    public void run()
    {
        try
        {
            System.out.println("||Turning on the Operating System||");
            OS os = new OS();
            OS.setMethod("RR");

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


            os.addProcess(time3);
            os.addProcess(time4);
            os.start();
        }
        catch (Exception e)
        {
            Utilities.errorMsg(e.getMessage());
        }
    }
}
