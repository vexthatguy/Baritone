package com.vex.baritone.config;

import com.vex.baritone.api.command.ICommand;
import java.util.Comparator;
import java.util.List;

/**
 * Sorts the help listing alphabetically so commands are easy to find across
 * pages (the default listing is in registration order, which puts appended
 * commands at the very end).
 */
public final class HelpSorter {

    private HelpSorter() {
    }

    public static void sort(List<ICommand> commands) {
        commands.sort(Comparator.comparing((ICommand c) -> c.getNames().get(0), String.CASE_INSENSITIVE_ORDER));
    }
}
