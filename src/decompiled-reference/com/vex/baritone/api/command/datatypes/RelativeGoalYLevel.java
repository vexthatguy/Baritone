package com.vex.baritone.api.command.datatypes;

import com.vex.baritone.api.command.argument.IArgConsumer;
import com.vex.baritone.api.pathing.goals.GoalYLevel;
import com.vex.baritone.api.utils.BetterBlockPos;
import java.util.stream.Stream;
import net.minecraft.class_3532;

public enum RelativeGoalYLevel implements IDatatypePost<GoalYLevel, BetterBlockPos> {
   INSTANCE;

   public final GoalYLevel apply(IDatatypeContext var1, BetterBlockPos var2) {
      if (var2 == null) {
         var2 = BetterBlockPos.ORIGIN;
      }

      return new GoalYLevel(class_3532.method_15357(var1.getConsumer().getDatatypePost(RelativeCoordinate.INSTANCE, (double)var2.y)));
   }

   @Override
   public final Stream<String> tabComplete(IDatatypeContext var1) {
      IArgConsumer var2;
      return (var2 = var1.getConsumer()).hasAtMost(1) ? var2.tabCompleteDatatype(RelativeCoordinate.INSTANCE) : Stream.empty();
   }
}
