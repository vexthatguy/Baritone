package com.vex.baritone.api.pathing.goals;

import com.vex.baritone.api.utils.SettingsUtil;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleOpenHashSet;
import java.util.Arrays;
import java.util.Objects;
import net.minecraft.class_2338;

public class GoalRunAway implements Goal {
   private final class_2338[] from;
   private final int distanceSq;
   private final Integer maintainY;

   public GoalRunAway(double var1, class_2338... var3) {
      this(var1, null, var3);
   }

   public GoalRunAway(double var1, Integer var3, class_2338... var4) {
      if (var4.length == 0) {
         throw new IllegalArgumentException("Positions to run away from must not be empty");
      }

      this.from = var4;
      this.distanceSq = (int)(var1 * var1);
      this.maintainY = var3;
   }

   @Override
   public boolean isInGoal(int var1, int var2, int var3) {
      if (this.maintainY != null && this.maintainY != var2) {
         return false;
      }

      class_2338[] var8 = this.from;
      int var4 = this.from.length;

      for (int var5 = 0; var5 < var4; var5++) {
         class_2338 var6 = var8[var5];
         int var7 = var1 - var6.method_10263();
         int var9 = var3 - var6.method_10260();
         if (var7 * var7 + var9 * var9 < this.distanceSq) {
            return false;
         }
      }

      return true;
   }

   @Override
   public double heuristic(int var1, int var2, int var3) {
      double var4 = Double.MAX_VALUE;
      class_2338[] var6 = this.from;
      int var7 = this.from.length;

      for (int var8 = 0; var8 < var7; var8++) {
         class_2338 var9;
         double var10;
         if ((var10 = GoalXZ.calculate((var9 = var6[var8]).method_10263() - var1, var9.method_10260() - var3)) < var4) {
            var4 = var10;
         }
      }

      var4 = -var4;
      if (this.maintainY != null) {
         var4 = var4 * 0.6 + GoalYLevel.calculate(this.maintainY, var2) * 1.5;
      }

      return var4;
   }

   @Override
   public double heuristic() {
      int var1 = (int)Math.ceil(Math.sqrt(this.distanceSq));
      int var2 = Integer.MAX_VALUE;
      int var3 = Integer.MAX_VALUE;
      int var4 = Integer.MAX_VALUE;
      int var5 = Integer.MIN_VALUE;
      int var6 = Integer.MIN_VALUE;
      int var7 = Integer.MIN_VALUE;
      class_2338[] var8 = this.from;
      int var9 = this.from.length;

      for (int var10 = 0; var10 < var9; var10++) {
         class_2338 var11 = var8[var10];
         var2 = Math.min(var2, var11.method_10263() - var1);
         var3 = Math.min(var3, var11.method_10264() - var1);
         var4 = Math.min(var4, var11.method_10260() - var1);
         var5 = Math.max(var2, var11.method_10263() + var1);
         var6 = Math.max(var3, var11.method_10264() + var1);
         var7 = Math.max(var4, var11.method_10260() + var1);
      }

      DoubleOpenHashSet var17 = new DoubleOpenHashSet();
      double var18 = Double.POSITIVE_INFINITY;

      for (int var19 = var2; var19 <= var5; var19++) {
         for (int var12 = var3; var12 <= var6; var12++) {
            for (int var15 = var4; var15 <= var7; var15++) {
               double var13;
               if ((var13 = this.heuristic(var19, var12, var15)) < var18 && this.isInGoal(var19, var12, var15)) {
                  var17.add(var13);
               } else {
                  var18 = Math.min(var18, var13);
               }
            }
         }
      }

      double var20 = Double.NEGATIVE_INFINITY;
      DoubleIterator var16 = var17.iterator();

      while (var16.hasNext()) {
         double var21;
         if ((var21 = var16.nextDouble()) < var18) {
            var20 = Math.max(var20, var21);
         }
      }

      return var20;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         var1 = var1;
         return this.distanceSq == var1.distanceSq && Arrays.equals(this.from, var1.from) && Objects.equals(this.maintainY, var1.maintainY);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return (Arrays.hashCode(this.from) * 1196803141 + this.distanceSq) * -2053788840 + this.maintainY;
   }

   @Override
   public String toString() {
      return this.maintainY != null
         ? String.format("GoalRunAwayFromMaintainY y=%s, %s", SettingsUtil.maybeCensor(this.maintainY), Arrays.asList(this.from))
         : "GoalRunAwayFrom" + Arrays.<class_2338>asList(this.from);
   }
}
