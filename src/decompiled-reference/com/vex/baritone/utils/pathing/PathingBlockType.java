package com.vex.baritone.utils.pathing;

public enum PathingBlockType {
   a(0),
   b(1),
   c(2),
   d(3);

   public final boolean[] a;

   PathingBlockType(int var3) {
      this.a = new boolean[]{(var3 & 2) != 0, (var3 & 1) != 0};
   }

   public static PathingBlockType a(boolean var0, boolean var1) {
      if (var0) {
         return var1 ? d : c;
      } else {
         return var1 ? b : a;
      }
   }
}
