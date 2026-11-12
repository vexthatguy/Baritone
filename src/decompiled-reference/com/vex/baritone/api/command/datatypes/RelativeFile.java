package com.vex.baritone.api.command.datatypes;

import com.vex.baritone.api.command.argument.IArgConsumer;
import com.vex.baritone.api.utils.Helper;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.FileSystems;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Stream;
import net.minecraft.class_310;

public enum RelativeFile implements IDatatypePost<File, File> {
   INSTANCE;

   public final File apply(IDatatypeContext var1, File var2) {
      if (var2 == null) {
         var2 = new File("./");
      }

      try {
         var4 = FileSystems.getDefault().getPath(var1.getConsumer().getString());
      } catch (InvalidPathException var3) {
         throw new IllegalArgumentException("invalid path");
      }

      return getCanonicalFileUnchecked(var2.toPath().resolve(var4).toFile());
   }

   @Override
   public final Stream<String> tabComplete(IDatatypeContext var1) {
      return Stream.empty();
   }

   private static File getCanonicalFileUnchecked(File var0) {
      try {
         return var0.getCanonicalFile();
      } catch (IOException var1) {
         throw new UncheckedIOException(var1);
      }
   }

   public static Stream<String> tabComplete(IArgConsumer var0, File var1) {
      var1 = getCanonicalFileUnchecked(var1);
      String var5 = var0.getString();
      Path var2;
      Path var3 = (var2 = FileSystems.getDefault().getPath(var5)).isAbsolute() ? var2.getRoot() : var1.toPath();
      boolean var4 = !var5.isEmpty() && !var5.endsWith(File.separator);
      var1 = var2.isAbsolute() ? var2.toFile() : new File(var1, var5);
      return Stream.of(Objects.requireNonNull(getCanonicalFileUnchecked(var4 ? var1.getParentFile() : var1).listFiles()))
         .map(var2x -> (var2.isAbsolute() ? var2x : var3.relativize(var2x.toPath()).toString()) + (var2x.isDirectory() ? File.separator : ""))
         .filter(var1x -> var1x.toLowerCase(Locale.US).startsWith(var5.toLowerCase(Locale.US)))
         .filter(var0x -> !var0x.contains(" "));
   }

   @Deprecated
   public static File gameDir() {
      return gameDir(Helper.mc);
   }

   public static File gameDir(class_310 var0) {
      File var1;
      return (var1 = var0.field_1697.getAbsoluteFile()).getName().equals(".") ? var1.getParentFile() : var1;
   }
}
