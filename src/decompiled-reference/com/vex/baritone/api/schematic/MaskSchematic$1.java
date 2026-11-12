package com.vex.baritone.api.schematic;

import com.vex.baritone.api.schematic.mask.Mask;
import net.minecraft.class_2680;

class MaskSchematic$1 extends MaskSchematic {
   MaskSchematic$1(ISchematic var1, Mask var2) {
      super(var1);
      this.val$function = var2;
   }

   @Override
   protected boolean partOfMask(int var1, int var2, int var3, class_2680 var4) {
      return this.val$function.partOfMask(var1, var2, var3, var4);
   }
}
