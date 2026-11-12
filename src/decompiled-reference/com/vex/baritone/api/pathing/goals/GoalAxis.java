package com.vex.baritone.api.pathing.goals;

import com.vex.baritone.api.BaritoneAPI;

public class GoalAxis implements Goal {
   private static final double SQRT_2_OVER_2 = Math.sqrt(2.0) / 2.0;

   @Override
   public boolean isInGoal(int var1, int var2, int var3) {
      return var2 == BaritoneAPI.getSettings().axisHeight.value && (var1 == 0 || var3 == 0 || Math.abs(var1) == Math.abs(var3));
   }

   @Override
   public double heuristic(int var1, int var2, int var3) {
      var1 = Math.abs(var1);
      var3 = Math.abs(var3);
      int var4 = Math.min(var1, var3);
      var4 = Math.max(var1, var3) - var4;
      return Math.min(var1, Math.min(var3, var4 * SQRT_2_OVER_2)) * BaritoneAPI.getSettings().costHeuristic.value
         + GoalYLevel.calculate(BaritoneAPI.getSettings().axisHeight.value, var2);
   }

   @Override
   public boolean equals(Object var1) {
      return var1.getClass() == GoalAxis.class;
   }

   @Override
   public int hashCode() {
      return 201385781;
   }

   @Override
   public String toString() {
      return "GoalAxis";
   }
}
