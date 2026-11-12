package com.vex.baritone.process.elytra;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.event.events.BlockChangeEvent;
import com.vex.baritone.utils.accessor.IPalettedContainer;
import dev.babbaj.pathfinder.NetherPathfinder;
import dev.babbaj.pathfinder.Octree;
import dev.babbaj.pathfinder.PathSegment;
import java.lang.ref.SoftReference;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import net.minecraft.class_1923;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_243;
import net.minecraft.class_2680;
import net.minecraft.class_2818;
import net.minecraft.class_2826;
import net.minecraft.class_2835;
import net.minecraft.class_6490;

public final class NetherPathfinderContext {
   private static final class_2680 a = class_2246.field_10124.method_9564();
   public final Object a = new Object();
   final long a;
   public final long b;
   final ExecutorService a;

   public NetherPathfinderContext(long var1) {
      this.a = NetherPathfinder.newContext(var1);
      this.b = var1;
      this.a = Executors.newSingleThreadExecutor();
   }

   public final void a(int var1, int var2, int var3, BlockStateOctreeInterface var4) {
      this.a.execute(() -> {
         synchronized (this.a) {
            var4.b = 0L;
            NetherPathfinder.cullFarChunks(this.a, var1, var2, var3);
         }
      });
   }

   public final void a(class_2818 var1) {
      SoftReference var2 = new SoftReference<>(var1);
      this.a.execute(() -> {
         class_2818 var22;
         if ((var22 = (class_2818)var2.get()) != null) {
            long var3 = NetherPathfinder.getOrCreateChunk(this.a, var22.method_12004().field_9181, var22.method_12004().field_9180);
            long var6 = var3;
            class_2818 var23 = var22;

            try {
               class_2826[] var24 = var23.method_12006();

               for (int var2x = 0; var2x < 8; var2x++) {
                  class_2826 var25;
                  if ((var25 = var24[var2x]) != null) {
                     IPalettedContainer var26 = (IPalettedContainer)var25.method_12265();
                     int var4 = -1;
                     if (var26.getPalette().method_19525(var0 -> var0.equals(a))) {
                        var4 = var26.getPalette().method_12291(a, class_2835.method_74153());
                     }

                     class_6490 var27;
                     if ((var27 = var26.getStorage()) != null) {
                        long[] var5 = var27.method_15212();
                        int var8 = var27.method_15215();
                        int var28 = var27.method_34896();
                        long var17 = (1L << var28) - 1L;
                        int var9 = var2x << 4;
                        int var10 = 0;

                        for (int var11 = 0; var10 < var5.length && var11 < var8; var10++) {
                           long var19 = var5[var10];

                           for (int var12 = 0; var12 <= 64 - var28 && var11 < var8; var11++) {
                              int var13 = (int)(var19 >> var12 & var17);
                              int var14 = var11 & 15;
                              int var15 = var9 + (var11 >> 8);
                              int var16 = var11 >> 4 & 15;
                              Octree.setBlock(var6, var14, var15, var16, var13 != var4);
                              var12 += var28;
                           }
                        }
                     }
                  }
               }

               Octree.setIsFromJava(var6);
            } catch (Exception var21) {
               var21.printStackTrace();
               throw new RuntimeException(var21);
            }
         }
      });
   }

   public final void a(BlockChangeEvent var1) {
      this.a.execute(() -> {
         class_1923 var2 = var1.getChunkPos();
         long var3;
         if ((var3 = NetherPathfinder.getChunkPointer(this.a, var2.field_9181, var2.field_9180)) != 0L) {
            var1.getBlocks().forEach(var2x -> {
               class_2338 var3x;
               if ((var3x = (class_2338)var2x.first()).method_10264() < 128) {
                  boolean var4 = var2x.second() != a;
                  Octree.setBlock(var3, var3x.method_10263() & 15, var3x.method_10264(), var3x.method_10260() & 15, var4);
               }
            });
         }
      });
   }

   public final CompletableFuture<PathSegment> a(class_2338 var1, class_2338 var2) {
      return CompletableFuture.supplyAsync(
         () -> {
            PathSegment var3;
            if ((
                  var3 = NetherPathfinder.pathFind(
                     this.a,
                     var1.method_10263(),
                     var1.method_10264(),
                     var1.method_10260(),
                     var2.method_10263(),
                     var2.method_10264(),
                     var2.method_10260(),
                     true,
                     false,
                     10000,
                     !Baritone.a().elytraPredictTerrain.value
                  )
               )
               == null) {
               throw new PathCalculationException("Path calculation failed");
            } else {
               return var3;
            }
         },
         this.a
      );
   }

   public final boolean a(class_243 var1, class_243 var2) {
      return NetherPathfinder.isVisible(
         this.a, NetherPathfinder.CACHE_MISS_SOLID, var1.field_1352, var1.field_1351, var1.field_1350, var2.field_1352, var2.field_1351, var2.field_1350
      );
   }

   public final boolean a(double[] var1, double[] var2) {
      return NetherPathfinder.isVisibleMulti(this.a, NetherPathfinder.CACHE_MISS_SOLID, 8, var1, var2, false) == -1;
   }

   public static boolean a() {
      return NetherPathfinder.isThisSystemSupported();
   }

   public static final class Visibility {
      private Visibility() {
      }
   }
}
