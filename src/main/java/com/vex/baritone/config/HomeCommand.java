package com.vex.baritone.config;

import com.vex.baritone.api.IBaritone;
import com.vex.baritone.api.cache.IWaypoint;
import com.vex.baritone.api.cache.IWaypointCollection;
import com.vex.baritone.api.command.Command;
import com.vex.baritone.api.command.argument.IArgConsumer;
import com.vex.baritone.api.command.exception.CommandInvalidStateException;
import com.vex.baritone.api.pathing.goals.GoalBlock;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * #home [name] - paths to a specific named home waypoint.
 */
public class HomeCommand extends SafeCommand {

    public HomeCommand(IBaritone baritone) {
        super(baritone, "home");
    }

    @Override
    protected void doExecute(String label, IArgConsumer args) throws Throwable {
        args.requireMax(1);
        IWaypoint home;
        if (args.hasAny()) {
            String name = args.getString();
            home = findByName(name);
            if (home == null) {
                throw CommandErrors.throwIt(new CommandInvalidStateException("No home named \"" + name + "\""));
            }
        } else {
            home = waypoints().getMostRecentByTag(IWaypoint.Tag.HOME);
            if (home == null) {
                throw CommandErrors.throwIt(new CommandInvalidStateException("No home set yet. Use #sethome <name>"));
            }
        }
        baritone.getCustomGoalProcess().setGoalAndPath(new GoalBlock(home.getLocation()));
        logDirect("Going to home \"" + home.getName() + "\"");
    }

    private IWaypoint findByName(String name) {
        IWaypoint best = null;
        long bestTs = Long.MIN_VALUE;
        for (IWaypoint wp : waypoints().getByTag(IWaypoint.Tag.HOME)) {
            if (wp.getName().equalsIgnoreCase(name) && wp.getCreationTimestamp() > bestTs) {
                best = wp;
                bestTs = wp.getCreationTimestamp();
            }
        }
        return best;
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
        return "Path to your home waypoint";
    }

    @Override
    public List<String> getLongDesc() {
        return Arrays.asList(
                "The home command paths to a specific named home waypoint.",
                "",
                "Usage:",
                "> home - path to your most recent home",
                "> home <name> - path to the named home"
        );
    }
}
