package com.vex.baritone.utils.schematic.format.defaults;

import com.vex.baritone.api.schematic.CompositeSchematic;
import com.vex.baritone.api.schematic.IStaticSchematic;
import com.vex.baritone.utils.schematic.StaticSchematic;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2382;
import net.minecraft.class_2487;
import net.minecraft.class_2499;
import net.minecraft.class_2680;
import net.minecraft.class_2769;
import net.minecraft.class_2960;
import net.minecraft.class_7923;
import net.minecraft.class_6880.class_6883;
import org.apache.commons.lang3.Validate;

public final class LitematicaSchematic extends CompositeSchematic implements IStaticSchematic {
   public LitematicaSchematic(class_2487 var1) {
      super(0, 0, 0);
      class_2487 var2 = var1;
      LitematicaSchematic var30 = this;
      class_2382 var3 = new class_2382(b(var2, "x"), b(var2, "y"), b(var2, "z"));
      class_2487[] var31;
      int var4 = (var31 = a(var2)).length;

      for (int var5 = 0; var5 < var4; var5++) {
         class_2487 var6;
         class_2499 var7;
         class_2499 var9;
         class_2680[] var10 = new class_2680[(var9 = var7 = (var6 = var31[var5]).method_68569("BlockStatePalette")).size()];

         for (int var8 = 0; var8 < var9.size(); var8++) {
            class_2487 var11;
            class_2960 var12;
            class_2248 var13 = (var12 = class_2960.method_12829((var11 = (class_2487)var9.method_10534(var8)).method_10558("Name").orElse(""))) == null
               ? class_2246.field_10124
               : class_7923.field_41175.method_10223(var12).<class_2248>map(class_6883::comp_349).orElse(class_2246.field_10124);
            class_2487 var14 = var11.method_10562("Properties").orElse(new class_2487());
            class_2487 var16 = var14;
            class_2248 var15 = var13;
            class_2680 var17 = var13.method_9564();

            for (String var19 : var16.method_10541()) {
               class_2769 var20 = var15.method_9595().method_11663(var19);
               String var21 = (String)var16.method_10558(var19).orElse(null);
               if (var20 != null) {
                  String var24 = var21;
                  class_2769 var23 = var20;
                  class_2680 var22 = var17;
                  Optional var25;
                  if (!(var25 = var23.method_11900(var24)).isPresent()) {
                     throw new IllegalArgumentException("Invalid value for property " + var23);
                  }

                  var17 = (class_2680)var22.method_11657(var23, (Comparable)var25.get());
               }
            }

            var10[var8] = var17;
         }

         class_2680[] var36 = var10;
         int var39 = var7.size();
         int var33 = (int)Math.max(2.0, Math.ceil(Math.log(var39) / Math.log(2.0)));
         class_2487 var42;
         long var46 = Math.abs(
            (var42 = var6.method_10562("Size").orElse(new class_2487())).method_10550("x").orElse(0)
               * var42.method_10550("y").orElse(0)
               * var42.method_10550("z").orElse(0)
         );
         long[] var40 = var6.method_10565("BlockStates").orElse(new long[0]);
         LitematicaSchematic.LitematicaBitArray var34 = new LitematicaSchematic.LitematicaBitArray(var33, var46, var40);
         LitematicaSchematic.LitematicaBitArray var47 = var34;
         class_2680[] var45 = var36;
         class_2382 var37 = var3;
         class_2487 var43 = var6;
         LitematicaSchematic var41 = var30;
         int var48 = a(var43, "x") - var37.method_10263();
         int var49 = a(var43, "y") - var37.method_10264();
         int var50 = a(var43, "z") - var37.method_10260();
         class_2487 var51;
         int var52 = Math.abs((var51 = var43.method_10562("Size").orElse(new class_2487())).method_10550("x").orElse(0));
         int var53 = Math.abs(var51.method_10550("y").orElse(0));
         int var54 = Math.abs(var51.method_10550("z").orElse(0));
         class_2680[][][] var55 = new class_2680[var52][var54][var53];
         int var56 = 0;

         for (int var57 = 0; var57 < var53; var57++) {
            for (int var58 = 0; var58 < var54; var58++) {
               for (int var59 = 0; var59 < var52; var59++) {
                  class_2680[] var10000 = var55[var59][var58];
                  long var26 = var56;
                  LitematicaSchematic.LitematicaBitArray var60 = var47;
                  Validate.inclusiveBetween(0L, var60.b - 1L, var26);
                  long var28;
                  int var32 = (int)((var28 = var26 * var60.a) >> 6);
                  int var35 = (int)((var26 + 1L) * var60.a - 1L >> 6);
                  int var38 = (int)(var28 & 63L);
                  int var10003;
                  if (var32 == var35) {
                     var10003 = (int)(var60.a[var32] >>> var38 & var60.a);
                  } else {
                     int var44 = 64 - var38;
                     var10003 = (int)((var60.a[var32] >>> var38 | var60.a[var35] << var44) & var60.a);
                  }

                  var10000[var57] = var45[var10003];
                  var56++;
               }
            }
         }

         var41.put(new StaticSchematic(var55), var48, var49, var50);
      }
   }

   private static class_2487[] a(class_2487 var0) {
      return var0.method_10562("Regions")
         .<Collection>map(class_2487::method_68567)
         .map(var0x -> var0x.stream().filter(var0xx -> var0xx instanceof class_2487).map(class_2487.class::cast).toArray(class_2487[]::new))
         .orElse(new class_2487[0]);
   }

   private static int a(class_2487 var0, String var1) {
      int var2 = var0.method_10562("Position").flatMap(var1x -> var1x.method_10550(var1)).orElse(0);
      int var3 = var0.method_10562("Size").flatMap(var1x -> var1x.method_10550(var1)).orElse(0);
      return Math.min(var2, var2 + var3 + 1);
   }

   private static int b(class_2487 var0, String var1) {
      int var2 = Integer.MAX_VALUE;
      class_2487[] var6;
      int var3 = (var6 = a(var0)).length;

      for (int var4 = 0; var4 < var3; var4++) {
         class_2487 var5 = var6[var4];
         var2 = Math.min(var2, a(var5, var1));
      }

      return var2;
   }

   @Override
   public final class_2680 getDirect(int var1, int var2, int var3) {
      return this.desiredState(var1, var2, var3, null, Collections.emptyList());
   }

   static class LitematicaBitArray {
      final long[] a;
      final int a;
      final long a;
      final long b;

      public LitematicaBitArray(int var1, long var2, @Nullable long[] var4) {
         Validate.inclusiveBetween(1L, 32L, var1);
         this.b = var2;
         this.a = var1;
         this.a = (1L << var1) - 1L;
         if (var4 != null) {
            this.a = var4;
         } else {
            long var5 = var2 * var1;
            byte var9 = 1;
            long var10001;
            if (64L == 0L) {
               var10001 = 0L;
            } else if (var5 == 0L) {
               var10001 = 64L;
            } else {
               if (var5 < 0L) {
                  var9 = -1;
               }

               long var7;
               var10001 = (var7 = var5 % (64L * var9)) == 0L ? var5 : var5 + 64L * var9 - var7;
            }

            this.a = new long[(int)(var10001 / 64L)];
         }
      }
   }
}
