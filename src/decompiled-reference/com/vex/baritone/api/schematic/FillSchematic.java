package com.vex.baritone.api.schematic;

import com.vex.baritone.api.utils.BlockOptionalMeta;
import java.util.List;
import net.minecraft.class_2680;

public class FillSchematic extends AbstractSchematic {
   private final BlockOptionalMeta bom;

   public FillSchematic(int var1, int var2, int var3, BlockOptionalMeta var4) {
      super(var1, var2, var3);
      this.bom = var4;
   }

   public FillSchematic(int var1, int var2, int var3, class_2680 var4) {
      this(var1, var2, var3, new BlockOptionalMeta(var4.method_26204()));
   }

   public BlockOptionalMeta getBom() {
      return this.bom;
   }

   @Override
   public class_2680 desiredState(int var1, int var2, int var3, class_2680 var4, List<class_2680> var5) {
      if (this.bom.matches(var4)) {
         return var4;
      }

      for (class_2680 var7 : var5) {
         if (this.bom.matches(var7)) {
            return var7;
         }
      }

      return this.bom.getAnyBlockState();
   }
}
