package com.vex.baritone.config;

/**
 * Sneaky-throw helper: the Baritone command API declares no checked
 * exceptions on ICommand.execute, yet its exception classes are checked.
 * This lets us throw them without declaring (same trick the stock jar uses).
 */
public final class CommandErrors {

    private CommandErrors() {
    }

    @SuppressWarnings("unchecked")
    public static <T extends Throwable> RuntimeException throwIt(Throwable t) throws T {
        throw (T) t;
    }
}
