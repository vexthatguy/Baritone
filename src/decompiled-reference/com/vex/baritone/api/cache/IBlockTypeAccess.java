package com.vex.baritone.api.cache;

import net.minecraft.class_2338;
import net.minecraft.class_2680;

public interface IBlockTypeAccess {
   class_2680 getBlock(int var1, int var2, int var3);

   default class_2680 getBlock(class_2338 var1) {
      return this.getBlock(var1.method_10263(), var1.method_10264(), var1.method_10260());
   }
}
