package com.noahbarkos;

import java.util.Scanner;

public class MainFunction {

    public static void main(String[] args)
    {
         scheduling();
  //       multithreading();

    }

    public static void scheduling()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("1: Round Robin | 2: Priority Queue: ");
        String choice = scanner.nextLine();

        while (!choice.equals("1") && !choice.equals("2"))
         {
             miscSystemProcesses.errorMsg("I said 1 OR 2!!!");
             choice = scanner.nextLine();
        }

        if (Integer.parseInt(choice) == 1)
        {
            simulateRoundRobin();
        }
        else if (Integer.parseInt(choice) == 2)
        {
            simulatePriorityQueue();
        }
    }

    public static void simulateRoundRobin()
    {
        try
        {
            System.out.println("-=||Starting Up The NoahBark OS||=-");
            OperatingSystem operatingSystem = new OperatingSystem();
            OperatingSystem.setMethod("RR");

            systemPCB systemPcb3 = new systemPCB();
            systemPcb3.setId(3);
            systemPcb3.setBurstTime(2);
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

    public static void simulatePriorityQueue()
    {
        try
        {
            System.out.println("-=||Starting Up The NoahBark OS||=-");
            OperatingSystem operatingSystem = new OperatingSystem();
            OperatingSystem.setMethod("PQ");

            systemPCB systemPcb1 = new systemPCB();
            systemPcb1.setId(1);
            systemPcb1.setPriority(6);
            systemProcessManager time = new systemProcessManagerCalulation(systemPcb1);

            systemPCB systemPcb2 = new systemPCB();
            systemPcb2.setId(2);
            systemPcb2.setPriority(1);
            systemProcessManager time2 = new systemProcessManagerCalulation(systemPcb2);

            operatingSystem.addProcess(time);
            operatingSystem.addProcess(time2);

            operatingSystem.start();
        }
        catch (Exception e)
        {
            miscSystemProcesses.errorMsg(e.getMessage());
        }
    }

    public static void multithreading()
    {
        new systemPriorityQueue();
        new systemRoundRobin();
    }
}
