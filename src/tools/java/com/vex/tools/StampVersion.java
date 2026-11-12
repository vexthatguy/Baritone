package com.vex.tools;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Copies a jar and rewrites the version field in fabric.mod.json.
 * Optionally injects extra classes (UI) and assets, and adds a modmenu
 * entrypoint to the merged fabric.mod.json.
 *
 * Usage:
 *   StampVersion <inputJar> <outputJar> <newVersion> [uiClassesDir] [assetsDir]
 */
public final class StampVersion {

    private StampVersion() {
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 3) {
            System.err.println("Usage: StampVersion <inputJar> <outputJar> <newVersion> [uiClassesDir] [assetsDir] [uiConfigDir]");
            System.exit(1);
            return;
        }
        Path input = Paths.get(args[0]);
        Path output = Paths.get(args[1]);
        String newVersion = args[2];
        Path uiClassesDir = args.length > 3 && !args[3].isBlank() ? Paths.get(args[3]) : null;
        Path assetsDir = args.length > 4 && !args[4].isBlank() ? Paths.get(args[4]) : null;
        Path uiConfigDir = args.length > 5 && !args[5].isBlank() ? Paths.get(args[5]) : null;

        Files.createDirectories(output.getParent());
        boolean sawFabricMod = false;
        boolean modMenuEntry = false;
        boolean sawManifest = false;
        try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(input));
             ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(output))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                byte[] data = readFully(zis);
                String name = entry.getName();
                if (name.equals("META-INF/MANIFEST.MF")) {
                    sawManifest = true;
                    String txt = new String(data, StandardCharsets.UTF_8);
                    if (!txt.contains("Implementation-Version:")) {
                        txt = txt.replaceFirst("Manifest-Version:\\s*[0-9.]+",
                            "$0\nImplementation-Version: " + newVersion);
                    }
                    data = txt.getBytes(StandardCharsets.UTF_8);
                } else if (name.equals("fabric.mod.json")) {
                    String txt = new String(data, StandardCharsets.UTF_8);
                    txt = txt.replaceFirst("\"version\"\\s*:\\s*\"[^\"]*\"", "\"version\": \"" + newVersion + "\"");
                    txt = rewriteMetadata(txt);
                    if (uiClassesDir != null) {
                        txt = injectModMenuEntrypoint(txt);
                        modMenuEntry = txt.contains("\"modmenu\": [");
                    }
                    if (!txt.contains("\"icon\"")) {
                        txt = txt.replaceFirst("\"version\"\\s*:\\s*\"[^\"]*\"",
                            "$0,\n  \"icon\": \"assets/baritone/icon.png\"");
                    }
                    if (!txt.contains("\"mixins.baritone-visibility.json\"")) {
                        txt = txt.replaceFirst("\"mixins\"\\s*:\\s*\\[",
                            "\"mixins\": [\n    \"mixins.baritone-visibility.json\",");
                    }
                    data = txt.getBytes(StandardCharsets.UTF_8);
                    sawFabricMod = true;
                }
                zos.putNextEntry(new ZipEntry(name));
                zos.write(data);
                zos.closeEntry();
            }

            if (uiClassesDir != null && Files.isDirectory(uiClassesDir)) {
                try (Stream<Path> paths = Files.walk(uiClassesDir)) {
                    for (Path p : paths.filter(Files::isRegularFile).toList()) {
                        String rel = uiClassesDir.relativize(p).toString().replace('\\', '/');
                        if (!rel.endsWith(".class")) {
                            continue;
                        }
                        zos.putNextEntry(new ZipEntry(rel));
                        zos.write(Files.readAllBytes(p));
                        zos.closeEntry();
                    }
                }
            }

            if (assetsDir != null && Files.isDirectory(assetsDir)) {
                try (Stream<Path> paths = Files.walk(assetsDir)) {
                    for (Path p : paths.filter(Files::isRegularFile).toList()) {
                        String rel = assetsDir.relativize(p).toString().replace('\\', '/');
                        zos.putNextEntry(new ZipEntry("assets/" + rel));
                        zos.write(Files.readAllBytes(p));
                        zos.closeEntry();
                    }
                }
            }

            if (uiConfigDir != null && Files.isDirectory(uiConfigDir)) {
                Path cfg = uiConfigDir.resolve("mixins.baritone-visibility.json");
                if (Files.isRegularFile(cfg)) {
                    zos.putNextEntry(new ZipEntry("mixins.baritone-visibility.json"));
                    zos.write(Files.readAllBytes(cfg));
                    zos.closeEntry();
                }
            }

            if (!sawManifest) {
                zos.putNextEntry(new ZipEntry("META-INF/MANIFEST.MF"));
                zos.write(("Manifest-Version: 1.0\r\nImplementation-Version: " + newVersion + "\r\n\r\n")
                    .getBytes(StandardCharsets.UTF_8));
                zos.closeEntry();
                System.out.println("manifest created with Implementation-Version " + newVersion);
            }
        }
        if (!sawFabricMod) {
            System.err.println("Warning: fabric.mod.json not found in input jar");
        }
        if (sawManifest) {
            System.out.println("manifest Implementation-Version set to " + newVersion);
        }
        if (uiClassesDir != null && !modMenuEntry) {
            System.err.println("Warning: could not add modmenu entrypoint");
        }
        System.out.println("wrote " + output + " (version " + newVersion + ")");
    }

    /**
     * Replaces the entire entrypoints object with a well-formed one that
     * includes our modmenu entrypoint. Handles both the empty "{}" form and
     * any existing content (balanced-brace span replacement).
     */
    private static String injectModMenuEntrypoint(String txt) {
        String entryBlock = "\"entrypoints\": {\n"
                + "    \"modmenu\": [\n"
                + "      \"com.vex.baritone.config.ModMenuIntegration\"\n"
                + "    ]\n"
                + "  }";

        int idx = txt.indexOf("\"entrypoints\"");
        if (idx < 0) {
            return txt;
        }
        int colon = txt.indexOf(':', idx);
        if (colon < 0) {
            return txt;
        }
        int open = txt.indexOf('{', colon);
        if (open < 0) {
            return txt;
        }
        // find matching close brace (JSON object, no nesting complexity here)
        int end = txt.indexOf('}', open);
        if (end < 0) {
            return txt;
        }
        return txt.substring(0, idx) + entryBlock + txt.substring(end + 1);
    }

    /**
     * Rewrites the branding to this remaster: sole author, Modrinth profile as
     * homepage, project page as sources, no issue tracker, and no Discord
     * links anywhere in the metadata.
     */
    private static String rewriteMetadata(String json) {
        json = json.replaceFirst("\"name\"\\s*:\\s*\"[^\"]*\"", "\"name\": \"Baritone Remastered\"");
        json = replaceArrayField(json, "authors", "[\n    \"vexthatguy\"\n  ]");
        json = replaceObjectField(json, "contact",
            "{\n    \"homepage\": \"https://modrinth.com/user/vexthatguy\",\n"
                + "    \"sources\": \"https://modrinth.com/project/baritone-remastered\"\n  }");
        json = removeObjectField(json, "custom");
        return json;
    }

    private static String replaceArrayField(String json, String field, String replacement) {
        int idx = json.indexOf('"' + field + '"');
        if (idx < 0) {
            return json;
        }
        int open = json.indexOf('[', idx);
        if (open < 0) {
            return json;
        }
        int close = matching(json, open, '[', ']');
        if (close < 0) {
            return json;
        }
        return json.substring(0, open) + replacement + json.substring(close + 1);
    }

    private static String replaceObjectField(String json, String field, String replacement) {
        int idx = json.indexOf('"' + field + '"');
        if (idx < 0) {
            return json;
        }
        int open = json.indexOf('{', idx);
        if (open < 0) {
            return json;
        }
        int close = matching(json, open, '{', '}');
        if (close < 0) {
            return json;
        }
        return json.substring(0, open) + replacement + json.substring(close + 1);
    }

    private static String removeObjectField(String json, String field) {
        int idx = json.indexOf('"' + field + '"');
        if (idx < 0) {
            return json;
        }
        int open = json.indexOf('{', idx);
        if (open < 0) {
            return json;
        }
        int close = matching(json, open, '{', '}');
        if (close < 0) {
            return json;
        }
        // Also drop the separating comma so the document stays valid.
        int start = idx;
        while (start > 0 && Character.isWhitespace(json.charAt(start - 1))) {
            start--;
        }
        int end = close + 1;
        if (start > 0 && json.charAt(start - 1) == ',') {
            start--;
        } else {
            while (end < json.length() && Character.isWhitespace(json.charAt(end))) {
                end++;
            }
            if (end < json.length() && json.charAt(end) == ',') {
                end++;
            }
        }
        return json.substring(0, start) + json.substring(end);
    }

    private static int matching(String text, int openIndex, char openChar, char closeChar) {
        int depth = 0;
        boolean inString = false;
        boolean escaped = false;
        for (int i = openIndex; i < text.length(); i++) {
            char c = text.charAt(i);
            if (inString) {
                if (escaped) {
                    escaped = false;
                } else if (c == '\\') {
                    escaped = true;
                } else if (c == '"') {
                    inString = false;
                }
                continue;
            }
            if (c == '"') {
                inString = true;
            } else if (c == openChar) {
                depth++;
            } else if (c == closeChar) {
                depth--;
                if (depth == 0) {
                    return i;
                }
            }
        }
        return -1;
    }

    private static byte[] readFully(InputStream in) throws IOException {
        java.io.ByteArrayOutputStream bos = new java.io.ByteArrayOutputStream();
        byte[] buf = new byte[8192];
        int n;
        while ((n = in.read(buf)) != -1) {
            bos.write(buf, 0, n);
        }
        return bos.toByteArray();
    }
}
