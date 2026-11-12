package com.vex.baritone.behavior.look;

import java.util.function.LongSupplier;

public final class ForkableRandom {
   public final long[] a;

   public ForkableRandom() {
      this(System.nanoTime() ^ System.currentTimeMillis());
   }

   private ForkableRandom(long var1) {
      LongSupplier var3 = () -> {
         long var10000 = var0.addAndGet(-7046029254386353131L);
         return ((var10000 ^ var10000 >>> 30) * -4658895280553007687L ^ (var10000 ^ var10000 >>> 30) * -4658895280553007687L >>> 27) * -7723592293110705685L
            ^ ((var10000 ^ var10000 >>> 30) * -4658895280553007687L ^ (var10000 ^ var10000 >>> 30) * -4658895280553007687L >>> 27) * -7723592293110705685L
               >>> 31;
      };
      this.a = new long[]{var3.getAsLong(), var3.getAsLong(), var3.getAsLong(), var3.getAsLong()};
   }

   public ForkableRandom(long[] var1) {
      this.a = var1;
   }

   public final double a() {
      return (this.a() >>> 11) * 1.110223E-16F;
   }

   private long a() {
      long var1 = a(this.a[0] + this.a[3], 23) + this.a[0];
      long var3 = this.a[1] << 17;
      this.a[2] = this.a[2] ^ this.a[0];
      this.a[3] = this.a[3] ^ this.a[1];
      this.a[1] = this.a[1] ^ this.a[2];
      this.a[0] = this.a[0] ^ this.a[3];
      this.a[2] = this.a[2] ^ var3;
      this.a[3] = a(this.a[3], 45);
      return var1;
   }

   private static long a(long var0, int var2) {
      return var0 << var2 | var0 >>> 64 - var2;
   }
}
