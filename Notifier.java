package com.company;
import java.util.Timer;
import java.util.TimerTask;

public class Notifier {
    Timer timer;
    int count = 0;
    int limit = 0;
    int currentCount = 0;
    int currentLimit = 0;
    boolean switched = false; //determine context switch condition
    public Notifier() {
        timer = new Timer();
    }

    // Execute the timer task with task = Remind Task
    public void toExecute(int seconds) {
        // If being switched back, resume current context
        if (isSwitched()) {
            count = currentCount;
            limit = currentLimit;
            switched = false;
            Utilities.print("Timer resume");
        } else {
            count = 0;
            limit = seconds;
            Utilities.print("Timer execute");
        }
        timer.schedule(new RemindTask(), 0, 1000); // schedule the task

    }

    public void toTerminate() {
        timer.cancel();
    }

    // If process is switched out of running queue, save the current context
    // and cancel the process
    public void contextSwitch() {
        Utilities.print("This timer task is onhold and being switched");
        currentCount = count;
        currentLimit = limit;
        count = 0;
        limit = 0;
        switched = true;
        timer.cancel();
    }

    public boolean isSwitched() {return switched;}

    // Here to modify the task being looped
    class RemindTask extends TimerTask {
        public void run() {
            Utilities.print("This timer task will be called every 1 second");
            count++;
            OperatingSystem.setIsExecutingAProcess(false);
            if (count == limit) {
                timer.cancel(); //Terminate the timer thread

            }

        }
    }
}
