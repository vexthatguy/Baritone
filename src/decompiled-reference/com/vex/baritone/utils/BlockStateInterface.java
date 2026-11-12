package com.vex.baritone.utils;

import com.vex.baritone.Baritone;
import com.vex.baritone.api.utils.BetterBlockPos;
import com.vex.baritone.api.utils.IPlayerContext;
import com.vex.baritone.cache.CachedRegion;
import com.vex.baritone.cache.WorldData;
import com.vex.baritone.utils.accessor.IClientChunkProvider;
import com.vex.baritone.utils.pathing.BetterWorldBorder;
import net.minecraft.class_1937;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2680;
import net.minecraft.class_2806;
import net.minecraft.class_2818;
import net.minecraft.class_2826;
import net.minecraft.class_631;
import net.minecraft.class_2338.class_2339;

public class BlockStateInterface {
   public final class_631 a;
   public final WorldData a;
   protected final class_1937 a;
   private class_2339 a;
   private BlockStateInterfaceAccessWrapper a;
   public final BetterWorldBorder a;
   public class_2818 a = null;
   public CachedRegion a = null;
   private final boolean a;
   private static final class_2680 a = class_2246.field_10124.method_9564();

   public BlockStateInterface(IPlayerContext var1) {
      this(var1, false);
   }

   public BlockStateInterface(IPlayerContext var1, boolean var2) {
      this.a = var1.world();
      this.a = new BetterWorldBorder(this.a.method_8621());
      this.a = (WorldData)var1.worldData();
      if (var2) {
         this.a = ((IClientChunkProvider)this.a.method_8398()).createThreadSafeCopy();
      } else {
         this.a = (class_631)this.a.method_8398();
      }

      this.a = !Baritone.a().pathThroughCachedOnly.value;
      if (!var1.minecraft().method_18854()) {
         throw new IllegalStateException("BlockStateInterface must be constructed on the main thread");
      }

      this.a = new class_2339();
      this.a = new BlockStateInterfaceAccessWrapper(this);
   }

   public final boolean a(int var1, int var2) {
      return this.a.method_12123(var1 >> 4, var2 >> 4);
   }

   public static class_2248 a(IPlayerContext var0, BetterBlockPos var1) {
      return a(var0, (class_2338)var1).method_26204();
   }

   public static class_2680 a(IPlayerContext var0, class_2338 var1) {
      return new BlockStateInterface(var0).a(var1.method_10263(), var1.method_10264(), var1.method_10260());
   }

   public final class_2680 a(class_2338 var1) {
      return this.a(var1.method_10263(), var1.method_10264(), var1.method_10260());
   }

   public final class_2680 a(int var1, int var2, int var3) {
      if ((var2 = var2 - this.a.method_8597().comp_651()) >= 0 && var2 < this.a.method_8597().comp_652()) {
         if (this.a) {
            class_2818 var4 = this.a;
            if (this.a != null && var4.method_12004().field_9181 == var1 >> 4 && var4.method_12004().field_9180 == var3 >> 4) {
               return a(var4, var1, var2, var3);
            }

            if ((var4 = this.a.method_2857(var1 >> 4, var3 >> 4, class_2806.field_12803, false)) != null && !var4.method_12223()) {
               this.a = var4;
               return a(var4, var1, var2, var3);
            }
         }

         CachedRegion var7 = this.a;
         if (this.a == null || var7.getX() != var1 >> 9 || var7.getZ() != var3 >> 9) {
            if (this.a == null) {
               return a;
            }

            if ((var7 = this.a.a.a(var1 >> 9, var3 >> 9)) == null) {
               return a;
            }

            this.a = var7;
         }

         class_2680 var8;
         return (var8 = var7.getBlock(var1 & 511, var2 + this.a.method_8597().comp_651(), var3 & 511)) == null ? a : var8;
      } else {
         return a;
      }
   }

   public static class_2680 a(class_2818 var0, int var1, int var2, int var3) {
      class_2826 var4;
      return (var4 = var0.method_12006()[var2 >> 4]).method_38292() ? a : var4.method_12254(var1 & 15, var2 & 15, var3 & 15);
   }
}
