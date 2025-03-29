package parade;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

public class CountdownTimer {
    private final AtomicBoolean timeHasExpired;
    private java.util.Timer internalTimer;

    public CountdownTimer () {
        // Initialize the flag to track whether the time has expired
        this.timeHasExpired = new AtomicBoolean(false);
    }

    public void start(int durationInMilliseconds) {
        // Reset the time-up flag
        timeHasExpired.set(false);

        // Create a new internal timer
        // java.util.Timer creates a separate thread, which runs schedules timertask
        internalTimer = new java.util.Timer();

        // Schedule a task to run after the given duration
        internalTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Mark that the time has expired using atomic boolean across both threads
                timeHasExpired.set(true);

                System.out.println("\n\nTime's up! You didn't respond in time.");
            }
        }, durationInMilliseconds);
    }

    public void stop(){
        internalTimer.cancel();
    }

    public boolean hasTimeExpired() {
        return timeHasExpired.get();
    }
}
