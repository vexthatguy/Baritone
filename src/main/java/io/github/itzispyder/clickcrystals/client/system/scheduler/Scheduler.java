package io.github.itzispyder.clickcrystals.client.system.scheduler;

/**
 * Minimal scheduler shim.
 */
public class Scheduler {
    public void runDelayedTask(Runnable task, long delayMs) {
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(Math.max(0, delayMs));
            } catch (InterruptedException ignored) {
            }
            task.run();
        });
        t.setDaemon(true);
        t.start();
    }
}
