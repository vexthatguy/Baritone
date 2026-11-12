package com.vex.tools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import net.fabricmc.tinyremapper.TinyRemapper;
import net.fabricmc.tinyremapper.TinyUtils;

/**
 * Remaps already-compiled Remastered classes from intermediary names to a
 * target namespace (e.g. yarn/official for Forge builds).
 *
 * Usage:
 *   RemapClasses <inputDir> <mappingsTiny> <fromNs> <toNs> <outputDir>
 */
public final class RemapClasses {

    private RemapClasses() {
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 5) {
            System.err.println("Usage: RemapClasses <inputDir> <mappingsTiny> <fromNs> <toNs> <outputDir>");
            System.exit(2);
            return;
        }
        Path input = Paths.get(args[0]);
        Path mappings = Paths.get(args[1]);
        String from = args[2];
        String to = args[3];
        Path output = Paths.get(args[4]);
        Files.createDirectories(output);

        // package input dir as a jar for readInputs
        Path inputJar = output.resolve("ui-input.jar");
        try (var zos = new java.util.zip.ZipOutputStream(Files.newOutputStream(inputJar))) {
            try (var paths = Files.walk(input)) {
                for (Path p : paths.filter(Files::isRegularFile).toList()) {
                    String rel = input.relativize(p).toString().replace('\\', '/');
                    zos.putNextEntry(new java.util.zip.ZipEntry(rel));
                    zos.write(Files.readAllBytes(p));
                    zos.closeEntry();
                }
            }
        }

        TinyRemapper remapper = TinyRemapper.newRemapper()
                .withMappings(TinyUtils.createTinyMappingProvider(mappings, from, to))
                .ignoreConflicts(true)
                .build();
        java.util.Map<String, byte[]> remapped = new java.util.concurrent.ConcurrentHashMap<>();
        try {
            remapper.readInputs(inputJar);
            remapper.apply((name, bytes) -> remapped.put(name, bytes));
        } finally {
            remapper.finish();
        }
        try (var zip = new java.util.zip.ZipOutputStream(Files.newOutputStream(output.resolve("ui-remapped.jar")))) {
            for (var en : remapped.entrySet()) {
                zip.putNextEntry(new java.util.zip.ZipEntry(en.getKey() + ".class"));
                zip.write(en.getValue());
                zip.closeEntry();
            }
        }
        System.out.println("wrote " + output.resolve("ui-remapped.jar") + " (" + remapped.size() + " classes)");
    }
}
