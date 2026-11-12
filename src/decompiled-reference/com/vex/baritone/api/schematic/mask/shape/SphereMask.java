package com.vex.baritone.api.schematic.mask.shape;

import com.vex.baritone.api.schematic.mask.AbstractMask;
import com.vex.baritone.api.schematic.mask.StaticMask;

public final class SphereMask extends AbstractMask implements StaticMask {
   private final double centerX;
   private final double centerY;
   private final double centerZ;
   private final double radiusSqX;
   private final double radiusSqY;
   private final double radiusSqZ;
   private final boolean filled;

   public SphereMask(int var1, int var2, int var3, boolean var4) {
      super(var1, var2, var3);
      this.centerX = var1 / 2.0;
      this.centerY = var2 / 2.0;
      this.centerZ = var3 / 2.0;
      this.radiusSqX = this.centerX * this.centerX;
      this.radiusSqY = this.centerY * this.centerY;
      this.radiusSqZ = this.centerZ * this.centerZ;
      this.filled = var4;
   }

   @Override
   public final boolean partOfMask(int var1, int var2, int var3) {
      double var4 = Math.abs(var1 + 0.5 - this.centerX);
      double var6 = Math.abs(var2 + 0.5 - this.centerY);
      double var8 = Math.abs(var3 + 0.5 - this.centerZ);
      return this.outside(var4, var6, var8)
         ? false
         : this.filled || this.outside(var4 + 1.0, var6, var8) || this.outside(var4, var6 + 1.0, var8) || this.outside(var4, var6, var8 + 1.0);
   }

   private boolean outside(double var1, double var3, double var5) {
      return var1 * var1 / this.radiusSqX + var3 * var3 / this.radiusSqY + var5 * var5 / this.radiusSqZ > 1.0;
   }
}
