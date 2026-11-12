package com.vex.baritone.api.command.datatypes;

import com.vex.baritone.api.command.helpers.TabCompleteHelper;
import java.util.Locale;
import java.util.stream.Stream;
import net.minecraft.class_2350;

public enum ForDirection implements IDatatypeFor<class_2350> {
   INSTANCE;

   public final class_2350 get(IDatatypeContext var1) {
      return class_2350.valueOf(var1.getConsumer().getString().toUpperCase(Locale.US));
   }

   @Override
   public final Stream<String> tabComplete(IDatatypeContext var1) {
      return new TabCompleteHelper()
         .append(Stream.of(class_2350.values()).<String>map(class_2350::method_10151).map(String::toLowerCase))
         .filterPrefix(var1.getConsumer().getString())
         .stream();
   }
}
