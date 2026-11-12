package com.vex.baritone.api.schematic.mask.shape;

import com.vex.baritone.api.schematic.mask.AbstractMask;
import com.vex.baritone.api.schematic.mask.StaticMask;
import net.minecraft.class_2350.class_2351;

public final class CylinderMask extends AbstractMask implements StaticMask {
   private final double centerA;
   private final double centerB;
   private final double radiusSqA;
   private final double radiusSqB;
   private final boolean filled;
   private final class_2351 alignment;

   public CylinderMask(int var1, int var2, int var3, boolean var4, class_2351 var5) {
      super(var1, var2, var3);
      this.centerA = getA(var1, var2, var5) / 2.0;
      this.centerB = getB(var2, var3, var5) / 2.0;
      this.radiusSqA = (this.centerA - 1.0) * (this.centerA - 1.0);
      this.radiusSqB = (this.centerB - 1.0) * (this.centerB - 1.0);
      this.filled = var4;
      this.alignment = var5;
   }

   @Override
   public final boolean partOfMask(int var1, int var2, int var3) {
      double var4 = Math.abs(getA(var1, var2, this.alignment) + 0.5 - this.centerA);
      double var6 = Math.abs(getB(var2, var3, this.alignment) + 0.5 - this.centerB);
      return this.outside(var4, var6) ? false : this.filled || this.outside(var4 + 1.0, var6) || this.outside(var4, var6 + 1.0);
   }

   private boolean outside(double var1, double var3) {
      return var1 * var1 / this.radiusSqA + var3 * var3 / this.radiusSqB > 1.0;
   }

   private static int getA(int var0, int var1, class_2351 var2) {
      return var2 == class_2351.field_11048 ? var1 : var0;
   }

   private static int getB(int var0, int var1, class_2351 var2) {
      return var2 == class_2351.field_11051 ? var0 : var1;
   }
}
