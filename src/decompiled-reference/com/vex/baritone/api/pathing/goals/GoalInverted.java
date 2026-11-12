package com.vex.baritone.api.pathing.goals;

import java.util.Objects;

public class GoalInverted implements Goal {
   public final Goal origin;

   public GoalInverted(Goal var1) {
      this.origin = var1;
   }

   @Override
   public boolean isInGoal(int var1, int var2, int var3) {
      return false;
   }

   @Override
   public double heuristic(int var1, int var2, int var3) {
      return -this.origin.heuristic(var1, var2, var3);
   }

   @Override
   public double heuristic() {
      return Double.NEGATIVE_INFINITY;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         var1 = var1;
         return Objects.equals(this.origin, var1.origin);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return this.origin.hashCode() * 495796690;
   }

   @Override
   public String toString() {
      return String.format("GoalInverted{%s}", this.origin.toString());
   }
}
