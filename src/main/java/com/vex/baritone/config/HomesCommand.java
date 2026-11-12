package com.vex.baritone.config;

import com.vex.baritone.api.IBaritone;
import com.vex.baritone.api.cache.IWaypoint;
import com.vex.baritone.api.cache.IWaypointCollection;
import com.vex.baritone.api.command.Command;
import com.vex.baritone.api.command.argument.IArgConsumer;
import com.vex.baritone.api.command.exception.CommandInvalidStateException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * #homes [filter] - lists home waypoints for the current world/server.
 */
public class HomesCommand extends SafeCommand {

    public HomesCommand(IBaritone baritone) {
        super(baritone, "homes");
    }

    @Override
    protected void doExecute(String label, IArgConsumer args) throws Throwable {
        args.requireMax(1);
        var collection = waypoints();
        List<IWaypoint> homes = new ArrayList<>(collection.getByTag(IWaypoint.Tag.HOME));
        if (homes.isEmpty()) {
            throw CommandErrors.throwIt(new CommandInvalidStateException("No homes set yet. Use #sethome <name>"));
        }
        String filter = args.hasAny() ? args.getString().toLowerCase() : "";
        homes.sort((a, b) -> a.getName().compareToIgnoreCase(b.getName()));
        int shown = 0;
        for (IWaypoint wp : homes) {
            if (!filter.isEmpty() && !wp.getName().toLowerCase().contains(filter)) {
                continue;
            }
            logDirect(wp.getName() + " @ " + wp.getLocation());
            shown++;
        }
        if (shown == 0) {
            throw CommandErrors.throwIt(new CommandInvalidStateException("No homes match \"" + filter + "\""));
        }
    }

    private IWaypointCollection waypoints() {
        var worldData = baritone.getWorldProvider().getCurrentWorld();
        if (worldData == null) {
            throw CommandErrors.throwIt(new CommandInvalidStateException("No world loaded"));
        }
        return worldData.getWaypoints();
    }

    @Override
    protected Stream<String> doTabComplete(String label, IArgConsumer args) throws Throwable {
        return waypoints().getByTag(IWaypoint.Tag.HOME).stream()
                .map(IWaypoint::getName)
                .distinct();
    }

    @Override
    public String getShortDesc() {
        return "List home waypoints";
    }

    @Override
    public List<String> getLongDesc() {
        return Arrays.asList(
                "Lists all home waypoints for the current world/server.",
                "",
                "Usage:",
                "> homes",
                "> homes <filter> - only homes matching the filter"
        );
    }
}
