package com.vex.tools;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.commons.ClassRemapper;
import org.objectweb.asm.commons.Remapper;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

/**
 * NamedRemap: translates our intermediary-compiled UI classes to a target
 * named runtime (26.x) using:
 *   1) a class map file (oldInternal -> newInternal, one per line)
 *   2) method/field resolution by exact descriptor probing of the target jar
 *      (unique match by descriptor in the mapped owner class).
 *
 * Usage: NamedRemap <uiJar> <classMapFile> <targetMergedJar> <outJar>
 */
public final class NamedRemap {

    private NamedRemap() {
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 4) {
            System.err.println("Usage: NamedRemap <uiJar> <classMapFile> <targetMergedJar> <outJar>");
            System.exit(2);
            return;
        }
        Path uiJar = Paths.get(args[0]);
        Map<String, String> classMap = new HashMap<>();
        for (String line : Files.readAllLines(Paths.get(args[1]))) {
            String t = line.trim();
            if (t.isEmpty() || t.startsWith("#")) {
                continue;
            }
            String[] p = t.split("\\s+");
            if (p.length >= 2) {
                classMap.put(p[0], p[1]);
            }
        }
        Path target = Paths.get(args[2]);
        Path outJar = Paths.get(args[3]);

        // index target: class -> list of (name, desc), plus superclass map
        Map<String, List<String[]>> methods = new HashMap<>();
        Map<String, List<String[]>> fields = new HashMap<>();
        Map<String, String> superOf = new HashMap<>();
        try (ZipFile zf = new ZipFile(target.toFile())) {
            Enumeration<? extends ZipEntry> en = zf.entries();
            while (en.hasMoreElements()) {
                ZipEntry e = en.nextElement();
                if (!e.getName().endsWith(".class")) {
                    continue;
                }
                String cls = e.getName().substring(0, e.getName().length() - 6);
                ClassNode cn = new ClassNode();
                try (InputStream in = zf.getInputStream(e)) {
                    new ClassReader(in).accept(cn, 0);
                }
                superOf.put(cls, cn.superName);
                List<String[]> ml = new ArrayList<>();
                for (MethodNode mn : cn.methods) {
                    ml.add(new String[]{mn.name, mn.desc});
                }
                methods.put(cls, ml);
                List<String[]> fl = new ArrayList<>();
                for (var fn : cn.fields) {
                    fl.add(new String[]{fn.name, fn.desc});
                }
                fields.put(cls, fl);
            }
        }
        System.out.println("target indexed: " + methods.size() + " classes");

        // hierarchy-aware method lookup: unique name across owner + ancestors
        java.util.function.BiFunction<String, String, String> findMethod = (String owner, String desc) -> {
            List<String> names = new ArrayList<>();
            Set<String> seen = new HashSet<>();
            String cur = owner;
            while (cur != null && seen.add(cur)) {
                List<String[]> ml = methods.get(cur);
                if (ml != null) {
                    for (String[] m : ml) {
                        if (m[1].equals(desc) && !m[0].startsWith("method_")) {
                            names.add(m[0]);
                        }
                    }
                }
                cur = superOf.get(cur);
            }
            if (names.isEmpty()) {
                return null;
            }
            Set<String> unique = new HashSet<>(names);
            if (unique.size() == 1) {
                return names.get(0);
            }
            return null;
        };

        Remapper remapper = new Remapper() {
            @Override
            public String map(String internalName) {
                String mapped = classMap.get(internalName);
                return mapped != null ? mapped : internalName;
            }

            @Override
            public String mapMethodName(String owner, String name, String descriptor) {
                if (!name.startsWith("method_")) {
                    return name;
                }
                String mappedOwner = map(owner);
                List<String[]> ml = methods.get(mappedOwner);
                if (ml == null) {
                    return name;
                }
                String mappedDesc = mapDesc(descriptor);
                String found = findMethod.apply(mappedOwner, mappedDesc);
                if (found != null) {
                    return found;
                }
                return name;
            }

            @Override
            public String mapFieldName(String owner, String name, String descriptor) {
                if (!name.startsWith("field_")) {
                    return name;
                }
                String mappedOwner = map(owner);
                List<String[]> fl = fields.get(mappedOwner);
                if (fl == null) {
                    return name;
                }
                String mappedDesc = mapDesc(descriptor);
                List<String> cands = new ArrayList<>();
                for (String[] f : fl) {
                    if (f[1].equals(mappedDesc) && !f[0].startsWith("field_")) {
                        cands.add(f[0]);
                    }
                }
                if (cands.size() == 1) {
                    return cands.get(0);
                }
                return name;
            }
        };

        int classes = 0;
        Map<String, String> unresolved = new TreeMap<>();
        try (ZipFile zf = new ZipFile(uiJar.toFile());
             ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(outJar))) {
            Enumeration<? extends ZipEntry> en = zf.entries();
            while (en.hasMoreElements()) {
                ZipEntry e = en.nextElement();
                if (!e.getName().endsWith(".class")) {
                    zos.putNextEntry(new ZipEntry(e.getName()));
                    try (InputStream in = zf.getInputStream(e)) {
                        in.transferTo(zos);
                    }
                    zos.closeEntry();
                    continue;
                }
                byte[] bytes;
                try (InputStream in = zf.getInputStream(e)) {
                    bytes = in.readAllBytes();
                }
                ClassNode cn = new ClassNode();
                new ClassReader(bytes).accept(cn, 0);

                // collect unresolved refs
                for (MethodNode mn : cn.methods) {
                    for (var insn : mn.instructions) {
                        if (insn instanceof org.objectweb.asm.tree.MethodInsnNode mi) {
                            if (mi.owner.startsWith("com/vex/baritone")) {
                                continue;
                            }
                            String mo = remapper.map(mi.owner);
                            if (mi.name.startsWith("method_")) {
                                List<String[]> ml = methods.get(mo);
                                boolean found = false;
                                if (ml != null) {
                                    String mappedDesc = remapper.mapDesc(mi.desc);
                                    found = findMethod.apply(mo, mappedDesc) != null;
                                }
                                if (!found) {
                                    unresolved.put(e.getName() + " -> " + mo + "." + mi.name + remapper.mapDesc(mi.desc), "");
                                }
                            }
                        }
                    }
                }

                ClassWriter cw = new ClassWriter(0);
                cn.accept(new ClassRemapper(cw, remapper));
                zos.putNextEntry(new ZipEntry(e.getName()));
                zos.write(cw.toByteArray());
                zos.closeEntry();
                classes++;
            }
        }
        System.out.println("remapped " + classes + " classes -> " + outJar);
        System.out.println("unresolved method refs: " + unresolved.size());
        int shown = 0;
        for (String k : unresolved.keySet()) {
            System.out.println("  UNRESOLVED: " + k);
            if (++shown >= 40) {
                System.out.println("  ...");
                break;
            }
        }
    }
}
