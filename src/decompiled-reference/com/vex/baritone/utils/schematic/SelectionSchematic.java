package com.vex.baritone.utils.schematic;

import com.vex.baritone.api.schematic.ISchematic;
import com.vex.baritone.api.schematic.MaskSchematic;
import com.vex.baritone.api.selection.ISelection;
import java.util.stream.Stream;
import net.minecraft.class_2350;
import net.minecraft.class_2382;
import net.minecraft.class_2680;

public class SelectionSchematic extends MaskSchematic {
   private final ISelection[] a;

   public SelectionSchematic(ISchematic var1, class_2382 var2, ISelection[] var3) {
      super(var1);
      this.a = Stream.of(var3)
         .map(
            var1x -> var1x.shift(class_2350.field_11039, var2.method_10263())
               .shift(class_2350.field_11033, var2.method_10264())
               .shift(class_2350.field_11043, var2.method_10260())
         )
         .toArray(ISelection[]::new);
   }

   @Override
   public boolean partOfMask(int var1, int var2, int var3, class_2680 var4) {
      ISelection[] var8 = this.a;
      int var5 = this.a.length;

      for (int var6 = 0; var6 < var5; var6++) {
         ISelection var7 = var8[var6];
         if (var1 >= var7.min().x && var2 >= var7.min().y && var3 >= var7.min().z && var1 <= var7.max().x && var2 <= var7.max().y && var3 <= var7.max().z) {
            return true;
         }
      }

      return false;
   }
}
