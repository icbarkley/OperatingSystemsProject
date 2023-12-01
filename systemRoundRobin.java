package com.noahbarkos;

public class systemRoundRobin implements Runnable
{
    Thread sysThread;

    public systemRoundRobin()
    {
        System.out.println("||In Round Robin thread||");
        sysThread = new Thread(this);
        sysThread.start();
    }


    @Override
    public void run()
    {
        try
        {
            System.out.println("||Starting Up The NoahBark OS||");
            OperatingSystem operatingSystem = new OperatingSystem();
            OperatingSystem.setMethod("RR");

            systemPCB systemPcb3 = new systemPCB();
            systemPcb3.setId(3);
            systemPcb3.setBurstTime(3);
            systemProcessManager time3= new systemProcessManagerCalulation(systemPcb3);

            systemPCB systemPcb4 = new systemPCB();
            systemPcb4.setId(4);
            systemPcb4.setBurstTime(2);
            systemProcessManager time4 = new systemProcessManagerCalulation(systemPcb4);


            operatingSystem.addProcess(time3);
            operatingSystem.addProcess(time4);
            operatingSystem.start();
        }
        catch (Exception e)
        {
            miscSystemProcesses.errorMsg(e.getMessage());
        }
    }
}
