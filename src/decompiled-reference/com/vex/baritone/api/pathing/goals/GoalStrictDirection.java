package com.vex.baritone.api.pathing.goals;

import com.vex.baritone.api.utils.BetterBlockPos;
import com.vex.baritone.api.utils.SettingsUtil;
import net.minecraft.class_2338;
import net.minecraft.class_2350;

public class GoalStrictDirection implements Goal {
   public final int x;
   public final int y;
   public final int z;
   public final int dx;
   public final int dz;

   public GoalStrictDirection(class_2338 var1, class_2350 var2) {
      this.x = var1.method_10263();
      this.y = var1.method_10264();
      this.z = var1.method_10260();
      this.dx = var2.method_10148();
      this.dz = var2.method_10165();
      if (this.dx == 0 && this.dz == 0) {
         throw new IllegalArgumentException(var2 + "");
      }
   }

   @Override
   public boolean isInGoal(int var1, int var2, int var3) {
      return false;
   }

   @Override
   public double heuristic(int var1, int var2, int var3) {
      int var4 = (var1 - this.x) * this.dx + (var3 - this.z) * this.dz;
      var1 = Math.abs((var1 - this.x) * this.dz) + Math.abs((var3 - this.z) * this.dx);
      var2 = Math.abs(var2 - this.y);
      return (double)(-var4 * 100) + var1 * 1000 + var2 * 1000;
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
         return this.x == var1.x && this.y == var1.y && this.z == var1.z && this.dx == var1.dx && this.dz == var1.dz;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return ((int)BetterBlockPos.longHash(this.x, this.y, this.z) * 630627507 + this.dx) * -283028380 + this.dz;
   }

   @Override
   public String toString() {
      return String.format(
         "GoalStrictDirection{x=%s, y=%s, z=%s, dx=%s, dz=%s}",
         SettingsUtil.maybeCensor(this.x),
         SettingsUtil.maybeCensor(this.y),
         SettingsUtil.maybeCensor(this.z),
         SettingsUtil.maybeCensor(this.dx),
         SettingsUtil.maybeCensor(this.dz)
      );
   }
}
