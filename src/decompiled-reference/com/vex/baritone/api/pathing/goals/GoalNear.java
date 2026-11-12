package com.vex.baritone.api.pathing.goals;

import com.vex.baritone.api.utils.BetterBlockPos;
import com.vex.baritone.api.utils.SettingsUtil;
import com.vex.baritone.api.utils.interfaces.IGoalRenderPos;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleOpenHashSet;
import net.minecraft.class_2338;

public class GoalNear implements Goal, IGoalRenderPos {
   private final int x;
   private final int y;
   private final int z;
   private final int rangeSq;

   public GoalNear(class_2338 var1, int var2) {
      this.x = var1.method_10263();
      this.y = var1.method_10264();
      this.z = var1.method_10260();
      this.rangeSq = var2 * var2;
   }

   @Override
   public boolean isInGoal(int var1, int var2, int var3) {
      var1 -= this.x;
      var2 -= this.y;
      var3 -= this.z;
      return var1 * var1 + var2 * var2 + var3 * var3 <= this.rangeSq;
   }

   @Override
   public double heuristic(int var1, int var2, int var3) {
      var1 -= this.x;
      var2 -= this.y;
      var3 -= this.z;
      return GoalBlock.calculate(var1, var2, var3);
   }

   @Override
   public double heuristic() {
      int var1 = (int)Math.ceil(Math.sqrt(this.rangeSq));
      DoubleOpenHashSet var2 = new DoubleOpenHashSet();
      double var3 = Double.POSITIVE_INFINITY;

      for (int var5 = -var1; var5 <= var1; var5++) {
         for (int var6 = -var1; var6 <= var1; var6++) {
            for (int var7 = -var1; var7 <= var1; var7++) {
               double var8;
               if ((var8 = this.heuristic(this.x + var5, this.y + var6, this.z + var7)) < var3 && this.isInGoal(this.x + var5, this.y + var6, this.z + var7)) {
                  var2.add(var8);
               } else {
                  var3 = Math.min(var3, var8);
               }
            }
         }
      }

      double var10 = Double.NEGATIVE_INFINITY;
      DoubleIterator var11 = var2.iterator();

      while (var11.hasNext()) {
         double var12;
         if ((var12 = var11.nextDouble()) < var3) {
            var10 = Math.max(var10, var12);
         }
      }

      return var10;
   }

   @Override
   public class_2338 getGoalPos() {
      return new class_2338(this.x, this.y, this.z);
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         var1 = var1;
         return this.x == var1.x && this.y == var1.y && this.z == var1.z && this.rangeSq == var1.rangeSq;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return (int)BetterBlockPos.longHash(this.x, this.y, this.z) + this.rangeSq;
   }

   @Override
   public String toString() {
      return String.format(
         "GoalNear{x=%s, y=%s, z=%s, rangeSq=%d}",
         SettingsUtil.maybeCensor(this.x),
         SettingsUtil.maybeCensor(this.y),
         SettingsUtil.maybeCensor(this.z),
         this.rangeSq
      );
   }
}
