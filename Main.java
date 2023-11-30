package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
//         Uncomment and run to see the question
         scheduling();
//         multithreading();

    }

    public static void scheduling() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Priority queue: press 1, Round robin: press 2: ");
        String choice = scanner.nextLine();
         while (!choice.equals("1") && !choice.equals("2")) {
             Utilities.errorMsg("I said 1 OR 2!!!");
             choice = scanner.nextLine();
        }

        if (Integer.parseInt(choice) == 1) {
            simulatePriorityQueue();
        }
        else if (Integer.parseInt(choice) == 2) {
            simulateRoundRobin();
        }
    }

    public static void simulateRoundRobin() {
        try{
            Utilities.printHeadLine("Start the OS");
            OS os = new OS();
            OS.setMethod("RR");

//        create a process
            PCB pcb3 = new PCB();
            pcb3.setId(3);
            pcb3.setBurstTime(2);
            Process time3= new ProcessCalulation(pcb3);

            PCB pcb4 = new PCB();
            pcb4.setId(4);
            pcb4.setBurstTime(2);
            Process time4 = new ProcessCalulation(pcb4);

//        add it to the os
            os.addProcess(time3);
            os.addProcess(time4);
            os.start();
        } catch (Exception e) {
            Utilities.errorMsg(e.getMessage());
        }
    }

    public static void simulatePriorityQueue() {
        try{
            Utilities.printHeadLine("Start the OS");
            OS os = new OS();
            OS.setMethod("PQ");

//        create a process
            PCB pcb1 = new PCB();
            pcb1.setId(1);
            pcb1.setPriority(6);
            Process time = new ProcessCalulation(pcb1);

            PCB pcb2 = new PCB();
            pcb2.setId(2);
            pcb2.setPriority(1);
            Process time2 = new ProcessCalulation(pcb2);

//        add it to the os
            os.addProcess(time);
            os.addProcess(time2);
//        start the OS
            os.start();
        } catch (Exception e) {
            Utilities.errorMsg(e.getMessage());
        }
    }

    public static void multithreading() {
        new systemPriorityQueue();
        new RoundRobin();
    }
}
