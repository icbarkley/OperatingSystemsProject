package com.company;

public class Utilities {
    static void print(String message) {
        System.out.println(message);
    }
    static void errorMsg(String message)  {
        System.out.println("WRONG " + message);
    }
    static void printHeadLine(String message)  {
        System.out.println("---------" + message + "---------");
    }
    static void printSubLine(String message)  {
        System.out.println("---" + message + "---");
    }
    static void printBreakLine()  {System.out.println("--------------------------------------------------------------");
    }
    static boolean isValid(Process process) {return process.pcb.getId() > 0;}

    static boolean isPriorityQueueMethod() {return  OS.method.equals("PQ");}
    static boolean isRoundRobinMethod() {return  OS.method.equals("RR");}
    static String getMethod() {return OS.method;}
}
