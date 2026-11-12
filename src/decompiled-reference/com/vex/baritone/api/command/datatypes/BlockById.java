package com.vex.baritone.api.command.datatypes;

import com.vex.baritone.api.command.helpers.TabCompleteHelper;
import java.util.stream.Stream;
import net.minecraft.class_2248;
import net.minecraft.class_2960;
import net.minecraft.class_7923;

public enum BlockById implements IDatatypeFor<class_2248> {
   INSTANCE;

   public final class_2248 get(IDatatypeContext var1) {
      class_2960 var2 = class_2960.method_60654(var1.getConsumer().getString());
      class_2248 var3;
      if ((var3 = (class_2248)class_7923.field_41175.method_17966(var2).orElse(null)) == null) {
         throw new IllegalArgumentException("no block found by that id");
      } else {
         return var3;
      }
   }

   @Override
   public final Stream<String> tabComplete(IDatatypeContext var1) {
      String var2 = var1.getConsumer().getString();
      return new TabCompleteHelper()
         .append(class_7923.field_41175.method_10235().stream().map(Object::toString))
         .filterPrefixNamespaced(var2)
         .sortAlphabetically()
         .stream();
   }
}
