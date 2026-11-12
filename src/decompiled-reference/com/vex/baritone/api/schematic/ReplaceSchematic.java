package com.vex.baritone.api.schematic;

import com.vex.baritone.api.utils.BlockOptionalMetaLookup;
import net.minecraft.class_2680;

public class ReplaceSchematic extends MaskSchematic {
   private final BlockOptionalMetaLookup filter;
   private final Boolean[][][] cache;

   public ReplaceSchematic(ISchematic var1, BlockOptionalMetaLookup var2) {
      super(var1);
      this.filter = var2;
      this.cache = new Boolean[this.widthX()][this.heightY()][this.lengthZ()];
   }

   @Override
   public void reset() {
      for (int var1 = 0; var1 < this.cache.length; var1++) {
         for (int var2 = 0; var2 < this.cache[0].length; var2++) {
            for (int var3 = 0; var3 < this.cache[0][0].length; var3++) {
               this.cache[var1][var2][var3] = null;
            }
         }
      }
   }

   @Override
   protected boolean partOfMask(int var1, int var2, int var3, class_2680 var4) {
      if (this.cache[var1][var2][var3] == null) {
         this.cache[var1][var2][var3] = this.filter.has(var4);
      }

      return this.cache[var1][var2][var3];
   }
}
