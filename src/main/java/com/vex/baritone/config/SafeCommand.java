package com.vex.baritone.config;

import com.vex.baritone.api.IBaritone;
import com.vex.baritone.api.command.Command;
import com.vex.baritone.api.command.argument.IArgConsumer;
import java.util.stream.Stream;

/**
 * Command base that converts the checked argument exceptions of some MC
 * versions into Baritone's sneaky-throw form so implementations compile
 * unchanged across versions.
 */
public abstract class SafeCommand extends Command {

    protected SafeCommand(IBaritone baritone, String... names) {
        super(baritone, names);
    }

    @Override
    public final void execute(String label, IArgConsumer args) {
        try {
            doExecute(label, args);
        } catch (Throwable t) {
            throw CommandErrors.throwIt(t);
        }
    }

    @Override
    public final Stream<String> tabComplete(String label, IArgConsumer args) {
        try {
            return doTabComplete(label, args);
        } catch (Throwable t) {
            return Stream.empty();
        }
    }

    protected abstract void doExecute(String label, IArgConsumer args) throws Throwable;

    protected Stream<String> doTabComplete(String label, IArgConsumer args) throws Throwable {
        return Stream.empty();
    }
}
