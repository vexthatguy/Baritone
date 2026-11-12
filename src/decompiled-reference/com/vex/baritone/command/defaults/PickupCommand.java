package com.vex.baritone.command.defaults;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.command.Command;
import com.vex.baritone.api.command.argument.IArgConsumer;
import com.vex.baritone.api.command.datatypes.ItemById;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.class_1792;
import net.minecraft.class_2960;
import net.minecraft.class_7923;

public class PickupCommand extends Command {
   public PickupCommand(Baritone var1) {
      super(var1, "pickup");
   }

   @Override
   public void execute(String var1, IArgConsumer var2) {
      HashSet var4 = new HashSet();

      while (var2.hasAny()) {
         class_1792 var3 = var2.getDatatypeFor(ItemById.INSTANCE);
         var4.add(var3);
      }

      if (var4.isEmpty()) {
         super.com.vex.baritone.getFollowProcess().pickup(var0 -> true);
         this.logDirect("Picking up all items");
      } else {
         super.com.vex.baritone.getFollowProcess().pickup(var1x -> var4.contains(var1x.method_7909()));
         this.logDirect("Picking up these items:");
         var4.stream().map(class_7923.field_41178::method_10221).<String>map(class_2960::toString).forEach(this::logDirect);
      }
   }

   @Override
   public Stream<String> tabComplete(String var1, IArgConsumer var2) {
      while (var2.has(2)) {
         if (var2.peekDatatypeOrNull(ItemById.INSTANCE) == null) {
            return Stream.empty();
         }

         var2.get();
      }

      return var2.tabCompleteDatatype(ItemById.INSTANCE);
   }

   @Override
   public String getShortDesc() {
      return "Pickup items";
   }

   @Override
   public List<String> getLongDesc() {
      return Arrays.asList("Usage:", "> pickup - Pickup anything", "> pickup <item1> <item2> <...> - Pickup certain items");
   }
}
