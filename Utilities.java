package com.company;

public class Utilities {
    static void print(String message) {
        System.out.println(message);
    }
    static void printErr(String message)  {
        System.out.println("Error happened: " + message);
    }
    static void printHeadLine(String message)  {
        System.out.println("--------" + message + "--------");
    }
    static void printSubLine(String message)  {
        System.out.println("---" + message);
    }
    static void printBreakLine()  {
        System.out.println("------------------------");
    }
    static boolean isValidProcess(Process process) {return process.processControlBlock.getId() > 0;}

    static boolean isPriorityQueueMethod() {return  OperatingSystem.method.equals("PQ");}
    static boolean isRoundRobinMethod() {return  OperatingSystem.method.equals("RR");}
    static String getMethod() {return OperatingSystem.method;}
}
