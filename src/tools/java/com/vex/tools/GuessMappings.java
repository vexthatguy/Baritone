package com.vex.tools;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Suggests candidate renames for missing type references by matching path
 * suffixes against a target runtime index. Reads the missing-reference list
 * from stdin arguments (one per line, format "<source> -> <oldName>") and a
 * target jar.
 *
 * Usage: GuessMappings <missingListFile> <targetJar>
 */
public final class GuessMappings {

    private GuessMappings() {
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.err.println("Usage: GuessMappings <missingListFile> <targetJar>");
            System.exit(2);
            return;
        }
        List<String> missingLines = java.nio.file.Files.readAllLines(Paths.get(args[0]));
        Path target = Paths.get(args[1]);

        TreeMap<String, Integer> index = new TreeMap<>();
        try (ZipFile zf = new ZipFile(target.toFile())) {
            Enumeration<? extends ZipEntry> en = zf.entries();
            while (en.hasMoreElements()) {
                ZipEntry e = en.nextElement();
                if (e.getName().endsWith(".class")) {
                    index.put(e.getName().substring(0, e.getName().length() - 6), 0);
                }
            }
        }

        System.out.println("# Suggested renames (best match by path suffix)");
        System.out.println("# old -> new (score)");
        for (String line : missingLines) {
            int arrow = line.indexOf(" -> ");
            if (arrow < 0) {
                continue;
            }
            String oldName = line.substring(arrow + 4).trim();
            String[] parts = oldName.split("/");
            String tail = parts[parts.length - 1];

            String best = null;
            int bestScore = -1;
            for (Map.Entry<String, Integer> e : index.entrySet()) {
                String n = e.getKey();
                if (!n.endsWith(tail)) {
                    continue;
                }
                // score by suffix length overlap on path segments
                String[] np = n.split("/");
                int score = 0;
                int i = parts.length - 1;
                int j = np.length - 1;
                while (i >= 0 && j >= 0 && parts[i].equals(np[j])) {
                    score++;
                    i--;
                    j--;
                }
                if (score > bestScore) {
                    bestScore = score;
                    best = n;
                }
            }
            System.out.println(oldName + " -> " + (best != null ? best : "(no candidate)") + " (" + bestScore + ")");
        }
    }
}
