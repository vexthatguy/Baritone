package com.vex.baritone.config;

/**
 * Builds a stable per-server folder key for waypoint storage. Keeps the
 * folder distinct per server address (and per realm), sanitized for the
 * filesystem.
 */
public final class WorldKey {

    private WorldKey() {
    }

    public static String serverKey(net.minecraft.class_642 info) {
        if (info == null) {
            return "unknown";
        }
        String raw;
        try {
            raw = info.method_52811() ? info.field_3752 : info.field_3761;
        } catch (Throwable t) {
            raw = info.field_3761;
        }
        if (raw == null || raw.isEmpty()) {
            raw = "server";
        }
        String key = raw.replaceAll("[^A-Za-z0-9._-]", "_");
        return key.isEmpty() ? "server" : key;
    }
}
