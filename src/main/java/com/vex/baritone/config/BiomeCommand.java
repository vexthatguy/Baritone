package com.vex.baritone.config;

import com.vex.baritone.api.IBaritone;
import com.vex.baritone.api.command.Command;
import com.vex.baritone.api.command.argument.IArgConsumer;
import com.vex.baritone.api.command.exception.CommandInvalidStateException;
import com.vex.baritone.api.pathing.goals.GoalXZ;
import com.vex.baritone.api.utils.BetterBlockPos;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.class_1937;
import net.minecraft.class_2338;

/**
 * #biome [name] - reports the current biome or paths to the nearest biome.
 * #biome list [filter] - lists biomes.
 */
public class BiomeCommand extends SafeCommand {

    private static final int SEARCH_RADIUS = 128;

    public BiomeCommand(IBaritone baritone) {
        super(baritone, "biome");
    }

    @Override
    protected void doExecute(String label, IArgConsumer args) throws Throwable {
        args.requireMax(2);
        if (!args.hasAny()) {
            reportCurrent();
            return;
        }
        String first = args.getString();
        if (first.equalsIgnoreCase("list")) {
            listBiomes(args);
            return;
        }
        String wanted = first.toLowerCase();
        if (args.hasAny()) {
            wanted = (wanted + " " + args.getString()).trim();
        }
        List<String> ids = resolveBiomeIds(wanted);
        if (ids.isEmpty()) {
            throw CommandErrors.throwIt(new CommandInvalidStateException("Unknown biome \"" + wanted + "\". Use #biome list"));
        }
        BetterBlockPos feet = ctx.playerFeet();
        BetterBlockPos found = searchNearest(feet, ids);
        if (found == null) {
            throw CommandErrors.throwIt(new CommandInvalidStateException("Could not find \"" + wanted + "\" within " + SEARCH_RADIUS + " blocks"));
        }
        logDirect("Found " + ids.get(0) + " at " + found.x + ", " + found.z);
        baritone.getCustomGoalProcess().setGoalAndPath(new GoalXZ(found.x, found.z));
    }

    private void reportCurrent() {
        var entry = world().method_23753(ctx.playerFeet());
        var keyOpt = entry.method_40230();
        String name = keyOpt.isPresent() ? keyOpt.get().method_29177().toString() : "unknown";
        logDirect("You are in biome " + name);
    }

    private void listBiomes(IArgConsumer args) throws Throwable {
        args.requireMax(1);
        String filter = args.hasAny() ? args.getString().toLowerCase() : "";
        var reg = biomeRegistry();
        List<String> names = reg.method_10220()
                .map(v -> reg.method_29113(v))
                .filter(java.util.Optional::isPresent)
                .map(o -> o.get().method_29177().toString())
                .filter(id -> filter.isEmpty() || id.contains(filter))
                .sorted()
                .collect(Collectors.toList());
        if (names.isEmpty()) {
            throw CommandErrors.throwIt(new CommandInvalidStateException("No biomes match \"" + filter + "\""));
        }
        int shown = Math.min(names.size(), 48);
        logDirect("Biomes (" + shown + "/" + names.size() + "):");
        for (int i = 0; i < shown; i++) {
            logDirect(names.get(i));
        }
        if (shown < names.size()) {
            logDirect("..." + (names.size() - shown) + " more");
        }
    }

    private List<String> resolveBiomeIds(String wanted) {
        var reg = biomeRegistry();
        return reg.method_10220()
                .map(v -> reg.method_29113(v))
                .filter(java.util.Optional::isPresent)
                .map(o -> o.get().method_29177())
                .filter(id -> {
                    String full = id.toString();
                    String path = id.method_12832();
                    return full.equalsIgnoreCase(wanted) || path.equalsIgnoreCase(wanted) || full.toLowerCase().contains(wanted);
                })
                .map(Object::toString)
                .distinct()
                .collect(Collectors.toList());
    }

    private BetterBlockPos searchNearest(BetterBlockPos center, List<String> ids) {
        class_1937 world = world();
        int cy = center.y;
        for (int r = 0; r <= SEARCH_RADIUS; r++) {
            for (int x = -r; x <= r; x++) {
                for (int z = -r; z <= r; z++) {
                    if (Math.max(Math.abs(x), Math.abs(z)) != r) {
                        continue;
                    }
                    var entry = world.method_23753(new class_2338(center.x + x, cy, center.z + z));
                    var keyOpt = entry.method_40230();
                    if (keyOpt.isPresent() && ids.contains(keyOpt.get().method_29177().toString())) {
                        return new BetterBlockPos(center.x + x, cy, center.z + z);
                    }
                }
            }
        }
        return null;
    }

    private class_1937 world() {
        class_1937 w = ctx.world();
        if (w == null) {
            throw CommandErrors.throwIt(new CommandInvalidStateException("No world loaded"));
        }
        return w;
    }

    @SuppressWarnings("unchecked")
    private net.minecraft.class_2378<net.minecraft.class_1959> biomeRegistry() {
        var keyOpt = world().method_23753(ctx.playerFeet()).method_40230();
        if (!keyOpt.isPresent()) {
            throw CommandErrors.throwIt(new CommandInvalidStateException("Could not resolve biome registry"));
        }
        return (net.minecraft.class_2378<net.minecraft.class_1959>) (Object)
                world().method_30349().method_30530((net.minecraft.class_5321) keyOpt.get());
    }

    @Override
    protected Stream<String> doTabComplete(String label, IArgConsumer args) throws Throwable {
        if (!args.hasAny()) {
            return Stream.of("list");
        }
        String prefix = args.peekString().toLowerCase();
        var reg = biomeRegistry();
        return Stream.concat(
                Stream.of("list"),
                reg.method_10220()
                        .map(v -> reg.method_29113(v))
                        .filter(java.util.Optional::isPresent)
                        .map(o -> o.get().method_29177().toString())
                        .filter(id -> id.toLowerCase().startsWith(prefix))
        ).distinct().sorted();
    }

    @Override
    public String getShortDesc() {
        return "Find or report biomes";
    }

    @Override
    public List<String> getLongDesc() {
        return Arrays.asList(
                "The biome command reports your current biome or paths to the nearest matching biome.",
                "",
                "Usage:",
                "> biome - report your current biome",
                "> biome <name> - path to the nearest biome matching the name",
                "> biome list [filter] - list biome ids"
        );
    }
}
