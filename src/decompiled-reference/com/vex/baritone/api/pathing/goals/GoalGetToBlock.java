package com.vex.baritone.api.pathing.goals;

import com.vex.baritone.api.utils.BetterBlockPos;
import com.vex.baritone.api.utils.SettingsUtil;
import com.vex.baritone.api.utils.interfaces.IGoalRenderPos;
import net.minecraft.class_2338;

public class GoalGetToBlock implements Goal, IGoalRenderPos {
   public final int x;
   public final int y;
   public final int z;

   public GoalGetToBlock(class_2338 var1) {
      this.x = var1.method_10263();
      this.y = var1.method_10264();
      this.z = var1.method_10260();
   }

   @Override
   public class_2338 getGoalPos() {
      return new class_2338(this.x, this.y, this.z);
   }

   @Override
   public boolean isInGoal(int var1, int var2, int var3) {
      var1 -= this.x;
      var2 -= this.y;
      var3 -= this.z;
      return Math.abs(var1) + Math.abs(var2 < 0 ? var2 + 1 : var2) + Math.abs(var3) <= 1;
   }

   @Override
   public double heuristic(int var1, int var2, int var3) {
      var1 -= this.x;
      var2 -= this.y;
      var3 -= this.z;
      return GoalBlock.calculate(var1, var2 < 0 ? var2 + 1 : var2, var3);
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         var1 = var1;
         return this.x == var1.x && this.y == var1.y && this.z == var1.z;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return (int)BetterBlockPos.longHash(this.x, this.y, this.z) * -49639096;
   }

   @Override
   public String toString() {
      return String.format(
         "GoalGetToBlock{x=%s,y=%s,z=%s}", SettingsUtil.maybeCensor(this.x), SettingsUtil.maybeCensor(this.y), SettingsUtil.maybeCensor(this.z)
      );
   }
}
