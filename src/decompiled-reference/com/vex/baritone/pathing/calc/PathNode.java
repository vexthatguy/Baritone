package com.vex.baritone.pathing.calc;

import com.vex.baritone.api.pathing.goals.Goal;
import com.vex.baritone.api.utils.BetterBlockPos;
import com.vex.baritone.api.utils.SettingsUtil;

public final class PathNode {
   public final int a;
   public final int b;
   public final int c;
   public final double a;
   public double b;
   public double c;
   public PathNode a = null;
   public int d;

   public PathNode(int var1, int var2, int var3, Goal var4) {
      this.b = 1000000.0;
      this.a = var4.heuristic(var1, var2, var3);
      if (Double.isNaN(this.a)) {
         throw new IllegalStateException(
            String.format(
               "%s calculated implausible heuristic NaN at %s %s %s",
               var4,
               SettingsUtil.maybeCensor(var1),
               SettingsUtil.maybeCensor(var2),
               SettingsUtil.maybeCensor(var3)
            )
         );
      }

      this.d = -1;
      this.a = var1;
      this.b = var2;
      this.c = var3;
   }

   @Override
   public final int hashCode() {
      return (int)BetterBlockPos.longHash(this.a, this.b, this.c);
   }

   @Override
   public final boolean equals(Object var1) {
      var1 = var1;
      return this.a == var1.a && this.b == var1.b && this.c == var1.c;
   }
}
