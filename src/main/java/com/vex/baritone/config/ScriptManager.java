package com.vex.baritone.config;

import com.vex.baritone.api.utils.IPlayerContext;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Stores scripts as plain text files (one baritone command per line) under
 * <run>/com.vex.baritone/scripts/.
 */
public final class ScriptManager {

    private ScriptManager() {
    }

    public static Path dir(IPlayerContext ctx) {
        return dir(ctx.minecraft().field_1697.toPath());
    }

    public static Path dir(Path runDir) {
        return runDir.resolve("com.vex.baritone").resolve("scripts");
    }

    public static String sanitize(String name) {
        return name.replaceAll("[^A-Za-z0-9._-]", "_");
    }

    public static List<String> list(IPlayerContext ctx) {
        return list(dir(ctx));
    }

    public static List<String> list(Path runDir) {
        List<String> out = new ArrayList<>();
        try {
            Path d = dir(runDir);
            if (Files.isDirectory(d)) {
                try (var stream = Files.list(d)) {
                    stream.filter(p -> p.getFileName().toString().endsWith(".txt"))
                            .forEach(p -> out.add(p.getFileName().toString().substring(0, p.getFileName().toString().length() - 4)));
                }
            }
        } catch (Throwable t) {
            // ignore
        }
        out.sort(Comparator.naturalOrder());
        return out;
    }

    public static List<String> load(IPlayerContext ctx, String name) {
        return load(dir(ctx), name);
    }

    public static List<String> load(Path runDir, String name) {
        List<String> lines = new ArrayList<>();
        try {
            Path f = dir(runDir).resolve(sanitize(name) + ".txt");
            if (Files.isRegularFile(f)) {
                lines.addAll(Files.readAllLines(f, StandardCharsets.UTF_8));
            }
        } catch (Throwable t) {
            // ignore
        }
        return lines;
    }

    public static boolean save(IPlayerContext ctx, String name, List<String> lines) {
        return save(dir(ctx), name, lines);
    }

    public static boolean save(Path runDir, String name, List<String> lines) {
        try {
            Path d = dir(runDir);
            Files.createDirectories(d);
            Files.write(d.resolve(sanitize(name) + ".txt"), lines, StandardCharsets.UTF_8);
            return true;
        } catch (Throwable t) {
            return false;
        }
    }

    public static boolean delete(IPlayerContext ctx, String name) {
        return delete(dir(ctx), name);
    }

    public static boolean delete(Path runDir, String name) {
        try {
            return Files.deleteIfExists(dir(runDir).resolve(sanitize(name) + ".txt"));
        } catch (Throwable t) {
            return false;
        }
    }
}
