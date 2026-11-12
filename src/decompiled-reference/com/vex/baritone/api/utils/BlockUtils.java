package com.vex.baritone.api.utils;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.class_2248;
import net.minecraft.class_2960;
import net.minecraft.class_7923;

public class BlockUtils {
   private static transient Map<String, class_2248> resourceCache = new HashMap<>();

   public static String blockToString(class_2248 var0) {
      class_2960 var2;
      String var1 = (var2 = class_7923.field_41175.method_10221(var0)).method_12832();
      if (!var2.method_12836().equals("minecraft")) {
         var1 = var2.toString();
      }

      return var1;
   }

   public static class_2248 stringToBlockRequired(String var0) {
      class_2248 var1;
      if ((var1 = stringToBlockNullable(var0)) == null) {
         throw new IllegalArgumentException(String.format("Invalid block name %s", var0));
      } else {
         return var1;
      }
   }

   public static class_2248 stringToBlockNullable(String var0) {
      class_2248 var1;
      if ((var1 = resourceCache.get(var0)) != null) {
         return var1;
      }

      if (resourceCache.containsKey(var0)) {
         return null;
      }

      var1 = (class_2248)class_7923.field_41175.method_17966(class_2960.method_12829(var0.contains(":") ? var0 : "minecraft:" + var0)).orElse(null);
      HashMap var2;
      (var2 = new HashMap<>(resourceCache)).put(var0, var1);
      resourceCache = var2;
      return var1;
   }

   private BlockUtils() {
   }
}
