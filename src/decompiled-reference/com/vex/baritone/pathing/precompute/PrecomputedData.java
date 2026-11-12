package com.vex.baritone.pathing.precompute;

import com.vex.baritone.pathing.movement.MovementHelper;
import com.vex.baritone.utils.BlockStateInterface;
import net.minecraft.class_2248;
import net.minecraft.class_2680;

public class PrecomputedData {
   public final int[] a = new int[class_2248.field_10651.method_10204()];

   public final int a(int var1, class_2680 var2) {
      int var3 = 0;
      Ternary var4;
      if ((var4 = MovementHelper.c(var2)) == Ternary.a) {
         var3 = 2;
      }

      if (var4 == Ternary.b) {
         var3 |= 4;
      }

      if ((var4 = MovementHelper.a(var2)) == Ternary.a) {
         var3 |= 8;
      }

      if (var4 == Ternary.b) {
         var3 |= 16;
      }

      Ternary var5;
      if ((var5 = MovementHelper.b(var2)) == Ternary.a) {
         var3 |= 32;
      }

      if (var5 == Ternary.b) {
         var3 |= 64;
      }

      var3 |= 1;
      this.a[var1] = var3;
      return var3;
   }

   public final boolean a(BlockStateInterface var1, int var2, int var3, int var4, class_2680 var5) {
      int var6 = class_2248.field_10651.method_10206(var5);
      int var7;
      if (((var7 = this.a[var6]) & 1) == 0) {
         var7 = this.a(var6, var5);
      }

      return (var7 & 16) != 0 ? MovementHelper.c(var1, var2, var3, var4, var5) : (var7 & 8) != 0;
   }
}
