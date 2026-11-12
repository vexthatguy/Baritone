package com.vex.baritone.cache;

import com.vex.baritone.api.utils.BlockUtils;
import com.vex.baritone.pathing.movement.MovementHelper;
import com.vex.baritone.utils.BlockStateInterface;
import com.vex.baritone.utils.pathing.PathingBlockType;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import net.minecraft.class_1937;
import net.minecraft.class_2189;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2320;
import net.minecraft.class_2338;
import net.minecraft.class_2356;
import net.minecraft.class_243;
import net.minecraft.class_2526;
import net.minecraft.class_2680;
import net.minecraft.class_2818;
import net.minecraft.class_2826;
import net.minecraft.class_2841;
import net.minecraft.class_5321;

public final class ChunkPacker {
   private ChunkPacker() {
   }

   public static CachedChunk a(class_2818 var0) {
      HashMap var1 = new HashMap();
      int var2 = var0.method_12200().method_8597().comp_652();
      BitSet var3 = new BitSet(CachedChunk.a(var2));

      try {
         class_2826[] var4 = var0.method_12006();

         for (int var5 = 0; var5 < var2 / 16; var5++) {
            class_2826 var6;
            if ((var6 = var4[var5]) != null) {
               class_2841 var7 = var6.method_12265();
               int var8 = var5 << 4;

               for (int var23 = 0; var23 < 16; var23++) {
                  int var9 = var23 | var8;

                  for (int var10 = 0; var10 < 16; var10++) {
                     for (int var11 = 0; var11 < 16; var11++) {
                        int var12 = CachedChunk.a(var11, var9, var10);
                        class_2680 var13;
                        class_2680 var10000 = var13 = (class_2680)var7.method_12321(var11, var23, var10);
                        int var18 = var10;
                        int var17 = var9;
                        int var16 = var11;
                        class_2818 var15 = var0;
                        class_2680 var14 = var10000;
                        class_2248 var19 = var10000.method_26204();
                        PathingBlockType var32;
                        if (MovementHelper.d(var14)) {
                           if (MovementHelper.g(var14)) {
                              var32 = PathingBlockType.c;
                           } else {
                              int var31 = var17 - var15.method_12200().method_8597().comp_651();
                              class_243 var29;
                              var32 = (var16 == 15 || !MovementHelper.g(BlockStateInterface.a(var15, var16 + 1, var31, var18)))
                                    && (var16 == 0 || !MovementHelper.g(BlockStateInterface.a(var15, var16 - 1, var31, var18)))
                                    && (var18 == 15 || !MovementHelper.g(BlockStateInterface.a(var15, var16, var31, var18 + 1)))
                                    && (var18 == 0 || !MovementHelper.g(BlockStateInterface.a(var15, var16, var31, var18 - 1)))
                                 ? (
                                    var16 != 0 && var16 != 15 && var18 != 0 && var18 != 15
                                       ? PathingBlockType.b
                                       : (
                                          (var29 = var14.method_26227()
                                                         .method_15758(
                                                            var15.method_12200(),
                                                            new class_2338(
                                                               var16 + (var15.method_12004().field_9181 << 4),
                                                               var17,
                                                               var18 + (var15.method_12004().field_9180 << 4)
                                                            )
                                                         ))
                                                      .field_1352
                                                   == 0.0
                                                && var29.field_1350 == 0.0
                                             ? PathingBlockType.c
                                             : PathingBlockType.b
                                       )
                                 )
                                 : PathingBlockType.c;
                           }
                        } else {
                           var32 = !MovementHelper.b(var14) && !MovementHelper.c(var14)
                              ? (
                                 !(var19 instanceof class_2189)
                                       && !(var19 instanceof class_2526)
                                       && !(var19 instanceof class_2320)
                                       && !(var19 instanceof class_2356)
                                    ? PathingBlockType.d
                                    : PathingBlockType.a
                              )
                              : PathingBlockType.c;
                        }

                        boolean[] var30 = var32.a;
                        var3.set(var12, var30[0]);
                        var3.set(var12 + 1, var30[1]);
                        class_2248 var27 = var13.method_26204();
                        if (CachedChunk.a.contains(var27)) {
                           String var28 = BlockUtils.blockToString(var27);
                           var1.computeIfAbsent(var28, var0x -> new ArrayList<>()).add(new class_2338(var11, var9 + var0.method_31607(), var10));
                        }
                     }
                  }
               }
            }
         }
      } catch (Exception var20) {
         var20.printStackTrace();
      }

      class_2680[] var21 = new class_2680[256];

      for (int var22 = 0; var22 < 16; var22++) {
         label87:
         for (int var24 = 0; var24 < 16; var24++) {
            for (int var25 = var2 - 1; var25 >= 0; var25--) {
               int var26 = CachedChunk.a(var24, var25, var22);
               if (var3.get(var26) || var3.get(var26 + 1)) {
                  var21[var22 << 4 | var24] = BlockStateInterface.a(var0, var24, var25, var22);
                  continue label87;
               }
            }

            var21[var22 << 4 | var24] = class_2246.field_10124.method_9564();
         }
      }

      return new CachedChunk(var0.method_12004().field_9181, var0.method_12004().field_9180, var2, var3, var21, var1, System.currentTimeMillis());
   }

   public static class_2680 a(PathingBlockType var0, class_5321<class_1937> var1) {
      switch (var0) {
         case a:
            return class_2246.field_10124.method_9564();
         case b:
            return class_2246.field_10382.method_9564();
         case c:
            return class_2246.field_10164.method_9564();
         case d:
            if (var1 == class_1937.field_25180) {
               return class_2246.field_10515.method_9564();
            } else {
               if (var1 == class_1937.field_25181) {
                  return class_2246.field_10471.method_9564();
               }

               return class_2246.field_10340.method_9564();
            }
         default:
            return null;
      }
   }
}
