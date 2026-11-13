package com.vex.baritone.config;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Resolves the mod version from our own fabric.mod.json at runtime. Root
 * resources like /fabric.mod.json are shared across the Fabric classpath, so
 * we must pick the file whose id is baritone-meteor, not whichever is found
 * first (other mods' dependencies ship their own fabric.mod.json).
 */
public final class VersionInfo {

    public static final String MOD_ID = "baritone-meteor";

    private static final String VERSION = load();

    private VersionInfo() {
    }

    public static String get() {
        return VERSION;
    }

    private static String load() {
        try {
            Enumeration<java.net.URL> urls = VersionInfo.class.getClassLoader().getResources("fabric.mod.json");
            while (urls.hasMoreElements()) {
                java.net.URL url = urls.nextElement();
                String json;
                try (InputStream in = url.openStream()) {
                    json = new String(in.readAllBytes(), StandardCharsets.UTF_8);
                }
                if (MOD_ID.equals(jsonField(json, "id"))) {
                    String v = jsonField(json, "version");
                    return v != null ? v : "unknown";
                }
            }
            return "unknown";
        } catch (Exception e) {
            return "unknown";
        }
    }

    private static String jsonField(String json, String field) {
        Matcher m = Pattern.compile("\"" + Pattern.quote(field) + "\"\\s*:\\s*\"([^\"]+)\"").matcher(json);
        return m.find() ? m.group(1) : null;
    }
}
