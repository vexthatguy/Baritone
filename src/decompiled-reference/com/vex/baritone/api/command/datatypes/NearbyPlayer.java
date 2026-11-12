package com.vex.baritone.api.command.datatypes;

import com.vex.baritone.api.command.helpers.TabCompleteHelper;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.class_1657;
import net.minecraft.class_2561;

public enum NearbyPlayer implements IDatatypeFor<class_1657> {
   INSTANCE;

   public final class_1657 get(IDatatypeContext var1) {
      String var2 = var1.getConsumer().getString();
      return getPlayers(var1).stream().filter(var1x -> var1x.method_5477().getString().equalsIgnoreCase(var2)).findFirst().orElse(null);
   }

   @Override
   public final Stream<String> tabComplete(IDatatypeContext var1) {
      return new TabCompleteHelper()
         .append(getPlayers(var1).stream().map(class_1657::method_5477).map(class_2561::getString))
         .filterPrefix(var1.getConsumer().getString())
         .sortAlphabetically()
         .stream();
   }

   private static List<? extends class_1657> getPlayers(IDatatypeContext var0) {
      return var0.getBaritone().getPlayerContext().world().method_18456();
   }
}
