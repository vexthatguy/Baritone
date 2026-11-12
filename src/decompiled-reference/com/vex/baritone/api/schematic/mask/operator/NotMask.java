package com.vex.baritone.api.schematic.mask.operator;

import com.vex.baritone.api.schematic.mask.AbstractMask;
import com.vex.baritone.api.schematic.mask.Mask;
import com.vex.baritone.api.schematic.mask.StaticMask;
import net.minecraft.class_2680;

public final class NotMask extends AbstractMask {
   private final Mask source;

   public NotMask(Mask var1) {
      super(var1.widthX(), var1.heightY(), var1.lengthZ());
      this.source = var1;
   }

   @Override
   public final boolean partOfMask(int var1, int var2, int var3, class_2680 var4) {
      return !this.source.partOfMask(var1, var2, var3, var4);
   }

   public static final class Static extends AbstractMask implements StaticMask {
      private final StaticMask source;

      public Static(StaticMask var1) {
         super(var1.widthX(), var1.heightY(), var1.lengthZ());
         this.source = var1;
      }

      @Override
      public final boolean partOfMask(int var1, int var2, int var3) {
         return !this.source.partOfMask(var1, var2, var3);
      }
   }
}
