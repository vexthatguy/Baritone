package com.vex.baritone.api.schematic;

public abstract class AbstractSchematic implements ISchematic {
   public int x;
   public int y;
   public int z;

   public AbstractSchematic() {
      this(0, 0, 0);
   }

   public AbstractSchematic(int var1, int var2, int var3) {
      this.x = var1;
      this.y = var2;
      this.z = var3;
   }

   @Override
   public int widthX() {
      return this.x;
   }

   @Override
   public int heightY() {
      return this.y;
   }

   @Override
   public int lengthZ() {
      return this.z;
   }
}
