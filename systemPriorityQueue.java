package com.noahbarkos;

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
            System.out.println("||Starting Up The NoahBark OS||");
            OperatingSystem mainOperatingSystem = new OperatingSystem();
            OperatingSystem.setMethod("PQ");

            systemPCB processcontrolblock1 = new systemPCB();
            processcontrolblock1.setId(1);
            processcontrolblock1.setPriority(6);
            systemProcessManager time = new systemProcessManagerCalulation(processcontrolblock1);

            systemPCB processcontrolblock2 = new systemPCB();
            processcontrolblock2.setId(2);
            processcontrolblock2.setPriority(1);
            systemProcessManager time2 = new systemProcessManagerCalulation(processcontrolblock2);

            mainOperatingSystem.addProcess(time);
            mainOperatingSystem.addProcess(time2);
            mainOperatingSystem.start();
        }
        catch (Exception e)
        {
            miscSystemProcesses.errorMsg(e.getMessage());
        }
    }
}
