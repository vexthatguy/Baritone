package com.vex.baritone.utils;

import javax.annotation.Nullable;
import net.minecraft.class_1922;
import net.minecraft.class_2338;
import net.minecraft.class_2586;
import net.minecraft.class_2680;
import net.minecraft.class_3610;

public final class BlockStateInterfaceAccessWrapper implements class_1922 {
   private final BlockStateInterface a;

   BlockStateInterfaceAccessWrapper(BlockStateInterface var1) {
      this.a = var1;
   }

   @Nullable
   public final class_2586 method_8321(class_2338 var1) {
      return null;
   }

   public final class_2680 method_8320(class_2338 var1) {
      return this.a.a(var1.method_10263(), var1.method_10264(), var1.method_10260());
   }

   public final class_3610 method_8316(class_2338 var1) {
      return this.method_8320(var1).method_26227();
   }

   public final int method_31605() {
      return this.a.a.method_31605();
   }

   public final int method_31607() {
      return this.a.a.method_31607();
   }
}
