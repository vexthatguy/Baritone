package com.vex.baritone.utils.schematic;

import com.vex.baritone.api.schematic.IStaticSchematic;
import com.vex.baritone.api.schematic.MaskSchematic;
import java.util.OptionalInt;
import java.util.function.Predicate;
import net.minecraft.class_2189;
import net.minecraft.class_2680;

public class MapArtSchematic extends MaskSchematic {
   private final int[][] a;

   public MapArtSchematic(IStaticSchematic var1) {
      super(var1);
      int[][] var2 = new int[var1.widthX()][var1.lengthZ()];
      int var3 = 0;

      for (int var4 = 0; var4 < var1.widthX(); var4++) {
         for (int var5 = 0; var5 < var1.lengthZ(); var5++) {
            class_2680[] var10001 = var1.getColumn(var4, var5);
            Predicate var7 = var0 -> !(var0.method_26204() instanceof class_2189);
            class_2680[] var6 = var10001;
            int var8 = var10001.length - 1;

            while (true) {
               if (var8 < 0) {
                  var10 = OptionalInt.empty();
                  break;
               }

               if (var7.test(var6[var8])) {
                  var10 = OptionalInt.of(var8);
                  break;
               }

               var8--;
            }

            OptionalInt var9 = var10;
            if (var10.isPresent()) {
               var2[var4][var5] = var9.getAsInt();
            } else {
               var3++;
               var2[var4][var5] = Integer.MAX_VALUE;
            }
         }
      }

      if (var3 != 0) {
         System.out.println(var3 + " columns had no block despite being in a map art, letting them be whatever");
      }

      this.a = var2;
   }

   @Override
   public boolean partOfMask(int var1, int var2, int var3, class_2680 var4) {
      return var2 >= this.a[var1][var3];
   }
}
