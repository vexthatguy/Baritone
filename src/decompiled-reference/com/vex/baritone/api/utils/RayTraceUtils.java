package com.vex.baritone.api.utils;

import net.minecraft.class_1297;
import net.minecraft.class_239;
import net.minecraft.class_243;
import net.minecraft.class_3959;
import net.minecraft.class_4050;
import net.minecraft.class_3959.class_242;
import net.minecraft.class_3959.class_3960;

public final class RayTraceUtils {
   private RayTraceUtils() {
   }

   public static class_239 rayTraceTowards(class_1297 var0, Rotation var1, double var2) {
      return rayTraceTowards(var0, var1, var2, false);
   }

   public static class_239 rayTraceTowards(class_1297 var0, Rotation var1, double var2, boolean var4) {
      class_243 var7;
      if (var4) {
         var7 = inferSneakingEyePosition(var0);
      } else {
         var7 = var0.method_5836(1.0F);
      }

      class_243 var5 = RotationUtils.calcLookDirectionFromRotation(var1);
      class_243 var6 = var7.method_1031(var5.field_1352 * var2, var5.field_1351 * var2, var5.field_1350 * var2);
      return var0.method_73183().method_17742(new class_3959(var7, var6, class_3960.field_17559, class_242.field_1348, var0));
   }

   public static class_243 inferSneakingEyePosition(class_1297 var0) {
      return new class_243(var0.method_23317(), var0.method_23318() + var0.method_18381(class_4050.field_18081), var0.method_23321());
   }
}
