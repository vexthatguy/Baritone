package com.vex.baritone.config;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Resolves the mod version from fabric.mod.json at runtime. The manifest
 * Implementation-Version attribute is not honored by Fabric's classloader,
 * so this is the reliable source.
 */
public final class VersionInfo {

    private static final String VERSION = load();

    private VersionInfo() {
    }

    public static String get() {
        return VERSION;
    }

    private static String load() {
        try (InputStream in = VersionInfo.class.getResourceAsStream("/fabric.mod.json")) {
            if (in == null) {
                return "unknown";
            }
            String txt = new String(in.readAllBytes(), StandardCharsets.UTF_8);
            Matcher m = Pattern.compile("\"version\"\\s*:\\s*\"([^\"]+)\"").matcher(txt);
            if (m.find()) {
                return m.group(1);
            }
            return "unknown";
        } catch (Exception e) {
            return "unknown";
        }
    }
}
