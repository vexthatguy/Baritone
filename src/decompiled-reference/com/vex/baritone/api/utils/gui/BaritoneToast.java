package com.vex.baritone.api.utils.gui;

import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_370;
import net.minecraft.class_370.class_9037;

public class BaritoneToast {
   private static final class_9037 BARITONE_TOAST_ID = new class_9037(5000L);

   public static void addOrUpdate(class_2561 var0, class_2561 var1) {
      class_370.method_1990(class_310.method_1551().method_1566(), BARITONE_TOAST_ID, var0, var1);
   }
}
