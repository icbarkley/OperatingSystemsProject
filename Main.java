package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
//         Uncomment and run to see the question
//         question1();
         question2();

    }

    public static void question1() {
        Utilities.print("Question 1: ");
        Scanner scanner = new Scanner(System.in);
        System.out.print("The stimulation of process will run with priority queue method if you press 1, " +
                "it will run with round robin method if you press 2: ");
        String choice = scanner.nextLine();
         while (!choice.equals("1") && !choice.equals("2")) {
             Utilities.printErr("Invalid input, please type (1 or 2): ");
             choice = scanner.nextLine();
        }

        if (Integer.parseInt(choice) == 1) {
            stimulationImplementedPriorityQueue();
        }
        else if (Integer.parseInt(choice) == 2) {
            stimulationImplementedRoundRobin();
        }
    }

    public static void stimulationImplementedRoundRobin() {
        try{
            Utilities.printHeadLine("Start the OS");
            OperatingSystem os = new OperatingSystem();
            OperatingSystem.setMethod("RR");

//        create a process
            ProcessControlBlock pcb3 = new ProcessControlBlock();
            pcb3.setId(3);
            pcb3.setBurstTime(2);
            Process time3= new CalculateProcess(pcb3);

            ProcessControlBlock pcb4 = new ProcessControlBlock();
            pcb4.setId(4);
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

    public static void stimulationImplementedPriorityQueue() {
        try{
            Utilities.printHeadLine("Start the OS");
            OperatingSystem os = new OperatingSystem();
            OperatingSystem.setMethod("PQ");

//        create a process
            ProcessControlBlock pcb1 = new ProcessControlBlock();
            pcb1.setId(1);
            pcb1.setPriority(6);
            Process time = new CalculateProcess(pcb1);

            ProcessControlBlock pcb2 = new ProcessControlBlock();
            pcb2.setId(2);
            pcb2.setPriority(1);
            Process time2 = new CalculateProcess(pcb2);

//        add it to the os
            os.addNewProcess(time);
            os.addNewProcess(time2);
//        start the OS
            os.start();
        } catch (Exception e) {
            Utilities.printErr(e.getMessage());
        }
    }

    public static void question2() {
        Utilities.print("Question 2: ");
        new PQThread();
        new RRThread();
    }
}
