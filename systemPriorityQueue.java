package com.company;

public class systemPriorityQueue implements Runnable
{
    Thread pqThread;
    public systemPriorityQueue()
    {
        System.out.println("||In Priority Queue Thread||");
        pqThread = new Thread(this);
        pqThread.start();
    }

    @Override
    public void run()
    {
        try
        {
            System.out.println("||Starting Up Operating System||");
            OS mainOS = new OS();
            OS.setMethod("PQ");

            PCB processcontrolblock1 = new PCB();
            processcontrolblock1.setId(1);
            processcontrolblock1.setPriority(6);
            Process time = new ProcessCalulation(processcontrolblock1);

            PCB processcontrolblock2 = new PCB();
            processcontrolblock2.setId(2);
            processcontrolblock2.setPriority(1);
            Process time2 = new ProcessCalulation(processcontrolblock2);

            mainOS.addProcess(time);
            mainOS.addProcess(time2);
            mainOS.start();
        }
        catch (Exception e)
        {
            Utilities.errorMsg(e.getMessage());
        }
    }
}
