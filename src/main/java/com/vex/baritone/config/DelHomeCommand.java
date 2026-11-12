package com.vex.baritone.config;

import com.vex.baritone.api.IBaritone;
import com.vex.baritone.api.cache.IWaypoint;
import com.vex.baritone.api.cache.IWaypointCollection;
import com.vex.baritone.api.command.Command;
import com.vex.baritone.api.command.argument.IArgConsumer;
import com.vex.baritone.api.command.exception.CommandInvalidStateException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * #delhome [name] - deletes home waypoint(s).
 */
public class DelHomeCommand extends Command {

    public DelHomeCommand(IBaritone baritone) {
        super(baritone, "delhome");
    }

    @Override
    public void execute(String label, IArgConsumer args) {
        args.requireMax(1);
        var collection = waypoints();
        if (args.hasAny()) {
            String name = args.getString();
            int removed = 0;
            for (IWaypoint wp : collection.getByTag(IWaypoint.Tag.HOME).toArray(new IWaypoint[0])) {
                if (wp.getName().equalsIgnoreCase(name)) {
                    collection.removeWaypoint(wp);
                    removed++;
                }
            }
            if (removed == 0) {
                throw CommandErrors.throwIt(new CommandInvalidStateException("No home named \"" + name + "\""));
            }
            logDirect("Deleted " + removed + " home(s) named \"" + name + "\"");
        } else {
            int removed = 0;
            for (IWaypoint wp : collection.getByTag(IWaypoint.Tag.HOME).toArray(new IWaypoint[0])) {
                collection.removeWaypoint(wp);
                removed++;
            }
            logDirect(removed == 0 ? "No homes to delete" : "Deleted all " + removed + " home(s)");
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
    public Stream<String> tabComplete(String label, IArgConsumer args) {
        return waypoints().getByTag(IWaypoint.Tag.HOME).stream()
                .map(IWaypoint::getName)
                .distinct();
    }

    @Override
    public String getShortDesc() {
        return "Delete home waypoints";
    }

    @Override
    public List<String> getLongDesc() {
        return Arrays.asList(
                "Deletes home waypoint(s).",
                "",
                "Usage:",
                "> delhome - deletes all homes",
                "> delhome <name> - deletes the named home"
        );
    }
}
