package com.vex.baritone.utils.schematic.format.defaults;

import com.vex.baritone.utils.schematic.StaticSchematic;
import net.minecraft.class_1181;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2487;
import net.minecraft.class_2680;
import net.minecraft.class_2960;
import net.minecraft.class_7923;
import net.minecraft.class_6880.class_6883;

public final class MCEditSchematic extends StaticSchematic {
   public MCEditSchematic(class_2487 var1) {
      String var2;
      if (!(var2 = (String)var1.method_10558("Materials").orElseThrow()).equals("Alpha")) {
         throw new IllegalStateException("bad schematic " + var2);
      }

      super.x = var1.method_10550("Width").orElse(0);
      super.y = var1.method_10550("Height").orElse(0);
      super.z = var1.method_10550("Length").orElse(0);
      byte[] var10 = (byte[])var1.method_10547("Blocks").orElseThrow();
      byte[] var3 = null;
      if (var1.method_10545("AddBlocks")) {
         byte[] var8;
         var3 = new byte[(var8 = (byte[])var1.method_10547("AddBlocks").orElseThrow()).length << 1];

         for (int var4 = 0; var4 < var8.length; var4++) {
            var3[var4 << 1] = (byte)(var8[var4] >> 4 & 15);
            var3[(var4 << 1) + 1] = (byte)(var8[var4] & 15);
         }
      }

      super.a = new class_2680[super.x][super.z][super.y];

      for (int var9 = 0; var9 < super.y; var9++) {
         for (int var11 = 0; var11 < super.z; var11++) {
            for (int var5 = 0; var5 < super.x; var5++) {
               int var6 = (var9 * super.z + var11) * super.x + var5;
               int var7 = var10[var6] & 255;
               if (var3 != null) {
                  var7 |= var3[var6] << 8;
               }

               class_2960 var12;
               class_2248 var13 = (var12 = class_2960.method_12829(class_1181.method_5018(var7))) == null
                  ? class_2246.field_10124
                  : class_7923.field_41175.method_10223(var12).<class_2248>map(class_6883::comp_349).orElse(class_2246.field_10124);
               super.a[var5][var11][var9] = var13.method_9564();
            }
         }
      }
   }
}
