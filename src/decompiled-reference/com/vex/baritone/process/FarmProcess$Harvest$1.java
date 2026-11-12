package com.vex.baritone.process;

import com.vex.baritone.Baritone;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_2523;
import net.minecraft.class_2680;

enum FarmProcess$Harvest$1 {
   @Override
   public final boolean a(class_1937 var1, class_2338 var2, class_2680 var3) {
      return Baritone.a().replantCrops.value ? var1.method_8320(var2.method_10074()).method_26204() instanceof class_2523 : true;
   }
}
