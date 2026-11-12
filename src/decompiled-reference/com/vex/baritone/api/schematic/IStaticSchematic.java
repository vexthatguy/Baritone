package com.vex.baritone.api.schematic;

import net.minecraft.class_2680;

public interface IStaticSchematic extends ISchematic {
   class_2680 getDirect(int var1, int var2, int var3);

   default class_2680[] getColumn(int var1, int var2) {
      class_2680[] var3 = new class_2680[this.heightY()];

      for (int var4 = 0; var4 < this.heightY(); var4++) {
         var3[var4] = this.getDirect(var1, var4, var2);
      }

      return var3;
   }
}
