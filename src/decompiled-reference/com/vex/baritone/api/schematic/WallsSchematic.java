package com.vex.baritone.api.schematic;

import net.minecraft.class_2680;

public class WallsSchematic extends MaskSchematic {
   public WallsSchematic(ISchematic var1) {
      super(var1);
   }

   @Override
   protected boolean partOfMask(int var1, int var2, int var3, class_2680 var4) {
      return var1 == 0 || var3 == 0 || var1 == this.widthX() - 1 || var3 == this.lengthZ() - 1;
   }
}
