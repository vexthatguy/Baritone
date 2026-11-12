package com.vex.baritone.api.utils;

import net.minecraft.class_1297;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_243;
import net.minecraft.class_265;
import net.minecraft.class_2680;
import net.minecraft.class_4770;
import net.minecraft.class_2350.class_2351;

public final class VecUtils {
   private VecUtils() {
   }

   public static class_243 calculateBlockCenter(class_1937 var0, class_2338 var1) {
      class_2680 var2;
      class_265 var9;
      if ((var9 = (var2 = var0.method_8320(var1)).method_26220(var0, var1)).method_1110()) {
         return getBlockPosCenter(var1);
      }

      double var3 = (var9.method_1091(class_2351.field_11048) + var9.method_1105(class_2351.field_11048)) / 2.0;
      double var5 = (var9.method_1091(class_2351.field_11052) + var9.method_1105(class_2351.field_11052)) / 2.0;
      double var7 = (var9.method_1091(class_2351.field_11051) + var9.method_1105(class_2351.field_11051)) / 2.0;
      if (!Double.isNaN(var3) && !Double.isNaN(var5) && !Double.isNaN(var7)) {
         if (var2.method_26204() instanceof class_4770) {
            var5 = 0.0;
         }

         return new class_243(var1.method_10263() + var3, var1.method_10264() + var5, var1.method_10260() + var7);
      } else {
         throw new IllegalStateException(var2 + " " + var1 + " " + var9);
      }
   }

   public static class_243 getBlockPosCenter(class_2338 var0) {
      return new class_243(var0.method_10263() + 0.5, var0.method_10264() + 0.5, var0.method_10260() + 0.5);
   }

   public static double distanceToCenter(class_2338 var0, double var1, double var3, double var5) {
      double var7 = var0.method_10263() + 0.5 - var1;
      double var9 = var0.method_10264() + 0.5 - var3;
      double var11 = var0.method_10260() + 0.5 - var5;
      return Math.sqrt(var7 * var7 + var9 * var9 + var11 * var11);
   }

   public static double entityDistanceToCenter(class_1297 var0, class_2338 var1) {
      return distanceToCenter(var1, var0.method_73189().field_1352, var0.method_73189().field_1351, var0.method_73189().field_1350);
   }

   public static double entityFlatDistanceToCenter(class_1297 var0, class_2338 var1) {
      return distanceToCenter(var1, var0.method_73189().field_1352, var1.method_10264() + 0.5, var0.method_73189().field_1350);
   }
}
