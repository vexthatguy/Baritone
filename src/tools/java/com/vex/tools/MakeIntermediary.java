package com.vex.tools;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import net.fabricmc.tinyremapper.OutputConsumerPath;
import net.fabricmc.tinyremapper.TinyRemapper;
import net.fabricmc.tinyremapper.TinyUtils;

/**
 * Merges the Mojang client+server jars and remaps them from official
 * (obfuscated) names to intermediary names using the intermediary v2 tiny
 * mapping. The output jar is shaped like Loom's
 * minecraft-merged-intermediary and can be used as mc_intermediary_jar.
 *
 * Usage:
 *   MakeIntermediary <client.jar> <server.jar> <intermediary.tiny> <merged.jar> <output.jar>
 */
public final class MakeIntermediary {

    private MakeIntermediary() {
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 5) {
            System.err.println("Usage: MakeIntermediary <client.jar> <server.jar> <intermediary.tiny> <merged.jar> <output.jar> [SKIP]");
            System.exit(2);
            return;
        }
        Path client = Paths.get(args[0]);
        Path server = Paths.get(args[1]);
        Path mappings = Paths.get(args[2]);
        Path merged = Paths.get(args[3]);
        Path output = Paths.get(args[4]);
        boolean skipRemap = args.length > 5 && args[5].equals("SKIP");

        // -- merge client + server (client wins on duplicates) ----------------
        System.out.println("merging " + client + " + " + server);
        Map<String, byte[]> entries = new LinkedHashMap<>();
        addZip(entries, client);
        addZip(entries, server);
        writeZip(merged, entries);
        System.out.println("merged " + entries.size() + " entries -> " + merged);

        if (skipRemap) {
            Files.copy(merged, output, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            System.out.println("wrote " + output + " (SKIP remap)");
            return;
        }

        // -- remap official -> intermediary -----------------------------------
        System.out.println("remapping with " + mappings);
        TinyRemapper remapper = TinyRemapper.newRemapper()
                .withMappings(TinyUtils.createTinyMappingProvider(mappings, "official", "intermediary"))
                .ignoreConflicts(true)
                .build();
        try (OutputConsumerPath out = new OutputConsumerPath(output)) {
            remapper.readInputs(merged);
            remapper.apply(out);
            out.addNonClassFiles(merged);
        } finally {
            remapper.finish();
        }
        System.out.println("wrote " + output);
    }

    private static void addZip(Map<String, byte[]> out, Path jar) throws IOException {
        try (ZipFile zf = new ZipFile(jar.toFile())) {
            var en = zf.entries();
            while (en.hasMoreElements()) {
                ZipEntry e = en.nextElement();
                if (e.isDirectory()) {
                    continue;
                }
                if (!out.containsKey(e.getName())) {
                    try (InputStream in = zf.getInputStream(e)) {
                        out.put(e.getName(), in.readAllBytes());
                    }
                }
            }
        }
    }

    private static void writeZip(Path jar, Map<String, byte[]> entries) throws IOException {
        try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(jar))) {
            for (var en : entries.entrySet()) {
                zos.putNextEntry(new ZipEntry(en.getKey()));
                zos.write(en.getValue());
                zos.closeEntry();
            }
        }
    }
}
