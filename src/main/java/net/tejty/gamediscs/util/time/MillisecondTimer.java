package net.tejty.gamediscs.util.time;

import java.time.Instant;

public class MillisecondTimer {
    private Instant startTime;

    public MillisecondTimer() {
        reset();
    }

    public void reset() {
        startTime = Instant.now();
    }

    public long getElapsedTime() {
        return Instant.now().toEpochMilli() - startTime.toEpochMilli();
    }
}