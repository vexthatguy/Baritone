package com.vex.baritone.api.command.datatypes;

import com.vex.baritone.api.command.argument.IArgConsumer;
import com.vex.baritone.api.pathing.goals.GoalBlock;
import com.vex.baritone.api.utils.BetterBlockPos;
import java.util.stream.Stream;
import net.minecraft.class_3532;

public enum RelativeGoalBlock implements IDatatypePost<GoalBlock, BetterBlockPos> {
   INSTANCE;

   public final GoalBlock apply(IDatatypeContext var1, BetterBlockPos var2) {
      if (var2 == null) {
         var2 = BetterBlockPos.ORIGIN;
      }

      IArgConsumer var3 = var1.getConsumer();
      return new GoalBlock(
         class_3532.method_15357(var3.getDatatypePost(RelativeCoordinate.INSTANCE, (double)var2.x)),
         class_3532.method_15357(var3.getDatatypePost(RelativeCoordinate.INSTANCE, (double)var2.y)),
         class_3532.method_15357(var3.getDatatypePost(RelativeCoordinate.INSTANCE, (double)var2.z))
      );
   }

   @Override
   public final Stream<String> tabComplete(IDatatypeContext var1) {
      IArgConsumer var2;
      return (var2 = var1.getConsumer()).hasAtMost(3) ? var2.tabCompleteDatatype(RelativeCoordinate.INSTANCE) : Stream.empty();
   }
}
