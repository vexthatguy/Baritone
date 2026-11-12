package com.vex.baritone.api.command.datatypes;

import com.vex.baritone.api.command.argument.IArgConsumer;
import com.vex.baritone.api.utils.BetterBlockPos;
import java.util.stream.Stream;

public enum RelativeBlockPos implements IDatatypePost<BetterBlockPos, BetterBlockPos> {
   INSTANCE;

   public final BetterBlockPos apply(IDatatypeContext var1, BetterBlockPos var2) {
      if (var2 == null) {
         var2 = BetterBlockPos.ORIGIN;
      }

      IArgConsumer var3 = var1.getConsumer();
      return new BetterBlockPos(
         var3.getDatatypePost(RelativeCoordinate.INSTANCE, (double)var2.x),
         var3.getDatatypePost(RelativeCoordinate.INSTANCE, (double)var2.y),
         var3.getDatatypePost(RelativeCoordinate.INSTANCE, (double)var2.z)
      );
   }

   @Override
   public final Stream<String> tabComplete(IDatatypeContext var1) {
      IArgConsumer var2;
      if ((var2 = var1.getConsumer()).hasAny() && !var2.has(4)) {
         while (var2.has(2) && var2.peekDatatypeOrNull(RelativeCoordinate.INSTANCE) != null) {
            var2.get();
         }

         return var2.tabCompleteDatatype(RelativeCoordinate.INSTANCE);
      } else {
         return Stream.empty();
      }
   }
}
