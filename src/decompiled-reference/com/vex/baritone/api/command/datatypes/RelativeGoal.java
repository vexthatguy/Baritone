package com.vex.baritone.api.command.datatypes;

import com.vex.baritone.api.command.argument.IArgConsumer;
import com.vex.baritone.api.pathing.goals.Goal;
import com.vex.baritone.api.pathing.goals.GoalBlock;
import com.vex.baritone.api.pathing.goals.GoalXZ;
import com.vex.baritone.api.pathing.goals.GoalYLevel;
import com.vex.baritone.api.utils.BetterBlockPos;
import java.util.stream.Stream;

public enum RelativeGoal implements IDatatypePost<Goal, BetterBlockPos> {
   INSTANCE;

   public final Goal apply(IDatatypeContext var1, BetterBlockPos var2) {
      if (var2 == null) {
         var2 = BetterBlockPos.ORIGIN;
      }

      GoalBlock var3;
      IArgConsumer var4;
      if ((var3 = (var4 = var1.getConsumer()).peekDatatypePostOrNull(RelativeGoalBlock.INSTANCE, var2)) != null) {
         return var3;
      }

      GoalXZ var6;
      if ((var6 = var4.peekDatatypePostOrNull(RelativeGoalXZ.INSTANCE, var2)) != null) {
         return var6;
      }

      GoalYLevel var5;
      return (var5 = var4.peekDatatypePostOrNull(RelativeGoalYLevel.INSTANCE, var2)) != null ? var5 : new GoalBlock(var2);
   }

   @Override
   public final Stream<String> tabComplete(IDatatypeContext var1) {
      return var1.getConsumer().tabCompleteDatatype(RelativeCoordinate.INSTANCE);
   }
}
