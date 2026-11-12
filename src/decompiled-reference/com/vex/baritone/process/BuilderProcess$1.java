package com.vex.baritone.process;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.schematic.ISchematic;
import com.vex.baritone.api.schematic.MaskSchematic;
import java.util.Collections;
import net.minecraft.class_2680;

class BuilderProcess$1 extends MaskSchematic {
   BuilderProcess$1(ISchematic var1) {
      super(var1);
   }

   @Override
   public boolean partOfMask(int var1, int var2, int var3, class_2680 var4) {
      return !Baritone.a().buildSkipBlocks.value.contains(this.desiredState(var1, var2, var3, var4, Collections.emptyList()).method_26204());
   }
}
