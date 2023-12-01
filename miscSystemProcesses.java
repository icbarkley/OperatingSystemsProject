package com.noahbarkos;

public class miscSystemProcesses
{
    static void errorMsg(String message)
    {
        System.out.println("WRONG " + message);
    }
    static boolean isValid(systemProcessManager systemProcessManager)
    {
        return systemProcessManager.systemProcessControlBlock.retrieveId() > 0;
    }

    static boolean priorityQueueMethod()
    {
        return  OperatingSystem.method.equals("PQ");
    }
    static boolean roundRobinMethod()
    {
        return  OperatingSystem.method.equals("RR");
    }
    static String retrieveMethod()
    {
        return OperatingSystem.method;
    }
}
