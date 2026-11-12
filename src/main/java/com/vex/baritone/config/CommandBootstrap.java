package com.vex.baritone.config;

import com.vex.baritone.api.IBaritone;
import com.vex.baritone.api.command.Command;
import com.vex.baritone.api.command.ICommand;
import java.util.List;

/**
 * Appends the custom commands to the default command list. Called via ASM
 * from DefaultCommands.a(...) before the list is frozen. The baritone
 * instance is taken from an existing command in the list (DefaultCommands.a
 * reuses its parameter slot after last use, so we cannot rely on it).
 */
public final class CommandBootstrap {

    private CommandBootstrap() {
    }

    public static void install(List<ICommand> commands) {
        IBaritone baritone = null;
        for (ICommand command : commands) {
            if (command instanceof Command cmd) {
                baritone = cmd.baritone;
                break;
            }
        }
        if (baritone == null) {
            return;
        }
        commands.add(new HomeCommand(baritone));
        commands.add(new SetHomeCommand(baritone));
        commands.add(new DelHomeCommand(baritone));
        commands.add(new HomesCommand(baritone));
        commands.add(new BiomeCommand(baritone));
        commands.add(new ScriptCommand(baritone));
    }
}
