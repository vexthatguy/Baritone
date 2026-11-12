package com.vex.baritone.api.schematic.mask;

public abstract class AbstractMask implements Mask {
   private final int widthX;
   private final int heightY;
   private final int lengthZ;

   public AbstractMask(int var1, int var2, int var3) {
      this.widthX = var1;
      this.heightY = var2;
      this.lengthZ = var3;
   }

   @Override
   public int widthX() {
      return this.widthX;
   }

   @Override
   public int heightY() {
      return this.heightY;
   }

   @Override
   public int lengthZ() {
      return this.lengthZ;
   }
}
