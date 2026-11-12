package com.vex.baritone.api.pathing.goals;

import com.vex.baritone.api.BaritoneAPI;
import com.vex.baritone.api.utils.BetterBlockPos;
import com.vex.baritone.api.utils.SettingsUtil;
import net.minecraft.class_243;
import net.minecraft.class_3532;

public class GoalXZ implements Goal {
   private static final double SQRT_2 = Math.sqrt(2.0);
   private final int x;
   private final int z;

   public GoalXZ(int var1, int var2) {
      this.x = var1;
      this.z = var2;
   }

   public GoalXZ(BetterBlockPos var1) {
      this.x = var1.x;
      this.z = var1.z;
   }

   @Override
   public boolean isInGoal(int var1, int var2, int var3) {
      return var1 == this.x && var3 == this.z;
   }

   @Override
   public double heuristic(int var1, int var2, int var3) {
      var1 -= this.x;
      var2 = var3 - this.z;
      return calculate(var1, var2);
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         var1 = var1;
         return this.x == var1.x && this.z == var1.z;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return (1204478626 + this.x) * -1331679453 + this.z;
   }

   @Override
   public String toString() {
      return String.format("GoalXZ{x=%s,z=%s}", SettingsUtil.maybeCensor(this.x), SettingsUtil.maybeCensor(this.z));
   }

   public static double calculate(double var0, double var2) {
      double var4 = Math.abs(var0);
      double var6 = Math.abs(var2);
      double var8;
      double var10;
      if (var4 < var6) {
         var8 = var6 - var4;
         var10 = var4;
      } else {
         var8 = var4 - var6;
         var10 = var6;
      }

      return (var10 * SQRT_2 + var8) * BaritoneAPI.getSettings().costHeuristic.value;
   }

   public static GoalXZ fromDirection(class_243 var0, float var1, double var2) {
      var1 = (float)Math.toRadians(var1);
      double var4 = var0.field_1352 - class_3532.method_15374(var1) * var2;
      double var6 = var0.field_1350 + class_3532.method_15362(var1) * var2;
      return new GoalXZ(class_3532.method_15357(var4), class_3532.method_15357(var6));
   }

   public int getX() {
      return this.x;
   }

   public int getZ() {
      return this.z;
   }
}
