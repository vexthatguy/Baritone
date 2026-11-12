package com.vex.baritone.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Lightweight key=value config at config/baritone.cfg.
 *
 * <p>A single switch controls the whole mod: enabled means normal operation
 * and visible in ModMenu; disabled means Baritone is inert and hidden from
 * ModMenu. There is deliberately no separate visibility option.</p>
 */
public final class VisibilityConfig {
    private static final Path FILE = Paths.get("config", "baritone.cfg");
    private static boolean enabled = true;

    private VisibilityConfig() {
    }

    private static boolean loaded = false;

    public static void load() {
        if (loaded) {
            return;
        }
        loaded = true;
        Properties p = new Properties();
        if (Files.exists(FILE)) {
            try (InputStream in = Files.newInputStream(FILE)) {
                p.load(in);
            } catch (IOException ignored) {
            }
        }
        enabled = !"false".equalsIgnoreCase(p.getProperty("enabled", "true"));
    }

    public static boolean isEnabled() {
        load();
        return enabled;
    }

    public static boolean isDisabled() {
        load();
        return !enabled;
    }

    /** Visibility in ModMenu is derived from the master switch. */
    public static boolean isModMenuHidden() {
        return isDisabled();
    }

    public static void setEnabled(boolean value) {
        if (!value) {
            shutdownNow();
        }
        enabled = value;
        store();
    }

    /**
     * Live shutdown: cancels everything the running bot is doing, then flips
     * the gate so future getProvider() calls return the no-op provider.
     */
    private static void shutdownNow() {
        try {
            com.vex.baritone.api.IBaritoneProvider provider = com.vex.baritone.api.BaritoneAPI.getProvider();
            if (provider == null || provider == NoopBaritoneProvider.INSTANCE) {
                return;
            }
            for (com.vex.baritone.api.IBaritone bot : provider.getAllBaritones()) {
                if (bot == null) {
                    continue;
                }
                cancelAll(bot);
            }
        } catch (Throwable ignored) {
        }
    }

    /** Stops pathing, every process and every visual leftover on one bot. */
    private static void cancelAll(com.vex.baritone.api.IBaritone bot) {
        try {
            if (bot.getPathingBehavior() != null) {
                bot.getPathingBehavior().cancelEverything();
            }
        } catch (Throwable ignored) {
        }
        try {
            if (bot.getInputOverrideHandler() != null) {
                bot.getInputOverrideHandler().clearAllKeys();
            }
        } catch (Throwable ignored) {
        }

        cancel(bot.getCustomGoalProcess());
        cancel(bot.getMineProcess());
        cancel(bot.getBuilderProcess());
        cancel(bot.getExploreProcess());
        cancel(bot.getFarmProcess());
        cancel(bot.getFollowProcess());
        cancel(bot.getGetToBlockProcess());
        cancel(bot.getElytraProcess());

        try {
            if (bot.getCustomGoalProcess() != null) {
                bot.getCustomGoalProcess().setGoal(null);
            }
        } catch (Throwable ignored) {
        }
        try {
            if (bot.getSelectionManager() != null) {
                bot.getSelectionManager().removeAllSelections();
            }
        } catch (Throwable ignored) {
        }
    }

    private static void cancel(com.vex.baritone.api.process.IBaritoneProcess process) {
        if (process == null) {
            return;
        }
        try {
            if (process.isActive()) {
                process.onLostControl();
            }
        } catch (Throwable ignored) {
        }
    }

    private static void store() {
        try {
            Files.createDirectories(FILE.getParent());
            Properties p = new Properties();
            p.setProperty("enabled", Boolean.toString(enabled));
            // Kept for readability; always derived from "enabled".
            p.setProperty("modMenuHidden", Boolean.toString(!enabled));
            try (OutputStream out = Files.newOutputStream(FILE)) {
                p.store(out, "Baritone master switch (ON = enabled + visible in ModMenu)");
            }
        } catch (IOException ignored) {
        }
    }
}
