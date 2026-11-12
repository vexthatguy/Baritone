package com.vex.tools;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;

/**
 * Static link check: for every class in the UI jar, resolve all referenced
 * internal names against the runtime class index (merged MC jar + patched
 * Baritone jar). Reports references that do not exist. Used to prove a build
 * is linkable against its target runtime without launching Minecraft.
 *
 * Usage: LinkCheck <uiJar> <mcMergedJar> <baritoneJar> [<optionalJar>...]
 */
public final class LinkCheck {

    private LinkCheck() {
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 3) {
            System.err.println("Usage: LinkCheck <uiJar> <mcMergedJar> <baritoneJar> [optionalJar...]");
            System.exit(2);
            return;
        }
        Path uiJar = Paths.get(args[0]);
        Set<String> index = new HashSet<>();
        for (int i = 1; i < args.length; i++) {
            indexJar(index, Paths.get(args[i]));
        }

        TreeSet<String> missing = new TreeSet<>();
        int checked = 0;
        try (ZipFile zf = new ZipFile(uiJar.toFile())) {
            Enumeration<? extends ZipEntry> en = zf.entries();
            while (en.hasMoreElements()) {
                ZipEntry e = en.nextElement();
                if (!e.getName().endsWith(".class")) {
                    continue;
                }
                checked++;
                ClassNode cn = new ClassNode();
                try (InputStream in = zf.getInputStream(e)) {
                    new ClassReader(in).accept(cn, 0);
                }
                checkType(index, cn.superName, missing, e.getName());
                for (String itf : cn.interfaces) {
                    checkType(index, itf, missing, e.getName());
                }
                for (var fn : cn.fields) {
                    checkDesc(index, fn.desc, missing, e.getName());
                }
                for (MethodNode mn : cn.methods) {
                    checkDesc(index, mn.desc, missing, e.getName());
                    if (mn.exceptions != null) {
                        for (String ex : mn.exceptions) {
                            checkType(index, ex, missing, e.getName());
                        }
                    }
                    for (AbstractInsnNode insn : mn.instructions) {
                        if (insn instanceof TypeInsnNode t) {
                            if (t.getOpcode() == org.objectweb.asm.Opcodes.NEW
                                || t.getOpcode() == org.objectweb.asm.Opcodes.CHECKCAST
                                || t.getOpcode() == org.objectweb.asm.Opcodes.INSTANCEOF
                                || t.getOpcode() == org.objectweb.asm.Opcodes.ANEWARRAY) {
                                checkType(index, t.desc, missing, e.getName());
                            } else {
                                checkType(index, t.desc, missing, e.getName());
                            }
                        } else if (insn instanceof MethodInsnNode m) {
                            checkType(index, m.owner, missing, e.getName());
                            checkDesc(index, m.desc, missing, e.getName());
                        } else if (insn instanceof FieldInsnNode f) {
                            checkType(index, f.owner, missing, e.getName());
                            checkDesc(index, f.desc, missing, e.getName());
                        }
                    }
                }
            }
        }

        System.out.println("LinkCheck checked " + checked + " classes; missing references: " + missing.size());
        for (String m : missing) {
            System.out.println("  MISSING: " + m);
        }
        if (!missing.isEmpty()) {
            System.exit(1);
        }
        System.out.println("LINK CHECK OK");
    }

    private static void checkDesc(Set<String> index, String desc, Set<String> missing, String source) {
        // object type descriptors like Lnet/minecraft/class_1937;
        int i = 0;
        while (i < desc.length()) {
            char c = desc.charAt(i);
            if (c == 'L') {
                int end = desc.indexOf(';', i);
                if (end < 0) {
                    break;
                }
                checkType(index, desc.substring(i + 1, end), missing, source);
                i = end + 1;
            } else if (c == '[') {
                i++;
            } else {
                i++;
            }
        }
    }

    private static void checkType(Set<String> index, String internal, Set<String> missing, String source) {
        if (internal == null || internal.isEmpty()) {
            return;
        }
        if (internal.startsWith("[")) {
            // array descriptors: verify element type instead
            String el = internal;
            while (el.startsWith("[")) {
                el = el.substring(1);
            }
            if (el.startsWith("L") && el.endsWith(";")) {
                checkType(index, el.substring(1, el.length() - 1), missing, source);
            }
            return;
        }
        if (internal.startsWith("java/") || internal.startsWith("javax/")
            || internal.startsWith("org/") || internal.startsWith("com/mojang/")
            || internal.startsWith("it/unimi/") || internal.startsWith("com/google/")
            || internal.startsWith("net/fabricmc/")) {
            return;
        }
        if (index.contains(internal)) {
            return;
        }
        missing.add(source + " -> " + internal);
    }

    private static void indexJar(Set<String> index, Path jar) throws IOException {
        try (ZipFile zf = new ZipFile(jar.toFile())) {
            Enumeration<? extends ZipEntry> en = zf.entries();
            while (en.hasMoreElements()) {
                ZipEntry e = en.nextElement();
                if (e.getName().endsWith(".class")) {
                    String name = e.getName().substring(0, e.getName().length() - 6);
                    index.add(name);
                }
            }
            System.out.println("indexed " + jar.getFileName() + " (" + index.size() + " types)");
        }
    }
}
