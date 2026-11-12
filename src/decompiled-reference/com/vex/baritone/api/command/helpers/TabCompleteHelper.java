package com.vex.baritone.api.command.helpers;

import com.vex.baritone.api.BaritoneAPI;
import com.vex.baritone.api.Settings;
import com.vex.baritone.api.command.manager.ICommandManager;
import com.vex.baritone.api.utils.SettingsUtil;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.class_2960;

public class TabCompleteHelper {
   private Stream<String> stream;

   public TabCompleteHelper(String[] var1) {
      this.stream = Stream.of(var1);
   }

   public TabCompleteHelper(List<String> var1) {
      this.stream = var1.stream();
   }

   public TabCompleteHelper() {
      this.stream = Stream.empty();
   }

   public TabCompleteHelper append(Stream<String> var1) {
      this.stream = Stream.concat(this.stream, var1);
      return this;
   }

   public TabCompleteHelper append(String... var1) {
      return this.append(Stream.of(var1));
   }

   public TabCompleteHelper append(Class<? extends Enum<?>> var1) {
      return this.append(Stream.of((Enum[])var1.getEnumConstants()).map(Enum::name).map(String::toLowerCase));
   }

   public TabCompleteHelper prepend(Stream<String> var1) {
      this.stream = Stream.concat(var1, this.stream);
      return this;
   }

   public TabCompleteHelper prepend(String... var1) {
      return this.prepend(Stream.of(var1));
   }

   public TabCompleteHelper prepend(Class<? extends Enum<?>> var1) {
      return this.prepend(Stream.of((Enum[])var1.getEnumConstants()).map(Enum::name).map(String::toLowerCase));
   }

   public TabCompleteHelper map(Function<String, String> var1) {
      this.stream = this.stream.map(var1);
      return this;
   }

   public TabCompleteHelper filter(Predicate<String> var1) {
      this.stream = this.stream.filter(var1);
      return this;
   }

   public TabCompleteHelper sort(Comparator<String> var1) {
      this.stream = this.stream.sorted(var1);
      return this;
   }

   public TabCompleteHelper sortAlphabetically() {
      return this.sort(String.CASE_INSENSITIVE_ORDER);
   }

   public TabCompleteHelper filterPrefix(String var1) {
      return this.filter(var1x -> var1x.toLowerCase(Locale.US).startsWith(var1.toLowerCase(Locale.US)));
   }

   public TabCompleteHelper filterPrefixNamespaced(String var1) {
      class_2960 var2;
      if ((var2 = class_2960.method_12829(var1)) == null) {
         this.stream = Stream.empty();
         return this;
      } else {
         return this.filterPrefix(var2.toString());
      }
   }

   public String[] build() {
      return this.stream.toArray(String[]::new);
   }

   public Stream<String> stream() {
      return this.stream;
   }

   public TabCompleteHelper addCommands(ICommandManager var1) {
      return this.append(var1.getRegistry().descendingStream().flatMap(var0 -> var0.getNames().stream()).distinct());
   }

   public TabCompleteHelper addSettings() {
      return this.append(
         BaritoneAPI.getSettings().allSettings.stream().filter(var0 -> !var0.isJavaOnly()).map(Settings.Setting::getName).sorted(String.CASE_INSENSITIVE_ORDER)
      );
   }

   public TabCompleteHelper addModifiedSettings() {
      return this.append(SettingsUtil.modifiedSettings(BaritoneAPI.getSettings()).stream().map(Settings.Setting::getName).sorted(String.CASE_INSENSITIVE_ORDER));
   }

   public TabCompleteHelper addToggleableSettings() {
      return this.append(
         BaritoneAPI.getSettings().getAllValuesByType(Boolean.class).stream().map(Settings.Setting::getName).sorted(String.CASE_INSENSITIVE_ORDER)
      );
   }
}
