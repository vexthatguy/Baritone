package com.vex.baritone.utils.type;

import it.unimi.dsi.fastutil.bytes.ByteArrayList;

public final class VarInt {
   public final int a;
   private final byte[] a;
   public final int b;

   private VarInt(int var1) {
      this.a = var1;
      var1 = this.a;
      ByteArrayList var2 = new ByteArrayList();

      while ((var1 & 128) != 0) {
         var2.add((byte)(var1 & 127 | 128));
         var1 >>>= 7;
      }

      var2.add((byte)var1);
      this.a = var2.toByteArray();
      this.b = this.a.length;
   }

   public static VarInt a(byte[] var0, int var1) {
      int var2 = 0;
      int var3 = 0;

      byte var4;
      do {
         var4 = var0[var1++];
         var2 |= (var4 & 127) << var3++ * 7;
         if (var3 > 5) {
            throw new IllegalArgumentException("VarInt size cannot exceed 5 bytes");
         }
      } while ((var4 & 128) != 0);

      return new VarInt(var2);
   }
}
