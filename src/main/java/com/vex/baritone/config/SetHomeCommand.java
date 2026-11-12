package com.vex.baritone.config;

import com.vex.baritone.api.IBaritone;
import com.vex.baritone.api.cache.IWaypoint;
import com.vex.baritone.api.cache.IWaypointCollection;
import com.vex.baritone.api.cache.Waypoint;
import com.vex.baritone.api.command.Command;
import com.vex.baritone.api.command.argument.IArgConsumer;
import com.vex.baritone.api.command.exception.CommandInvalidStateException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * #sethome [name] - sets a named home waypoint at your feet.
 */
public class SetHomeCommand extends Command {

    public SetHomeCommand(IBaritone baritone) {
        super(baritone, "sethome", "addhome");
    }

    @Override
    public void execute(String label, IArgConsumer args) {
        args.requireMax(1);
        String name = args.hasAny() ? args.getString() : "home";
        var collection = waypoints();
        // Overwrite any existing home with the same name.
        for (IWaypoint wp : collection.getByTag(IWaypoint.Tag.HOME).toArray(new IWaypoint[0])) {
            if (wp.getName().equalsIgnoreCase(name)) {
                collection.removeWaypoint(wp);
            }
        }
        collection.addWaypoint(new Waypoint(name, IWaypoint.Tag.HOME, ctx.playerFeet()));
        logDirect("Set home \"" + name + "\" at " + ctx.playerFeet());
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
        return Stream.empty();
    }

    @Override
    public String getShortDesc() {
        return "Set your home waypoint";
    }

    @Override
    public List<String> getLongDesc() {
        return Arrays.asList(
                "Sets a named home waypoint at your feet.",
                "",
                "Usage:",
                "> sethome - sets home named \"home\"",
                "> sethome <name> - sets a named home"
        );
    }
}
