package com.vex.baritone.api.pathing.goals;

import com.vex.baritone.api.pathing.movement.ActionCosts;
import com.vex.baritone.api.utils.SettingsUtil;

public class GoalYLevel implements Goal, ActionCosts {
   public final int level;

   public GoalYLevel(int var1) {
      this.level = var1;
   }

   @Override
   public boolean isInGoal(int var1, int var2, int var3) {
      return var2 == this.level;
   }

   @Override
   public double heuristic(int var1, int var2, int var3) {
      return calculate(this.level, var2);
   }

   public static double calculate(int var0, int var1) {
      if (var1 > var0) {
         return FALL_N_BLOCKS_COST[2] / 2.0 * (var1 - var0);
      } else {
         return var1 < var0 ? (var0 - var1) * JUMP_ONE_BLOCK_COST : 0.0;
      }
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         var1 = var1;
         return this.level == var1.level;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return this.level * 1271009915;
   }

   @Override
   public String toString() {
      return String.format("GoalYLevel{y=%s}", SettingsUtil.maybeCensor(this.level));
   }
}
