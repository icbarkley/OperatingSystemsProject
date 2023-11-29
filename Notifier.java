package com.company;
import java.util.Timer;
import java.util.TimerTask;

public class Notifier {
    Timer systemTimer;
    int numberCount = 0;
    int setLimit = 0;
    int realtimeCount = 0;
    int realtimeLimit = 0;
    boolean switched = false;
    public Notifier() {
        systemTimer = new Timer();
    }


    public void toExecute(int seconds)
    {
        if (isSwitched()) {
            numberCount = realtimeCount;
            setLimit = realtimeLimit;
            switched = false;
            Utilities.print("Timer resume");
        } else {
            numberCount = 0;
            setLimit = seconds;
            Utilities.print("Timer execute");
        }
        systemTimer.schedule(new RemindTask(), 0, 1000);

    }

    public void toTerminate() {
        systemTimer.cancel();
    }


    public void contextSwitch()
    {
        Utilities.print("This timer task is on hold and being switched");
        realtimeCount = numberCount;
        realtimeLimit = setLimit;
        numberCount = 0;
        setLimit = 0;
        switched = true;
        systemTimer.cancel();
    }

    public boolean isSwitched() {return switched;}


    class RemindTask extends TimerTask
    {
        public void run()
        {
            Utilities.print("This timer task will be called every 1 second");
            numberCount++;
            OperatingSystem.setIsExecutingAProcess(false);

            if (numberCount == setLimit)
            {
                systemTimer.cancel();

            }

        }
    }
}
