package com.vex.baritone.api.utils;

import javax.annotation.Nonnull;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2382;
import net.minecraft.class_3532;

public final class BetterBlockPos extends class_2338 {
   private static final int NUM_X_BITS = 26;
   private static final int NUM_Z_BITS = 26;
   private static final int NUM_Y_BITS = 12;
   private static final int Y_SHIFT = 26;
   private static final int X_SHIFT = 38;
   private static final long X_MASK = 67108863L;
   private static final long Y_MASK = 4095L;
   private static final long Z_MASK = 67108863L;
   public static final BetterBlockPos ORIGIN = new BetterBlockPos(0, 0, 0);
   public final int x;
   public final int y;
   public final int z;

   public BetterBlockPos(int var1, int var2, int var3) {
      super(var1, var2, var3);
      this.x = var1;
      this.y = var2;
      this.z = var3;
   }

   public BetterBlockPos(double var1, double var3, double var5) {
      this(class_3532.method_15357(var1), class_3532.method_15357(var3), class_3532.method_15357(var5));
   }

   public BetterBlockPos(class_2338 var1) {
      this(var1.method_10263(), var1.method_10264(), var1.method_10260());
   }

   public static BetterBlockPos from(class_2338 var0) {
      return var0 == null ? null : new BetterBlockPos(var0);
   }

   public final int hashCode() {
      return (int)longHash(this.x, this.y, this.z);
   }

   public static long longHash(BetterBlockPos var0) {
      return longHash(var0.x, var0.y, var0.z);
   }

   public static long longHash(int var0, int var1, int var2) {
      long var3 = 11206370049L + var0;
      var3 = 8734625L * var3 + var1;
      return 2873465L * var3 + var2;
   }

   public final boolean equals(Object var1) {
      if (var1 == null) {
         return false;
      }

      class_2338 var2;
      BetterBlockPos var3;
      return var1 instanceof BetterBlockPos
         ? (var3 = (BetterBlockPos)var1).x == this.x && var3.y == this.y && var3.z == this.z
         : (var2 = (class_2338)var1).method_10263() == this.x && var2.method_10264() == this.y && var2.method_10260() == this.z;
   }

   public final BetterBlockPos above() {
      return new BetterBlockPos(this.x, this.y + 1, this.z);
   }

   public final BetterBlockPos above(int var1) {
      return var1 == 0 ? this : new BetterBlockPos(this.x, this.y + var1, this.z);
   }

   public final BetterBlockPos below() {
      return new BetterBlockPos(this.x, this.y - 1, this.z);
   }

   public final BetterBlockPos below(int var1) {
      return var1 == 0 ? this : new BetterBlockPos(this.x, this.y - var1, this.z);
   }

   public final BetterBlockPos relative(class_2350 var1) {
      class_2382 var2 = var1.method_62675();
      return new BetterBlockPos(this.x + var2.method_10263(), this.y + var2.method_10264(), this.z + var2.method_10260());
   }

   public final BetterBlockPos relative(class_2350 var1, int var2) {
      if (var2 == 0) {
         return this;
      }

      class_2382 var3 = var1.method_62675();
      return new BetterBlockPos(this.x + var3.method_10263() * var2, this.y + var3.method_10264() * var2, this.z + var3.method_10260() * var2);
   }

   public final BetterBlockPos north() {
      return new BetterBlockPos(this.x, this.y, this.z - 1);
   }

   public final BetterBlockPos north(int var1) {
      return var1 == 0 ? this : new BetterBlockPos(this.x, this.y, this.z - var1);
   }

   public final BetterBlockPos south() {
      return new BetterBlockPos(this.x, this.y, this.z + 1);
   }

   public final BetterBlockPos south(int var1) {
      return var1 == 0 ? this : new BetterBlockPos(this.x, this.y, this.z + var1);
   }

   public final BetterBlockPos east() {
      return new BetterBlockPos(this.x + 1, this.y, this.z);
   }

   public final BetterBlockPos east(int var1) {
      return var1 == 0 ? this : new BetterBlockPos(this.x + var1, this.y, this.z);
   }

   public final BetterBlockPos west() {
      return new BetterBlockPos(this.x - 1, this.y, this.z);
   }

   public final BetterBlockPos west(int var1) {
      return var1 == 0 ? this : new BetterBlockPos(this.x - var1, this.y, this.z);
   }

   public final double distanceSq(BetterBlockPos var1) {
      double var2 = (double)this.x - var1.x;
      double var4 = (double)this.y - var1.y;
      double var6 = (double)this.z - var1.z;
      return var2 * var2 + var4 * var4 + var6 * var6;
   }

   public final double distanceTo(BetterBlockPos var1) {
      double var2 = (double)this.x - var1.x;
      double var4 = (double)this.y - var1.y;
      double var6 = (double)this.z - var1.z;
      return Math.sqrt(var2 * var2 + var4 * var4 + var6 * var6);
   }

   @Nonnull
   public final String toString() {
      return String.format(
         "BetterBlockPos{x=%s,y=%s,z=%s}", SettingsUtil.maybeCensor(this.x), SettingsUtil.maybeCensor(this.y), SettingsUtil.maybeCensor(this.z)
      );
   }

   public static long serializeToLong(int var0, int var1, int var2) {
      return (var0 & 67108863L) << 38 | (var1 & 4095L) << 26 | var2 & 67108863L;
   }

   public static BetterBlockPos deserializeFromLong(long var0) {
      int var2 = (int)(var0 >> 38);
      int var3 = (int)(var0 << 26 >> 52);
      int var4 = (int)(var0 << 38 >> 38);
      return new BetterBlockPos(var2, var3, var4);
   }
}
