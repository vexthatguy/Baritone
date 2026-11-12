package com.vex.baritone.api.command.datatypes;

import com.vex.baritone.api.command.helpers.TabCompleteHelper;
import java.util.stream.Stream;
import net.minecraft.class_1792;
import net.minecraft.class_2960;
import net.minecraft.class_7923;

public enum ItemById implements IDatatypeFor<class_1792> {
   INSTANCE;

   public final class_1792 get(IDatatypeContext var1) {
      class_2960 var2 = class_2960.method_60654(var1.getConsumer().getString());
      class_1792 var3;
      if ((var3 = (class_1792)class_7923.field_41178.method_17966(var2).orElse(null)) == null) {
         throw new IllegalArgumentException("No item found by that id");
      } else {
         return var3;
      }
   }

   @Override
   public final Stream<String> tabComplete(IDatatypeContext var1) {
      return new TabCompleteHelper()
         .append(class_7923.field_41175.method_10235().stream().map(class_2960::toString))
         .filterPrefixNamespaced(var1.getConsumer().getString())
         .sortAlphabetically()
         .stream();
   }
}
