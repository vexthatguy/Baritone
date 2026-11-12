package com.vex.baritone.config;

import com.vex.baritone.api.utils.IPlayerContext;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * Small config for Remastered-only features. Stored as a properties file in
 * <run>/com.vex.baritone/remastered.properties so we don't depend on the
 * stock Settings registry (which is hard to extend from outside the jar).
 */
public final class FeatureConfig {

    private static final String FILE_NAME = "remastered.properties";

    private static Properties props;
    private static Path loadedFrom;

    private FeatureConfig() {
    }

    public static boolean deathCoordinates(IPlayerContext ctx) {
        return bool(ctx, "deathCoordinates", true);
    }

    public static boolean deathWaypoint(IPlayerContext ctx) {
        return bool(ctx, "deathWaypoint", true);
    }

    public static boolean scriptsEnabled(IPlayerContext ctx) {
        return bool(ctx, "scriptsEnabled", true);
    }

    private static boolean bool(IPlayerContext ctx, String key, boolean dflt) {
        try {
            Properties p = load(ctx);
            String v = p.getProperty(key);
            return v == null ? dflt : Boolean.parseBoolean(v);
        } catch (Throwable t) {
            return dflt;
        }
    }

    private static synchronized Properties load(IPlayerContext ctx) {
        try {
            Path dir = ctx.minecraft().field_1697.toPath().resolve("com.vex.baritone");
            Files.createDirectories(dir);
            if (props == null || !dir.equals(loadedFrom)) {
                Properties p = new Properties();
                Path file = dir.resolve(FILE_NAME);
                if (Files.isRegularFile(file)) {
                    try (InputStream in = Files.newInputStream(file)) {
                        p.load(in);
                    }
                }
                props = p;
                loadedFrom = dir;
            }
        } catch (Throwable t) {
            if (props == null) {
                props = new Properties();
            }
        }
        return props;
    }

    public static void save(IPlayerContext ctx, String key, boolean value) {
        try {
            Properties p = load(ctx);
            p.setProperty(key, Boolean.toString(value));
            Path file = loadedFrom.resolve(FILE_NAME);
            try (OutputStream out = Files.newOutputStream(file)) {
                p.store(out, "Baritone Remastered feature config");
            }
        } catch (Throwable t) {
            // ignore
        }
    }
}
