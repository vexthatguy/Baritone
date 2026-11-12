package com.vex.tools;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.ClassRemapper;
import org.objectweb.asm.commons.Remapper;

/**
 * Renames the Baritone package root from {@code baritone} to
 * {@code com.vex.baritone} (and {@code baritone.altoclef} to
 * {@code com.vex.baritone.altoclef}) in a compiled jar, patches the metadata
 * (fabric.mod.json, mixin config, refmap) and injects the compiled
 * {@code com.vex.baritone.altoclef.AltoClefSettings} class.
 */
public final class RenameJar {

   private RenameJar() {
   }

   public static void main(String[] args) throws Exception {
      if (args.length < 3) {
         System.err.println("usage: RenameJar <input.jar> <output.jar> <tools-classes-dir>");
         System.exit(2);
      }

      Path input = Paths.get(args[0]);
      Path output = Paths.get(args[1]);
      Path classesDir = Paths.get(args[2]);

      if (!Files.exists(input)) {
         System.err.println("input jar not found: " + input);
         System.exit(2);
      }

      Remapper remapper = new Remapper() {
         private String mapAll(String internal) {
            if (internal.startsWith("baritone/altoclef/")) {
               return "com/vex/baritone/altoclef/" + internal.substring("baritone/altoclef/".length());
            }
            if (internal.startsWith("baritone/")) {
               return "com/vex/baritone/" + internal.substring("baritone/".length());
            }
            return internal;
         }

         @Override
         public String map(String internalName) {
            return mapAll(internalName);
         }

         @Override
         public String mapMethodName(String owner, String name, String descriptor) {
            return name;
         }

         @Override
         public String mapFieldName(String owner, String name, String descriptor) {
            return name;
         }

         @Override
         public String mapRecordComponentName(String owner, String name, String descriptor) {
            return name;
         }
      };

      java.util.function.Function<String, String> stringMapper = s -> {
         if (s.startsWith("baritone/altoclef/")) {
            return "com/vex/baritone/altoclef/" + s.substring("baritone/altoclef/".length());
         }
         if (s.startsWith("baritone/")) {
            return "com/vex/baritone/" + s.substring("baritone/".length());
         }
         if (s.startsWith("baritone.altoclef.")) {
            return "com.vex.baritone.altoclef." + s.substring("baritone.altoclef.".length());
         }
         if (s.startsWith("baritone.")) {
            return "com.vex.baritone." + s.substring("baritone.".length());
         }
         if (s.equals("baritone")) {
            return "com.vex.baritone";
         }
         return s;
      };

      Files.createDirectories(output.getParent());

      try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(input));
         ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(output))) {

         byte[] buf = new byte[65536];
         ZipEntry entry;
         while ((entry = zis.getNextEntry()) != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int n;
            while ((n = zis.read(buf)) > 0) {
               baos.write(buf, 0, n);
            }
            byte[] data = baos.toByteArray();
            String name = entry.getName();

            if (name.endsWith(".class") && name.startsWith("baritone/")) {
               ClassReader cr = new ClassReader(data);
               ClassWriter cw = new ClassWriter(0);
               ClassVisitor cv = new ClassVisitor(Opcodes.ASM9, new ClassRemapper(cw, remapper)) {
                  @Override
                  public org.objectweb.asm.FieldVisitor visitField(int access, String fname, String descriptor, String signature, Object value) {
                     if (value instanceof String) {
                        value = stringMapper.apply((String) value);
                     }
                     return super.visitField(access, fname, descriptor, signature, value);
                  }

                  @Override
                  public MethodVisitor visitMethod(int access, String mname, String descriptor, String signature, String[] exceptions) {
                     MethodVisitor mv = super.visitMethod(access, mname, descriptor, signature, exceptions);
                     return new MethodVisitor(Opcodes.ASM9, mv) {
                        @Override
                        public void visitLdcInsn(Object value) {
                           if (value instanceof String) {
                              super.visitLdcInsn(stringMapper.apply((String) value));
                           } else {
                              super.visitLdcInsn(value);
                           }
                        }
                     };
                  }
               };
               cr.accept(cv, 0);
               String newName = remapper.map(cr.getClassName()).replace('.', '/') + ".class";
               zos.putNextEntry(new ZipEntry(newName));
               zos.write(cw.toByteArray());
               zos.closeEntry();
               continue;
            }

            if (name.equals("mixins.baritone-meteor.json")
               || name.equals("mixins.baritone-meteor-refmap.json")) {
               String txt = new String(data, StandardCharsets.UTF_8);
               txt = txt.replace("baritone/altoclef/", "com/vex/baritone/altoclef/");
               txt = txt.replace("baritone/", "com/vex/baritone/");
               txt = txt.replace("baritone.altoclef.", "com.vex.baritone.altoclef.");
               txt = txt.replace("baritone.", "com.vex.baritone.");
               data = txt.getBytes(StandardCharsets.UTF_8);
            } else if (name.equals("fabric.mod.json")) {
               // Only drop the icon reference if it points at an asset we do not carry.
               String txt = new String(data, StandardCharsets.UTF_8);
               txt = txt.replaceAll("(?m)\\s*\"icon\":\\s*\"[^\"]*\",?", "");
               data = txt.getBytes(StandardCharsets.UTF_8);
            }

            zos.putNextEntry(new ZipEntry(name));
            zos.write(data);
            zos.closeEntry();
         }

      }

      System.out.println("wrote " + output);
   }
}
