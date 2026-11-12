package com.vex.baritone.process;

import com.vex.baritone.api.schematic.ISchematic;
import java.util.List;
import net.minecraft.class_2680;

class BuilderProcess$3 implements ISchematic {
   BuilderProcess$3(BuilderProcess var1, ISchematic var2, int var3, int var4) {
      this.a = var1;
      this.a = var2;
      this.a = var3;
      this.b = var4;
      super();
   }

   @Override
   public class_2680 desiredState(int var1, int var2, int var3, class_2680 var4, List<class_2680> var5) {
      return this.a.desiredState(var1, var2, var3, var4, this.a.a);
   }

   @Override
   public boolean inSchematic(int var1, int var2, int var3, class_2680 var4) {
      return ISchematic.super.inSchematic(var1, var2, var3, var4) && var2 >= this.a && var2 <= this.b && this.a.inSchematic(var1, var2, var3, var4);
   }

   @Override
   public void reset() {
      this.a.reset();
   }

   @Override
   public int widthX() {
      return this.a.widthX();
   }

   @Override
   public int heightY() {
      return this.a.heightY();
   }

   @Override
   public int lengthZ() {
      return this.a.lengthZ();
   }
}
