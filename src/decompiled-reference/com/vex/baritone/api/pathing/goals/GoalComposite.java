package com.vex.baritone.api.pathing.goals;

import java.util.Arrays;

public class GoalComposite implements Goal {
   private final Goal[] goals;

   public GoalComposite(Goal... var1) {
      this.goals = var1;
   }

   @Override
   public boolean isInGoal(int var1, int var2, int var3) {
      Goal[] var4 = this.goals;
      int var5 = this.goals.length;

      for (int var6 = 0; var6 < var5; var6++) {
         if (var4[var6].isInGoal(var1, var2, var3)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public double heuristic(int var1, int var2, int var3) {
      double var4 = Double.MAX_VALUE;
      Goal[] var6 = this.goals;
      int var7 = this.goals.length;

      for (int var8 = 0; var8 < var7; var8++) {
         Goal var9 = var6[var8];
         var4 = Math.min(var4, var9.heuristic(var1, var2, var3));
      }

      return var4;
   }

   @Override
   public double heuristic() {
      double var1 = Double.MAX_VALUE;
      Goal[] var3 = this.goals;
      int var4 = this.goals.length;

      for (int var5 = 0; var5 < var4; var5++) {
         Goal var6 = var3[var5];
         var1 = Math.min(var1, var6.heuristic());
      }

      return var1;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         var1 = var1;
         return Arrays.equals(this.goals, var1.goals);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Arrays.hashCode(this.goals);
   }

   @Override
   public String toString() {
      return "GoalComposite" + Arrays.toString(this.goals);
   }

   public Goal[] goals() {
      return this.goals;
   }
}
