package aie.easyAPI.utils;

import aie.easyAPI.excepation.StopWatchException;

import java.util.concurrent.TimeUnit;

public class StopWatch {

    private boolean isRunning;

    private long startTime;
    private long elapsedTime;

    public void start() {
        if (isRunning)
            throw new StopWatchException("Stop Watch already Started");
        startTime = System.nanoTime();
        isRunning = true;
    }

    public void stop() {
        if (!isRunning)
            throw new StopWatchException("Stop Watch didn't start yet");
        elapsedTime = System.nanoTime() - startTime;
        isRunning = false;
    }

    public long getElapsedTime(TimeUnit timeUnit) {
        return timeUnit.convert(elapsedTime, TimeUnit.NANOSECONDS);
    }
}
