package com.vex.baritone.utils.schematic;

import com.vex.baritone.api.schematic.AbstractSchematic;
import com.vex.baritone.api.schematic.IStaticSchematic;
import java.util.List;
import net.minecraft.class_2680;

public class StaticSchematic extends AbstractSchematic implements IStaticSchematic {
   public class_2680[][][] a;

   public StaticSchematic() {
   }

   public StaticSchematic(class_2680[][][] var1) {
      this.a = var1;
      boolean var2 = var1.length == 0 || var1[0].length == 0 || var1[0][0].length == 0;
      super.x = var2 ? 0 : var1.length;
      super.z = var2 ? 0 : var1[0].length;
      super.y = var2 ? 0 : var1[0][0].length;
   }

   @Override
   public class_2680 desiredState(int var1, int var2, int var3, class_2680 var4, List<class_2680> var5) {
      return this.a[var1][var3][var2];
   }

   @Override
   public class_2680 getDirect(int var1, int var2, int var3) {
      return this.a[var1][var3][var2];
   }

   @Override
   public class_2680[] getColumn(int var1, int var2) {
      return this.a[var1][var2];
   }
}
